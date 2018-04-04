package monsters

/**
  * Orc Worg Rider
  * http://www.d20pfsrd.com/bestiary/npc-s/npc-1/orc-worg-rider/
  */
class OrcWorgRider extends AbstractMonster {

  override var armor: Int = 18
  override var hp: Int = 13
  override var damages: Int = 14
  override var init: Int = 2
  override var name: String = "Orc Worg Rider"
  override var meleeHit: Int = 2
}
