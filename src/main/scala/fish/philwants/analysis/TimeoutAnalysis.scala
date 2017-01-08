package fish.philwants.analysis

import fish.philwants.csv.Csv
import fish.philwants.data.{Fight, Fighter, FighterService}
import org.joda.time.Duration

object TimeoutAnalysis {

  val allFights = Csv.read[Fight]("./data/fights.csv")

  def datapoint(f: Fight): Vector[Option[(Long, Boolean)]] = {
    val winnerData = for {
      winner <- FighterService.fighter(f.winner)
      loser <- FighterService.fighter(f.loser)
      f1Duration <- timeSinceLastFight(winner, f)
      f2Duration <- timeSinceLastFight(loser, f)
    } yield {
      ((f1Duration.getStandardDays - f2Duration.getStandardDays) / 30, true)
    }

    val loserData = for {
      winner <- FighterService.fighter(f.winner)
      loser <- FighterService.fighter(f.loser)
      f1Duration <- timeSinceLastFight(winner, f)
      f2Duration <- timeSinceLastFight(loser, f)
    } yield {
      ((f2Duration.getStandardDays - f1Duration.getStandardDays) / 30, false)
    }

    Vector(winnerData, loserData)
  }


  def isFirstFight(fighter: Fighter, fight: Fight): Boolean = {
    val fights = allFights.filter { f => f.winner.equals(fighter.name) || f.loser.equals(fighter.name) }
    fights.head.event.date.equals(fight.event.date)
  }

  def timeSinceLastFight(fighter: Fighter, fight: Fight): Option[Duration] = {
    val fights = allFights.filter { f => f.winner.equals(fighter.name) || f.loser.equals(fighter.name) }
    fights.sliding(2).collectFirst {
      case fs: Vector[Fight] if fs.size == 2 && fs.last.event.date.equals(fight.event.date) =>
        new Duration(fs.head.event.date.toInstant, fs.last.event.date.toInstant)
    }
  }
}
