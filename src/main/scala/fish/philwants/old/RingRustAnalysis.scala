package fish.philwants.old

import org.jfree.data.general.DefaultKeyedValues2DDataset

case class RingRustAnalysis(timeMin: Int = 12, timeMax: Int = 12*4) extends GenericAnalysis {
  // If one fighter has been out longer than `time` than the other, then we should use it for this metric
  def isCandidate(fight: Fight): Boolean = {
    val result = for {
      f1mon <- fight.f1MonthsSinceLastFight
      f2mon <- fight.f2MonthsSinceLastFight
    } yield Math.abs(f1mon - f2mon) >= timeMin && Math.abs(f1mon - f2mon) <= timeMax
    result.getOrElse(false)
  }

  // Check if being out has an affect on the outcome
  def isCorrect(fight: Fight): Boolean = {
    // Check which fighter has been out longer
    if (fight.f1MonthsSinceLastFight.get > fight.f2MonthsSinceLastFight.get) {
      fight.f1result == "win"
    } else {
      fight.f2result == "win"
    }
  }

}


object RingRustAnalysis {
  def createDataset(data: Map[Long, Vector[Boolean]]): DefaultKeyedValues2DDataset = {
    val dataset = new DefaultKeyedValues2DDataset()

    data.toSeq.sortBy(_._1).foreach { case (month, results) =>
      dataset.addValue(results.count(r => r), month, "win")
      dataset.addValue(results.count(r => !r), month, "loss")
    }

    dataset
  }

  val rra = RingRustAnalysis()
  val fights = Data.fights.filter { f => rra.isCandidate(f) }
  val monthlyResults = fights
    .groupBy { f => Math.abs(f.f1MonthsSinceLastFight.get - f.f2MonthsSinceLastFight.get) }
    .toSeq
    .sortBy(_._1)
    .map { case (month, fs) => (month, fs.map(f => rra.isCorrect(f))) }

  val totalResults = fights.map { f => rra.isCorrect(f) }
}
