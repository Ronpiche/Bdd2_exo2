package monsters

abstract class AbstractMonster extends Serializable {

  var armor : Int // Armor Point (AC)
  var hp : Int // Health Point
  var damages : Int // Damages per attack
  var reach : Int // Distance to attack
  var init : Int // Initiative : who starts first
  var name : String // Name

}
