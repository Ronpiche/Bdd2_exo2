package crawler

import scala.collection.mutable.ArrayBuffer

abstract class AbstractCrawler {

  var links: ArrayBuffer[String] = ArrayBuffer[String]()

  def addLinkToCrawl(url: String): Unit = {
    links += url
  }

  def crawl(): Unit = {
    for (link <- links) {
      crawlLink(link)
    }
  }

  protected def crawlLink(link: String)

}
