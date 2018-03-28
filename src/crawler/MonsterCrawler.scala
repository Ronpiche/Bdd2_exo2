package crawler

import scala.collection.mutable.ArrayBuffer

class MonsterCrawler extends AbstractCrawler {

  var monsters: ArrayBuffer[Monster] = ArrayBuffer[Monster]()

  override def crawlLink(link: String): Unit = {

  }

}
