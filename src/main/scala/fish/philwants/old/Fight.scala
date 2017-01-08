package fish.philwants.old

import java.text.SimpleDateFormat

/**
  * Created by okeefephil on 2017-01-02.
  */
case class Fight(url: String, eid: String, mid: String, event_name: String, event_org: String, event_date: String, event_place: String,
                 f1pageurl: String, f2pageurl: String, f1name:String, f2name: String, f1result: String, f2result: String,
                 f1fid: String, f2fid: String, method: String, method_d: String, ref: String, round: String, time: String) {

  val date = {
    val formatter = new SimpleDateFormat("MM/dd/yyyy")
    formatter.parse(event_date)
  }

  val dateInSeconds = date.getTime / 1000

  val eventYear = event_date.split("/").last

  lazy val f1firstfight = {
    val fights = Data.fights.filter { f => f.f1name.equals(f1name) || f.f2name.equals(f1name) }
    if (fights.head.event_date.equals(event_date)) true else false
  }

  lazy val f2firstfight = {
    val fights = Data.fights.filter { f => f.f1name.equals(f2name) || f.f2name.equals(f2name) }
    if (fights.head.event_date.equals(event_date)) true else false
  }

  val secondsInMonth = 30 * 24 * 60 * 60
  val secondsInDay = 24 * 60 * 60
  lazy val f1MonthsSinceLastFight: Option[Long] = {
    if (f1firstfight) None
    else {
      val fights = Data.fights.filter { f => f.f1name.equals(f1name) || f.f2name.equals(f1name) }
      fights.sliding(2).collectFirst {
        case fs: Vector[Fight] if fs.last.event_date.equals(event_date) => (fs.head.dateInSeconds - fs.last.dateInSeconds) / secondsInMonth
      }
    }
  }

  lazy val f1DaysSinceLastFight: Option[Long] = {
    if (f1firstfight) None
    else {
      val fights = Data.fights.filter { f => f.f1name.equals(f1name) || f.f2name.equals(f1name) }
      fights.sliding(2).collectFirst {
        case fs: Vector[Fight] if fs.last.event_date.equals(event_date) => (fs.head.dateInSeconds - fs.last.dateInSeconds) / secondsInDay
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

  lazy val f2DaysSinceLastFight: Option[Long] = {
    if (f2firstfight) None
    else {
      val fights = Data.fights.filter { f => f.f1name.equals(f2name) || f.f2name.equals(f2name) }
      fights.sliding(2).collectFirst {
        case fs: Vector[Fight] if fs.last.event_date.equals(event_date) => (fs.head.dateInSeconds - fs.last.dateInSeconds) / secondsInDay
      }
    }
  }
}
