package fish.philwants.data

import fish.philwants.crawler.Organization
import fish.philwants.csv._
import org.joda.time.DateTime

object DataTypes {
  def FightStringToFight(f: FightString): Option[Fight] = {
    val winner = if (f.f1result.equals("win")) Some(f.fighter1)
    else if (f.f2result.equals("win")) Some(f.fighter2)
    else None

    val loser = if (f.f1result.equals("loss")) Some(f.fighter1)
    else if (f.f2result.equals("loss")) Some(f.fighter2)
    else None

    val round = Integer.parseInt(f.round)

    for {
      w <- winner
      l <- loser
    } yield Fight(w, l, f.method, f.sub_method, f.ref, round, f.time, f.fighter1url, f.fighter2url, f.event)
  }

}

case class Event(date: DateTime, org: Organization, name: String, title: String, loc: String, url: String)

case class FightString(fighter1: String, f1result: String, fighter2: String, f2result: String, method: String, sub_method: String, ref: String, round: String, time: String, fighter1url: String, fighter2url: String, event: Event)
case class Fight(winner: String, loser: String, method: String, sub_method: String, ref: String, round: Int, time: String, fighter1url: String, fighter2url: String, event: Event)

case class Fighter(url: String, name: String, birth: Option[DateTime], height: Option[Int], weight: Option[Int], association: String, country: String, lastUpdated: DateTime)


object Event {
  implicit val csvImplicit = new EventCsvReaderWriter
}


object FightString {
  implicit val csvImplicit = new FightStringCsvReaderWriter
}

object Fight {
  implicit val csvImplicit = new FightCsvFormatter
}

object Fighter {
  implicit val csvImplicit = new FighterCsvFormatter
}
