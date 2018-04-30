import monsters._
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.{SparkConf, SparkContext, graphx}
import org.apache.spark.util.LongAccumulator
import Console.{BLACK, BLUE, GREEN, RED, RESET, YELLOW_B, UNDERLINED}

import scala.collection.mutable.ArrayBuffer

object Exercise2 extends App {

  def createGraph(sc: SparkContext, allies: Alignment, enemies: Alignment): Graph[Int, Int] = {
    val allies_len = allies.members.length
    val enemies_len = enemies.members.length

    val offset = allies_len
    print(s"$offset")

    var vertices = allies.vertices()
    vertices ++= enemies.vertices().map(e => (offset + e._1, e._2))

    val alliesEdges = allies.edges()
    val enemiesEdges = enemies.edges().map(e => Edge(offset + e.srcId, offset + e.dstId, e.attr))
    // Joins allies and enemies
    //0 indicates that the nodes of the edge are on different sides
    val inBetweenEdges = for (i <- 0 until allies_len; j <- 0 until enemies_len) yield Edge(i.toLong, (offset + j).toLong, 0)

    /// faire plusieurs graphes?
    var edges = alliesEdges
    edges ++= enemiesEdges
    edges ++= inBetweenEdges

    val result = Graph(sc.parallelize(vertices), sc.parallelize(edges))
    return result
  }

  override def main(args: Array[String]) {

    val t0 = System.nanoTime()

    System.setProperty("hadoop.home.dir", "C:\\Winutils")
    // val conf = new SparkConf().setMaster("local[2]")
    val conf = new SparkConf()
      .setAppName("Combat")
      .setMaster("local[*]")
    conf.set("spark.testing.memory", "2147480000")

    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    var store = sc.broadcast(StoreCreature)

    var allies = new Alignment()
    var enemies = new Alignment()

    initFirstBattle(allies, enemies)
    //initSecondBattle(allies, enemies)

    val numAllies = allies.members.length
    val numEnemies = enemies.members.length


    var alliesDead: LongAccumulator = sc.longAccumulator("Nombre d'alliés morts")
    var enemiesDead: LongAccumulator = sc.longAccumulator("Nombre d'ennemis morts")

    val graph = createGraph(sc, allies, enemies)

    val graphBis = graph.vertices.collect()


    var done = false
    var turn = 1

    while (!done) {
      println("\n\n\n")
      println(s"${RESET}${GREEN}Début du tour $turn\n\n${RESET}")
      alliesDead.reset()
      enemiesDead.reset()

      //met a jour les entités
      graphBis.map(element => {
        var key = element._2
        var c = store.value.get(key)

        c.update()
      })

      graphBis.map(element => {
        var id = element._1
        var key = element._2
        var c = store.value.get(key)

        if (c.deadSince<1) {
          c.naiveAttack(id, graph, store)
        }
        else {
          val name = c.name
          val deadSince = c.deadSince
          println(s"$name $id died $deadSince turns ago\n")
          if (c.getAlignement() == "Good")
            alliesDead.add(1)
          else
            enemiesDead.add(1)
        }
      }
      )

      if (alliesDead.value == numAllies || enemiesDead.value == numEnemies)
        done = true

      turn += 1
    }
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0)/math.pow(10,9) + "s")

    if (alliesDead.value == numAllies && enemiesDead.value == numEnemies)
      println("Everyone is dead")

    else {
      if (alliesDead.value == numAllies)
        println("GAME OVER")

      if (enemiesDead.value == numEnemies)
        println("YOU WON")
    }
  }

  def initFirstBattle(allies: Alignment, enemies: Alignment): Unit={
    allies.add(new Solar())


    enemies.add(new OrcWorgRider() ,9)
    enemies.add(new BrutalWarlord())
  }
}
