package battle

import monsters.AbstractMonster

import scala.collection.mutable.ArrayBuffer

class Battle {

  var currentRound: Int = 1
  val MAX_ROUND = 3 // Maximum number of round / 0 = no limit

  var monsters: ArrayBuffer[AbstractMonster] = ArrayBuffer[AbstractMonster]()

  def this(m: ArrayBuffer[AbstractMonster]) {
    this
    monsters = m.sortWith(_.init > _.init) // Sort monsters by initiative
  }

  def gameFinished(): Boolean = {
    MAX_ROUND != 0 && currentRound > MAX_ROUND
  }

  def start(): Unit = {
    while (!gameFinished()) {
      println("[-------------------- ROUND n°" + currentRound + " --------------------]")
      for (currentPlayer <- monsters) {
        if (currentPlayer.canPlay) {
          currentPlayer.play()
        }
      }
      currentRound += 1
    }
  }
}
