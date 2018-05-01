import java.util
import org.apache.spark._
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.graphx.{Graph, VertexId}

import scala.collection.mutable.ArrayBuffer

abstract class Creature(val name:String, val id : Int = 1)  {

  val hpMax : Int
  var hp : In
  var armor : Int
  val creatureType: String
  val favouredEnemy: (String, Int) = ("",0)
  //val name : String
  ////ajoute capacités défensives
  val regen: Int = 0
  val damageReduction = 0
  val alignment : String
  var deadSince = -1
  def update(): Unit ={
    this.hp = math.min(this.hp+regen, hpMax)
    if (this.deadSince!= -1)
      deadSince += 1
  }
  var baseMeleeAttack : Array[Int]

  def diceThrow(throwNumber: Int, facesNumber: Int): Int ={
    val random = scala.util.Random
    var sum = 0
    var x = 0
    for ( x <- 1 to throwNumber){
      sum += random.nextInt(facesNumber)+1
    }
    sum
  }

  def isDead(): Boolean = {
    if (hp <= 0) true
    else false
  }

  def getAlignement(): String ={
    this.alignment
  }

  //attack is an array commposed of : number of attacks left, base precision, base damage, number of dice throws, number of dice faces, critical value, special efect
  def melee(attack : Array[Int], targetId: VertexId, target: Creature): Int =
  {
    var numberAttacksLeft = attack(0)

    while(!target.isDead() && numberAttacksLeft > 0) {
      val diceThrowPrc = diceThrow(1, 20)
      val prc = attack(1) + numberAttacksLeft * 5 + diceThrowPrc
      val targetName = target.name
      numberAttacksLeft -= 1
      if (prc >= target.armor || (this.name=="Orc Barbarian" && prc == 20)) {
        var dmg = attack(2) +  diceThrow(attack(3), attack(4))
        if (diceThrowPrc >= attack(5)) {
          dmg *= 3
          println("Critical hit!")
        }
        var effectiveDmg = math.max(0,(dmg-target.damageReduction))
        target.hp -= effectiveDmg

        println(s"$name inflicted $effectiveDmg to $targetName $targetId")
        specialEffect(attack(6), target)
      }

        if (target.isDead())
          target.deadSince = 0

      else
        println(s"$name missed $targetName")
    }

    println("\n")

    numberAttacksLeft
  }

  def naiveAttack(id: VertexId, graph: Graph[Int, Int], store: Broadcast[StoreCreature.type]) : Unit =
  {
    println(s"$name $id (hp:$hp) is choosing its action...")

    val result = findLowestHealthEnemy(id, graph, store)

    if (result._2 == -1) {
      println(s"\t$name can't do anything\n")
    }
    else {
      var hitsLeft = melee(this.baseMeleeAttack, result._1, store.value.get(result._2))
      if (this.name == "Solar" && store.value.get(result._2).name == "Orc Barbarian") {
        while (hitsLeft > 0) {
          var attackTemp = this.baseMeleeAttack.clone()
          attackTemp(0) = hitsLeft
          val nextTarget = findLowestHealthEnemy(id, graph, store)
          if (nextTarget._2 == -1)
            hitsLeft == 0
          else
            hitsLeft = melee(attackTemp, nextTarget._1, store.value.get(nextTarget._2))
        }
      }
    }
  }

  def findLowestHealthEnemy(id: VertexId, graph: Graph[Int, Int], store: Broadcast[StoreCreature.type]) : (VertexId, Int) = {
    //tester en filtrant le graphe au départ pour chaque créature
    //on pourrait penser à retirer les arcs des créatures mortes pour raccourcir le processus
    // cepandant dans l'optique d'implémentation des sorts qui contiennent des sorts de résurrection
    // il faudrait vérifier ce qui prendrait le plus de temps entre recréer et supprimer les arcs (qui constituent des créations de nouveaux graphes) lors de la mors et de la résurrection de créatures
    // et interroger tous les arcs àchaque itération.

    // filtrer le graphe avant pour effectuer un aggregate messages uniquement
    // sur les sommets correspondants à la créature est actuellement plus long
    val temp = graph.aggregateMessages[(VertexId, Int, Int)](
      edge => {
        val isEnemy = edge.toEdgeTriplet.attr == 0
        if ( isEnemy) {
          if ((edge.srcId == id) ){
            val key = edge.dstAttr
            val creature = store.value.get(key)

            if (!creature.isDead()) {
              edge.sendToSrc((edge.dstId, key, creature.hp))
            }
          }
          if(edge.dstId == id){
            val key = edge.srcAttr
            val creature = store.value.get(key)
            if (!creature.isDead()) {
              edge.sendToDst((edge.srcId, key, creature.hp))
            }
          }

        }
      },
      (a, b) => if (b._3 > a._3) a else b)

    val resultAggregate = temp.collect()

    if (resultAggregate.length == 0) {

      return (-1, -1)
    }

    val result = resultAggregate(0)._2
    return (result._1, result._2)
  }

  def specialEffect(specialEffect : Int, target: Creature): Unit =
  {
    specialEffect match {
      case 0 => return
      case 1 => this.vicious(target)
      case 2 => this.holy(target)
      case 3 => this.flaming(target)
    }

  }


  def vicious(target : Creature): Unit =
  {
    target.hp -= diceThrow(2,6)
    this.hp -= diceThrow(1,6)
  }

  def holy(target : Creature): Unit =
  {
    if (target.getAlignement() == "Evil")
      target.hp -= diceThrow(2,6)
  }

  def flaming(target : Creature): Unit ={
    target.hp -= diceThrow(1,6) //créer fonction pour discriminer les différents types de dommages
    //et appliquer les résistances et immunités
  }

}


