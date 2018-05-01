class AngelSlayer() extends Creature("Angel Slayer") {

  var armor = 26

  val hpMax = 112

  var hp = 112

  //val name = "Barbare Orc"

  var baseMeleeAttack = Array(3, 6, 7, 1, 8, 19, 0)

  val alignment = "Evil"

  val creatureType = "Orc"

  override val favouredEnemy = ("Angel",8)

}
