package crawler

import org.jsoup.Jsoup

import scala.collection.mutable.ArrayBuffer

class BestiaryCrawler extends AbstractCrawler {

  var monsterLinks: ArrayBuffer[String] = ArrayBuffer[String]()

  override def crawlLink(link: String): Unit = {
    try {
      var conn = Jsoup.connect(link)
      var doc = conn.get()

      var hrefLinks = doc.getElementById("monster-index-wrapper").select("a[href]")

      for (i <- 0 until hrefLinks.size()) {
        monsterLinks.append(hrefLinks.get(i).attr("abs:href"))
      }

    } catch {
      case e: Exception => print(e)
    }
  }
}
