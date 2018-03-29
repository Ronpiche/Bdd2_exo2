import java.util.Calendar

import crawler.{BestiaryCrawler, MonsterCrawler}
import org.apache.spark.{SparkConf, SparkContext}

object Exercise1 extends App {

  println(Calendar.getInstance().getTime)

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

  println("Bestiary Crawling finished - found " + bestiaryCrawler.monsterLinks.size + " monster's links")

  // Monster Crawling
  var monsterCrawler = new MonsterCrawler(bestiaryCrawler.monsterLinks)
  monsterCrawler.crawl()
  val monsters = monsterCrawler.monsters
  println("Monster Crawling finished - " + monsters.size + " monsters obtained")

  // Spark Set Up
  val conf = new SparkConf().setAppName("Exercise1").setMaster("local[*]")
  val sc = new SparkContext(conf)
  sc.setLogLevel("WARN")

  // Rdd : inverse the list of spells by monster to a list of monsters by spell
  val rddMonsters = sc.parallelize(monsters)
  val rddMonstersBySpell = rddMonsters.flatMap(
    monster => monster.spells.map(spells => (spells, monster.name)
    )).reduceByKey((x, y) => x + "***" + y)

  // Save BatchView result into folder BatchView
  rddMonstersBySpell.saveAsTextFile("BatchView")
  // Print Results in console
  //  rddMonstersBySpell.foreach(println(_))

  println(Calendar.getInstance().getTime)
}
