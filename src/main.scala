import org.apache.spark.{SparkConf, SparkContext}
import org.jsoup.{HttpStatusException, Jsoup}
import org.jsoup.nodes.Element
import org.jsoup.nodes.Document

object main extends App
{
  def handleError(errorMessage: String): Unit ={
    println("Error:"+ errorMessage)
    System.exit(1)
  }

  def getJsoupDoc(url: String): Document ={
    try {
      val doc = Jsoup.connect(url).get
      val links = doc.select("a")
      return doc
    }
    catch {
      case unknown => handleError("can't connect to the website")
    }
    return null
  }

  def getMonsterData(url: String){
    var monsterDoc =getJsoupDoc(url)
    //get the monster name
    var monsterName = monsterDoc.getElementsByAttributeValue("class", "stat-block-title")
    println(monsterName.size())
  }

  //var alphabetArray = Array("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
  var alphabetArray = Array("E")//for debug; only one monster start by u (🦄)
  var indexDoc =getJsoupDoc("http://paizo.com/pathfinderRPG/prd/bestiary/monsterIndex.html")
  var letter = null
  var urlAlreadyVisited = List[String]()
  for (letter <- alphabetArray){
    var indexMonsters = indexDoc.getElementById("index-monsters-"+letter)
    // if a monster start with this letter
    if (indexMonsters != null){
      // select all the link to the monster page
      var tabMonsterLink = indexMonsters.select("a")
      //iterate though each element
      var i: Int = 0
      while (i < tabMonsterLink.size) {
        // get the url link from the element <a .. />
        var monsterLink = tabMonsterLink.get(i)
        var absLink : String = monsterLink.attr("abs:href")
        //remove the part after the # for instance : http://paizo.com/pathfinderRPG/prd/bestiary/eel.html#eel-electric ===> http://paizo.com/pathfinderRPG/prd/bestiary/eel.html
        var posHastag  = absLink.lastIndexOf("#")
        if( posHastag != -1){
          absLink = absLink.slice(0,posHastag)
        }
        //if the monster is not already visited
        if(!(urlAlreadyVisited contains absLink)){
          urlAlreadyVisited =urlAlreadyVisited :+absLink
          //use url to go to the monster data webpage
          getMonsterData(absLink)
          print(absLink)
        }
        i +=1
      }
    }
  }
}
