package fish.philwants

import DataHelper._

object HeightAnalysis {
  def isCandidate(fight: Fight, heightDiffMin: Int = 3): Boolean = {
    val result = for {
      f1 <- fight.f1()
      f2 <- fight.f2()
      f1height <- f1.height
      f2height <- f2.height
    } yield Math.abs(f1height - f2height) > heightDiffMin

    result.getOrElse(false)
  }

  def isCorrect(fight: Fight): Boolean = {
    val f1IsWinner = fight.f1.get.height.get > fight.f2.get.height.get
    if (f1IsWinner) fight.f1result.equals("win")
    else fight.f2result.equals("win")
  }

  val fights = Data.fights.filter { f => isCandidate(f) }
  val totalResults = fights.map { f => isCorrect(f) }
}


