package fish.philwants.csv

import fish.philwants.crawler.Organization
import fish.philwants.data._
import org.joda.time.DateTime

import scala.util.Try

class EventCsvReaderWriter extends CsvFormatter[Event] {
  def toCSV(event: Event): Vector[String] = {
    Vector(event.date.toString, event.org.toString, event.name, event.title, event.loc, event.url)
  }

  def fromCSV(data: Vector[String]): Event = {
    val date = new DateTime(data(0))
    val org = Organization.mkOrganization(data(1))
    Event(date, org, data(2), data(3), data(4), data(5))
  }
}

class FightStringCsvReaderWriter extends CsvFormatter[FightString] {
  def toCSV(fight: FightString): Vector[String] = {
    val eventFormatter = new EventCsvReaderWriter
    Vector(fight.fighter1, fight.f1result, fight.fighter2, fight.f2result, fight.method, fight.sub_method, fight.ref, fight.round, fight.time, fight.fighter1url, fight.fighter2url) ++ eventFormatter.toCSV(fight.event)
  }

  def fromCSV(data: Vector[String]): FightString = {
    val eventFormatter = new EventCsvReaderWriter
    val event = eventFormatter.fromCSV(data.takeRight(6))
    FightString(data(0), data(1), data(2), data(3), data(4), data(5), data(6), data(7), data(8), data(9), data(10), event)
  }
}

//(winner: String, loser: String, method: String, sub_method: String, ref: String, round: Int, time: String, fighter1url: String, fighter2url: String, event: Event)
class FightCsvFormatter extends CsvFormatter[Fight] {
  def toCSV(fight: Fight): Vector[String] = {
    val eventFormatter = new EventCsvReaderWriter
    Vector(fight.winner, fight.loser, fight.method, fight.sub_method, fight.ref, fight.round.toString, fight.time, fight.fighter1url, fight.fighter2url) ++ eventFormatter.toCSV(fight.event)
  }

  def fromCSV(data: Vector[String]): Fight = {
    val eventFormatter = new EventCsvReaderWriter
    val event = eventFormatter.fromCSV(data.takeRight(6))
    Fight(data(0), data(1), data(2), data(3), data(4), Integer.parseInt(data(5)), data(6), data(7), data(8), event)
  }
}


class FighterCsvFormatter extends CsvFormatter[Fighter] {
  def toCSV(fighter: Fighter): Vector[String] = {
    val birth = fighter.birth.getOrElse("").toString
    val height = fighter.height.map(_.toString).getOrElse("")
    val weight = fighter.weight.map(_.toString).getOrElse("")
    Vector(fighter.url, fighter.name, birth, height, weight, fighter.association, fighter.country, fighter.lastUpdated.toString)
  }

  def fromCSV(data: Vector[String]): Fighter = {
    val birth = Try(new DateTime(data(2))).toOption
    val height = Try(Integer.parseInt(data(3))).toOption
    val weight = Try(Integer.parseInt(data(4))).toOption
    val lastUpdated = new DateTime(data(7))
    Fighter(data(0), data(1), birth, height, weight, data(5), data(6), lastUpdated)
  }
}
