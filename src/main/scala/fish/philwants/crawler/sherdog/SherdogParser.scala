package fish.philwants.crawler.sherdog

import com.typesafe.scalalogging.LazyLogging
import fish.philwants.crawler._
import fish.philwants.data._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

import scala.collection.JavaConverters._
import scala.util.Try

/**
  * This case class is used when parsing Event pages from Sherdog. This is only meant to be used during parsing. Use
  * `Event` for all other purposes.
  */
private case class EventString(date: String, name: String, title: String, loc: String, url: String)


object SherdogParser extends LazyLogging {

  def events(eventPageResponse: String): Vector[Event] = {
    // Parse the String into a Jsoup Document
    val doc = Jsoup.parse(eventPageResponse)

    // Use CSS selectors to select each row in the table of event listings
    val div_recentfights = doc.select("div[id=recentfights_tab]").asScala
    val tables = div_recentfights.head.children().select("table").asScala
    val eventTable: Element = tables.head
    val rows = eventTable.select("tr[itemscope]").asScala

    // Parse each row into an EventString class. These will be easier to work with compared to tuples
    val eventStrings = rows.map { r =>
      val tds = r.children.asScala.toVector
      val url = "http://www.sherdog.com" + tds(2).children.asScala.head.attr("href")
      EventString(tds(0).text, tds(1).text, tds(2).text, tds(3).text, url)
    }

    // Transform EventString into Event. This will convert String into better types
    val events = eventStrings.map { es =>
      val date = DateTime.parse(es.date, DateTimeFormat.forPattern("MMMDDyyyy"))
      Event(date, Organization.mkOrganization(es.name), es.name, es.title, es.loc, es.url)
    }

    logger.info(s"Parsed ${events.size} events")
    events.toVector
  }

  def fights(cardPageResponse: String, event: Event): Vector[Fight] = {
    val resultRegex = "(win|loss|NC|draw)"
    val nameRegex = "((?:\\S+ )+\\S+)"
    val refRegex = "((?:(?:\\S+ )+\\S+)|N/A|\\S+)"
    val timeRegex = "((?:\\d+:\\d+)|(?:N/A))"

    val doc = Jsoup.parse(cardPageResponse)

    // The headline fight has special html structure
    val div = doc.select("div[class=fight")
    val table2 = doc.select("table[class=resume")


    val fighter1_div = div.select("div[class=fighter left_side")
    val fighter2_div = div.select("div[class=fighter right_side")

    val fighter1 = fighter1_div.select("a").text()
    val fighter2 = fighter2_div.select("a").text()
    val f1result = fighter1_div.select("span").asScala(1).text()
    val f2result = fighter2_div.select("span").asScala(1).text()
    val fighter1url = fighter1_div.select("a").first.attr("href")
    val fighter2url = fighter2_div.select("a").first.attr("href")

    val headlineResultPattern = s"Match \\d+ Method (.+) \\((.+)\\) Referee $refRegex Round (\\d+) Time $timeRegex".r
    val headlineResultPattern2 = s"Match \\d+ Method (.+) Referee $refRegex Round (\\d+) Time $timeRegex".r
    val headline_fight = table2.text() match {
      case headlineResultPattern(method, sub_method, ref, round, time) => {
        Some(FightString(fighter1, f1result, fighter2, f2result, method, sub_method, ref, round, time, fighter1url, fighter2url, event))
      }
      case headlineResultPattern2(method, ref, round, time) => {
        Some(FightString(fighter1, f1result, fighter2, f2result, method, "", ref, round, time, fighter1url, fighter2url, event))

      }
      case e => {
        logger.error(s"Couldn't match $e")
        None
      }
    }

    // Rows contains the rest of the fight
    val table = doc.select("table").asScala.last
    val rows = table.select("tr[itemscope]").asScala

    val fight_pattern = s"\\d+ $nameRegex $resultRegex vs $nameRegex $resultRegex (\\S+(?: \\S+)?) \\((.+)\\) $refRegex (\\d) $timeRegex".r

    val fightStrings = rows.flatMap { r =>
      r.text() match {
        case fight_pattern(fighter1, f1result, fighter2, f2result, method, sub_method, ref, round, time) => {
          val urlnodes = r.select("div[class=fighter_result_data] > a").asScala
          val fighter1url = urlnodes(0).attr("href")
          val fighter2url = urlnodes(1).attr("href")
          Some(FightString(fighter1, f1result, fighter2, f2result, method, sub_method, ref, round, time, fighter1url, fighter2url, event))
        }
        case _ => {
          logger.error(s"No match for ${r.text()}")
          None
        }
      }
    }

    val allFightStrings = Vector(headline_fight.get) ++ fightStrings.toVector
    logger.info(s"Parsed ${allFightStrings.size} fights")
    allFightStrings.flatMap(DataTypes.FightStringToFight)
  }

  def fighter(resp: String): Fighter = {
    val doc = Jsoup.parse(resp)

    val name = doc.select("h1[itemprop=name] > span[class=fn]").text()
    val nick = doc.select("h1[itemprop=name] > span[class=nickname]").text()

    // 1974-09-10
    val birthString = doc.select("span[itemprop=birthDate]").text()

    // Calcualte age based on birthday and current day
    val age = doc.select("span[class=item birthday] > strong").text().split(" ").last


    val country = doc.select("strong[itemprop=nationality]").text()

    val association = doc.select("a[class=association] > span[itemprop=name]").text()
    val url = doc.select("meta[property=og:url]").attr("content")

    val birth = Try(DateTime.parse(birthString, DateTimeFormat.forPattern("YYYY-MM-dd"))).toOption

    val heightString = doc.select("span[class=item height] > strong").text()
    val height = {
      val heightPattern = "(\\d+)'(\\d+)\"".r
      heightString match {
        case heightPattern(feet, inches) => Some((Integer.parseInt(feet) * 12) + Integer.parseInt(inches))
        case _ => None
      }
    }

    val weightString = doc.select("span[class=item weight] > strong").text().split(" ").head
    val weight = {
      Try(Integer.parseInt(weightString)).toOption
    }

    Fighter(url, name, birth, height, weight, association, country, DateTime.now())
  }
}

