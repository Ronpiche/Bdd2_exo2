import org.apache.spark.graphx.{Edge, VertexId}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class Alignment() {
  var members: ArrayBuffer[Int] = ArrayBuffer.empty[Int]

  def add(creature: Creature, number : Int = 1): Unit = {

    val classe = creature.getClass()

    println(creature.name, number)
    println("health", creature.hp)
    println("armor", creature.armor)
    println("regen", creature.regen)
    println("\n")

    for (i <- 0 until number) {
      val crea = classe.newInstance()
      members += StoreCreature.add(crea)
    }
  }

  def vertices(): ArrayBuffer[(VertexId, Int)] = {
    val result = members.zipWithIndex.map{case (creature, index) => (index.toLong, creature)}

    result
  }

  def edges(): ArrayBuffer[Edge[Int]] = {
    val result = for (i <- 0 until members.length; j <- (i + 1) until members.length) yield Edge(i.toLong, j.toLong, 1)
    // the 1 indicates that the nodes of a given edge are on the same side

    result.to[mutable.ArrayBuffer]
  }
}