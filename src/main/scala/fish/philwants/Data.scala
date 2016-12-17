package fish.philwants

import java.io.File
import java.text.{DateFormat, SimpleDateFormat}
import java.util.Date

import com.github.tototoshi.csv.CSVReader

import scala.util.Try


case class Fighter(url: String, id: String, name: String, nick: String, birth: String, heightString: String, weightString: String, association: String, locality: String, country: String) {
  val height = Try(Integer.parseInt(heightString)).toOption
  val weight = Try(Integer.parseInt(weightString)).toOption
}
case class Fight(url: String, eid: String, mid: String, event_name: String, event_org: String, event_date: String, event_place: String,
                 f1pageurl: String, f2pageurl: String, f1name:String, f2name: String, f1result: String, f2result: String,
                 f1fid: String, f2fid: String, method: String, method_d: String, ref: String, round: String, time: String) {
  val dateInSeconds = {
    val formatter = new SimpleDateFormat("MM/dd/yyyy")
    val date: Date = formatter.parse(event_date)
    date.getTime / 1000
  }

  lazy val f1firstfight = {
    val fights = Data.fights.filter { f => f.f1name.equals(f1name) || f.f2name.equals(f1name) }
    if (fights.head.event_date.equals(event_date)) true else false
  }

  lazy val f2firstfight = {
    val fights = Data.fights.filter { f => f.f1name.equals(f2name) || f.f2name.equals(f2name) }
    if (fights.head.event_date.equals(event_date)) true else false
  }

  val secondsInMonth = 30 * 24 * 60 * 60
  lazy val f1MonthsSinceLastFight: Option[Long] = {
    if (f1firstfight) None
    else {
      val fights = Data.fights.filter { f => f.f1name.equals(f1name) || f.f2name.equals(f1name) }
      fights.sliding(2).collectFirst {
        case fs: Vector[Fight] if fs.last.event_date.equals(event_date) => (fs.head.dateInSeconds - fs.last.dateInSeconds) / secondsInMonth
      }
    }
  }

  lazy val f2MonthsSinceLastFight: Option[Long] = {
    if (f2firstfight) None
    else {
      val fights = Data.fights.filter { f => f.f1name.equals(f2name) || f.f2name.equals(f2name) }
      fights.sliding(2).collectFirst {
        case fs: Vector[Fight] if fs.last.event_date.equals(event_date) => (fs.head.dateInSeconds - fs.last.dateInSeconds) / secondsInMonth
      }
    }
  }
}

object Data {
  // Create CSV readers for fights and fighters
  val fightersReader = CSVReader.open(new File(getClass.getClassLoader.getResource("fighters.csv").getFile))
  val fightsReader = CSVReader.open(new File(getClass.getClassLoader.getResource("fights.csv").getFile))

  // Parse csv files line-by-line into case classes
  val fighterBuilder = Vector.newBuilder[Fighter]
  fightersReader.foreach { line => fighterBuilder += Fighter(line(0),line(1),line(2),line(3),line(4),line(5),line(6),line(7),line(8),line(9)) }

  val fightsBuilder = Vector.newBuilder[Fight]
  fightsReader.foreach { line => fightsBuilder += Fight(line(0),line(1),line(2),line(3),line(4),line(5),line(6),line(7),line(8),line(9),line(10),line(11),line(12),line(13),line(14),line(15),line(16),line(17),line(18),line(19)) }

  // Provide data as case class
  val fighters = fighterBuilder.result()
  val fights = fightsBuilder.result()
}
