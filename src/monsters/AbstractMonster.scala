package monsters

import utils.Dice

abstract class AbstractMonster extends Serializable {

  var armor: Int // Armor Point (AC)
  var hp: Int // Health Point
  var damages: Int // Damages per attack
  var meleeHit: Int // Melee Hit
  var init: Int // Initiative : who starts first
  var name: String // Name
  var nbAttacks: Int = 1 // Number of attacks

  /**
    * Melee attack
    * @param opponent target
    */
  def meleeAttack(opponent: AbstractMonster): Unit = {
    for (_ <- 0 to nbAttacks) {
      Dice.roll() // Roll the hit chance
      if (Dice.criticalSuccess || Dice.value + meleeHit > opponent.armor) {
        opponent.hurt(damages)
      }
    }
  }

  /**
    * Taking damages
    *
    * @param damages Damages to take
    */
  def hurt(damages: Int): Unit = {
    hp = scala.math.max(0, hp - damages)
    if (hp == 0) {
      die()
    }
  }

  /**
    * Monster is dead
    */
  def die(): Unit = {
    println(name + " died")
  }
}
