//package fish.philwants
//
//import fish.philwants.old.HeightAnalysis
//
//import scalax.chart.api._
//import org.scalatest.{FlatSpec, Matchers}
//
//class HeightAnalysisTests extends FlatSpec with Matchers {
//
//  "HeightAnalysis" should "print total results" in {
//    for(h <- 1 to 10) {
//      print(s"Results for height diff of $h: ")
//      val ha = HeightAnalysis(h)
//      val fights = Data.fights.filter { f => ha.isCandidate(f)}
//      val totalResults = fights.map { f => ha.isCorrect(f) }
//      print(s"Wins:${totalResults.count(r => r)},")
//      print(s"Losses:${totalResults.count(r => !r)}")
//      println(s"Percentage ${(totalResults.count(r => r).toDouble / totalResults.size) * 100 }")
//    }
//  }
//
//  it should "create graphs" in {
//    val data = (1 to 10).map { h =>
//      val ha = new HeightAnalysis(h)
//      val fights = Data.fights.filter { f => ha.isCandidate(f)}
//      val totalResults = fights.map { f => ha.isCorrect(f) }
//      (h, (totalResults.count(r => r).toDouble / totalResults.size) * 100)
//    }
//
//    val chart = XYAreaChart(data)
//    chart.saveAsPNG("chart.png")
//  }
//
//  it should "provide a good predictor" in {
//    val ha = new HeightAnalysis()
//    val fights = Data.fights.filter { f => ha.isCandidate(f)}
//    val totalResults = fights.map { f => ha.isCorrect(f) }
//    val percent = (totalResults.count(r => r).toDouble / totalResults.size) * 100
//    println(s"Corrected predicted $percent percent fights")
//    percent should be > 55.0
//  }
//
////  it should "" in {
////    val fights = Data.fights.filter { f => HeightAnalysis.isCandidate(f, 9)}
////    fights.foreach { f =>
////      print(s"Event: ${f.event_name} -- Fighters: ")
////      print(s" ${f.f1name} vs ${f.f2name}")
////      val winner = if (f.f1result.equals("win")) f.f1name else f.f2name
////      println(s". Winner: ${winner}")
////    }
////  }
//
//}
