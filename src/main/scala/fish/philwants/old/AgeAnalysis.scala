//package fish.philwants.old
//
//import fish.philwants.old.Fight
//
//case class AgeAnalysis(ageDiffMin: Int = 1) extends GenericAnalysis {
//  def isCandidate(fight: Fight): Boolean = {
//    val resultOpt = for {
//      f1 <- fight.f1
//      f2 <- fight.f2
//      f1birthyear <- f1.birthYear
//      f2birthyear <- f2.birthYear
//    } yield {
//      val f1age = Integer.parseInt(fight.eventYear) - f1birthyear
//      val f2age = Integer.parseInt(fight.eventYear) - f2birthyear
//      Math.abs(f1age - f2age) >= ageDiffMin
//    }
//    resultOpt.getOrElse(false)
//  }
//
//  def isCorrect(fight: Fight): Boolean = {
//    if (fight.f1.get.birthYear.get > fight.f2.get.birthYear.get) fight.f1result.equals("win")
//    else fight.f2result.equals("win")
//  }
//}
