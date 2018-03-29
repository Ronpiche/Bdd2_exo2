package crawler

import scala.collection.mutable.ArrayBuffer

abstract class AbstractCrawler {

  var links: ArrayBuffer[String] = ArrayBuffer[String]()

  def addLinkToCrawl(url: String): Unit = {
    links += url
  }

  def crawl(): Unit = {
    var nbLinks = links.size
    println("Crawling " + nbLinks + " link(s) ...")
    var i = 1
    for (link <- links) {
      crawlLink(link)
      printf("\r %3d%%", i*100/nbLinks); // Percentage
      i += 1
    }
    println("\nCrawling finished")
  }

  protected def crawlLink(link: String)

}
