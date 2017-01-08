package fish.philwants.analysis

import fish.philwants.old.DataHelper._
import fish.philwants.old.Fight

object AgeAnalysis2 {
  /**
    * This function transforms a fight into a data point that encodes the age difference between the fighters relative
    * to the winner, and the fight result of the older fighter as a 1 or 0.
    * @param fight The fight to transform
    * @return A tuple consisting of the age difference and the result
    */
  def dataPoint(fight: Fight): Option[(Any, String)] = {
    for {
      f1 <- fight.f1
      f2 <- fight.f2
      f1birthyear <- f1.birthYear
      f2birthyear <- f2.birthYear
    } yield {
      val f1age = Integer.parseInt(fight.eventYear) - f1birthyear
      val f2age = Integer.parseInt(fight.eventYear) - f2birthyear
      if (fight.f1result.equals("win")) (f1age - f2age, "1")
      else (f2age - f1age, "0")
    }
  }
}
