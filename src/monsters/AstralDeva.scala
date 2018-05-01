class AstralDeva extends Creature("Astral Deva") {

  var hp = 172

  val hpMax = 172

  var armor = 29

  //override val regen = 10

  //val name = "Solar"

  override val damageReduction: Int = 10

  var baseMeleeAttack = Array(3, 11, 14, 1, 8, 20, 2)

  val alignment = "Good"

  val creatureType = "Angel"

}
