package fish.philwants.analysis

import fish.philwants.old.Fight

object RingRustAnalysis2 {
  /**
    * This function transforms a fight into a data point consisting of the timeout difference relative to the winner and
    * the result of the fighter thats been out longer as a 1 or 0.
    *
    * Did the fighter who was longer win?
    *
    * @param fight The fight to transform
    * @return The timeout difference and result relative to the fighter out longer
    */
  def dataPoint(fight: Fight): Option[(Long, String)] = {
    for {
      f1mon <- fight.f1MonthsSinceLastFight
      f2mon <- fight.f2MonthsSinceLastFight
      if fight.f1result.equals("win") || fight.f1result.equals("loss") // ignore NC and draws for now
    } yield {
      // (timeout, won)
      val timeout = Math.abs(f1mon - f2mon)
      val isF1OutLonger = f1mon > f2mon
      val isF1Winner = fight.f1result.equals("win")

      if (f1mon > f2mon) {
        if (isF1Winner) (timeout, "1")
        else (timeout, "0")
      } else {
        if (isF1Winner) (timeout, "0")
        else (timeout, "1")
      }
    }
  }

  def dataPointForDays(fight: Fight): Option[(Long, String)] = {
    for {
      f1mon <- fight.f1DaysSinceLastFight
      f2mon <- fight.f2DaysSinceLastFight
      if fight.f1result.equals("win") || fight.f1result.equals("loss") // ignore NC and draws for now
    } yield {
      // (timeout, won)
      val timeout = Math.abs(f1mon - f2mon)
      val isF1OutLonger = f1mon > f2mon
      val isF1Winner = fight.f1result.equals("win")

      if (f1mon > f2mon) {
        if (isF1Winner) (timeout, "1")
        else (timeout, "0")
      } else {
        if (isF1Winner) (timeout, "0")
        else (timeout, "1")
      }
    }
  }
}

