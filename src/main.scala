import crawler.{BestiaryCrawler, MonsterCrawler}

object main extends App
{

  // Bestiary Crawler
  var bestiaryCrawler = new BestiaryCrawler
  // Setup links to Crawl
  bestiaryCrawler.addLinkToCrawl("http://paizo.com/pathfinderRPG/prd/bestiary/monsterIndex.html")
  bestiaryCrawler.addLinkToCrawl("http://paizo.com/pathfinderRPG/prd/bestiary2/additionalMonsterIndex.html")
  bestiaryCrawler.addLinkToCrawl("http://paizo.com/pathfinderRPG/prd/bestiary3/monsterIndex.html")
  bestiaryCrawler.addLinkToCrawl("http://paizo.com/pathfinderRPG/prd/bestiary4/monsterIndex.html")
  bestiaryCrawler.addLinkToCrawl("http://paizo.com/pathfinderRPG/prd/bestiary5/index.html")
  // Launch Crawling : fetch all monster links
  bestiaryCrawler.crawl()

  // Monster Crawler
  var monsterCrawler = new MonsterCrawler
  monsterCrawler.links = bestiaryCrawler.monsterLinks
  // Todo GM : Monster Crawling
  // monsterCrawler.crawl()

}
