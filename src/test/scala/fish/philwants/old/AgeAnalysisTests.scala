//package fish.philwants
//
//import fish.philwants.old.AgeAnalysis
//import org.scalatest.{FlatSpec, Matchers}
//
//class AgeAnalysisTests extends FlatSpec with Matchers {
//  "AgeAnalysis" should "" in {
//    (1 to 20).foreach { ageMin =>
//      val aa = new AgeAnalysis(ageMin)
////      println(s"Age min:$ageMin, percent:${aa.percent}")
//    }
//  }
//
//  it should "also" in {
//    (1 to 16).foreach { ageMin =>
//      val aa = new AgeAnalysis(ageMin)
//      println(s"Age min:$ageMin, percent:${aa.percentForLast1000}")
//    }
//
//    val data = (1 to 16).map { ageMin =>
//      val aa = new AgeAnalysis(ageMin)
//      (ageMin, aa.percentSringForLast1000Simple)
//    }
//    data.foreach { case (i, s) =>
//      println(s"$i,$s")
//    }
//  }
//}
