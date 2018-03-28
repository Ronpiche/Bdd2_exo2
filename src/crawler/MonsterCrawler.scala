package crawler

import org.jsoup.Jsoup

import scala.collection.mutable.ArrayBuffer

class MonsterCrawler extends AbstractCrawler {

  def this(monsterLinks: ArrayBuffer[String]) = {
    this()
    links = monsterLinks
  }

  var monsters: ArrayBuffer[Monster] = ArrayBuffer[Monster]()

  override def crawlLink(link: String): Unit = {

    // If link doesn't contain #, it is incorrect we don't process it
    if (!link.contains("#")) {
      return
      //      throw new IllegalArgumentException(link + " : Link doesn't contain #")
    }

    // Get the name of the Monster from the link
    val name = link.split("#")(1)
    var monster = new Monster(name, link)

    // Crawl spells
    try {
      var conn = Jsoup.connect(link)
      var fullPage = conn.get()
      val baseUri = fullPage.baseUri() // Needed because of the split

      // We split the page by h1, in order to process the pages with multiple monsters on it
      val h1Split = fullPage.toString.split("<h1 ")

      for (h1Siblings <- h1Split) {
        if (h1Siblings.startsWith("id=\"" + name + "\"")) {
          var doc = Jsoup.parse(h1Siblings)
          doc.setBaseUri(baseUri) // Needed to use abs:href below
          var spellsList = doc.select("a[href*=/spells/]").eachAttr("abs:href")

          for (i <- 0 until spellsList.size()) {
            monster.addSpell(spellsList.get(i))
          }

        }
      }

    } catch {
      case e: Exception => println(e)
    }

    // We finally add the monster properly set up to the list
    monsters.append(monster)
  }

}
