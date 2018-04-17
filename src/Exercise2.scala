import battle.Battle
import monsters._

import scala.collection.mutable.ArrayBuffer

object Exercise2 extends App {

  var monsters : ArrayBuffer[AbstractMonster] = ArrayBuffer[AbstractMonster]()

  for( a <- 1 to 9){
    monsters.append(new OrcWorgRider)
  }
  for( a <- 1 to 4){
    monsters.append(new OrcBarbarian)
  }
  monsters.append(new BrutalWarlord)
  monsters.append(new Solar)

  var battle = new Battle(monsters)

  battle.start()
}
