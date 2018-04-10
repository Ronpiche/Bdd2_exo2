package monsters

import utils.Dice

/**
  * Solar
  * http://www.d20pfsrd.com/bestiary/monster-listings/outsiders/angel/solar/
  */
class Solar extends AbstractMonster {

  override var armor: Int = 44
  override var hp: Int = 363
  override var damages: Int = 35
  override var init: Int = 9
  override var name: String = "Angel Solar"
  override var meleeHit: Int = 22
  var rangeHit: Int = 22

  /**
    * Rnage attack
    * @param opponent target
    */
  def rangeAttack(opponent: AbstractMonster): Unit = {
    for (_ <- 0 to nbAttacks) {
      Dice.roll() // Roll the hit chance
      if (Dice.criticalSuccess || Dice.value + rangeHit > opponent.armor) {
        opponent.hurt(damages)
      }
    }
  }
}
