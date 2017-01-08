//package fish.philwants.old
//
//import fish.philwants.{Data, Fight}
//
//case class HeightAnalysis(heightDiffMin: Int = 8) extends GenericAnalysis {
//  def isCandidate(fight: Fight): Boolean = {
//    val result = for {
//      f1 <- fight.f1
//      f2 <- fight.f2
//      f1height <- f1.height
//      f2height <- f2.height
//    } yield Math.abs(f1height - f2height) >= heightDiffMin
//
//    result.getOrElse(false)
//  }
//
//  def isCorrect(fight: Fight): Boolean = {
//    val f1IsWinner = fight.f1.get.height.get > fight.f2.get.height.get
//    if (f1IsWinner) fight.f1result.equals("win")
//    else fight.f2result.equals("win")
//  }
//}
//
//object HeightAnalysis {
//  val ha = HeightAnalysis()
//  val fights = Data.fights.filter { f => ha.isCandidate(f) }
//  val totalResults = fights.map { f => ha.isCorrect(f) }
//
//}
