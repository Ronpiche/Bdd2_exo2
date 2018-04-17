package monsters

/**
  * Barbarian Orc
  * http://www.d20pfsrd.com/bestiary/npc-s/npc-10/double-axe-fury-half-orc-barbarian-11/
  */
class OrcBarbarian extends AbstractMonster {

  override var armor: Int = 17
  override var hp: Int = 142
  override var damages: Int = 14
  override var init: Int = 4
  override var name: String = "Orc Barbarian Double Axe Fury"
  override var meleeHit: Int = 11
}
