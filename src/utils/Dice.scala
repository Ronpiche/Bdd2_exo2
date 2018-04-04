package utils

object Dice {

  var value : Int = 0

  var maxValue : Int = 20

  /**
    * Roll a dice
    *
    * @param range : max dice value
    * @return value of the dice rolled
    */
  def roll (range : Int = maxValue): Unit = {
    maxValue = range
    value = scala.util.Random.nextInt(maxValue)
  }

  /**
    * Tells if the dice is a critical success or not
    * @return true if value rolled == maxValue
    */
  def criticalSuccess(): Boolean = {
    value == maxValue
  }

}
