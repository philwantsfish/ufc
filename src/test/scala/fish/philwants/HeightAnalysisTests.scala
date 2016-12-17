package fish.philwants

import org.scalatest.{FlatSpec, Matchers}

class HeightAnalysisTests extends FlatSpec with Matchers {

  "HeightAnalysis" should "print total results" in {

    for(h <- 1 to 10) {
      print(s"Results for height diff of $h: ")
      val fights = Data.fights.filter { f => HeightAnalysis.isCandidate(f, h)}
      val totalResults = fights.map { f => HeightAnalysis.isCorrect(f) }
      print(s"Wins:${totalResults.count(r => r)},")
      print(s"Losses:${totalResults.count(r => !r)}")
      println(s"Percentage ${(totalResults.count(r => r).toDouble / totalResults.size) * 100 }")
    }

  }
}
