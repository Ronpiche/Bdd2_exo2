class MovanicDeva extends Creature("Monavic Deva") {

  var hp = 126

  val hpMax = 126

  var armor = 24

  //override val regen = 0

  //val name = "Solar"

  override val damageReduction: Int = 10

  var baseMeleeAttack = Array(3, 2, 7, 2, 6, 19, 3)

  val alignment = "Good"

  val creatureType = "Angel"


}
