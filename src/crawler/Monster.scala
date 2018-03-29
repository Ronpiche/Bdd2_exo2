package crawler

import scala.collection.mutable.ArrayBuffer

/**
  * Represents a PathfinderRPG Monster (with list of spells)
  *
  * @param name Monster name
  * @param url  url of Monster Page
  */
class Monster(val name: String, val url: String) extends Serializable {

  var spells: ArrayBuffer[String] = ArrayBuffer[String]()

  def addSpell(spell: String): Unit = {
    spells += spell
  }

  override def toString: String = {
    "------------------- \n" + name + "\n" + url + "\n" + spells
  }
}
