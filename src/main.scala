import crawler.{BestiaryCrawler, MonsterCrawler}

object main extends App {

  // Bestiary Crawling
  var bestiaryCrawler = new BestiaryCrawler
  // Setup links to Crawl
  bestiaryCrawler.addLinkToCrawl("http://paizo.com/pathfinderRPG/prd/bestiary/monsterIndex.html")
  bestiaryCrawler.addLinkToCrawl("http://paizo.com/pathfinderRPG/prd/bestiary2/additionalMonsterIndex.html")
  bestiaryCrawler.addLinkToCrawl("http://paizo.com/pathfinderRPG/prd/bestiary3/monsterIndex.html")
  bestiaryCrawler.addLinkToCrawl("http://paizo.com/pathfinderRPG/prd/bestiary4/monsterIndex.html")
  bestiaryCrawler.addLinkToCrawl("http://paizo.com/pathfinderRPG/prd/bestiary5/index.html")
  // Launch Crawling : fetch all monster links
  bestiaryCrawler.crawl()

  println("Bestiary Crawling finished - found " + bestiaryCrawler.monsterLinks.size + " monster's link")

  // Monster Crawling
  var monsterCrawler = new MonsterCrawler(bestiaryCrawler.monsterLinks)
  monsterCrawler.crawl()
  println("Monster Crawling finished - " + monsterCrawler.monsters.size + " monsters obtained")
  for (monster <- monsterCrawler.monsters) {
    println("-----------")
    println(monster.name)
    println(monster.url)
    println(monster.spells)
  }
}
