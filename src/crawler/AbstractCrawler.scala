package crawler

import scala.collection.mutable.ArrayBuffer

abstract class AbstractCrawler {

  var links: ArrayBuffer[String] = ArrayBuffer[String]()

  def addLinkToCrawl(url: String): Unit = {
    links += url
  }

  def crawl(): Unit = {
    println(links.size + " links to Crawl")
    for (link <- links) {
      crawlLink(link)
    }
    println("Crawling finished")
  }

  protected def crawlLink(link: String)

}
