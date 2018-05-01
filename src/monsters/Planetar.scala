class Planetar extends Creature("Planetar") {

  var hp = 229

  val hpMax = 229

  var armor = 32

  override val regen = 10

  //val name = "Solar"

  override val damageReduction: Int = 10

  var baseMeleeAttack = Array(3, 12, 15, 3, 6, 19, 2)

  val alignment = "Good"

  val creatureType = "Angel"
}
