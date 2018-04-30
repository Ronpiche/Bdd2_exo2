class Solar extends Creature("Solar") {

  var hp = 363

  val hpMax = 363

  var armor = 44

  override val regen = 15

  //val name = "Solar"

  override val damageReduction: Int = 15

  var baseMeleeAttack = Array(4, 15, 18, 3, 6, 21, 0)

  val alignment = "Good"

  val creatureType = "Angel"


}
