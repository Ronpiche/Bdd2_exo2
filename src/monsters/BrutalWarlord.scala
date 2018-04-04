package monsters

/**
  * Brutal Warlord
  * http://www.d20pfsrd.com/bestiary/npc-s/npc-12/brutal-warlord-half-orc-fighter-13/
  */
class BrutalWarlord extends AbstractMonster {

  override var armor: Int = 27
  override var hp: Int = 141
  override var damages: Int = 34
  override var init: Int = 2
  override var name: String = "Brutal Warlord"
  override var meleeHit: Int = 13
}
