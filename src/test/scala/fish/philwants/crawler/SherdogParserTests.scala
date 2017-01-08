package fish.philwants.crawler

import java.io.{BufferedWriter, File, FileWriter}

import fish.philwants.crawler.sherdog.{SherdogParser, SherdogRequests}
import fish.philwants.data.Event
import org.joda.time.DateTime
import org.jsoup.Jsoup
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

class SherdogParserTests extends FlatSpec with Matchers {
  val dummyEvent = Event(new DateTime, UFC, "", "", "", "")

  def writeToFile(path: String, text: String): Unit = {
    val file = new File(path)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(text)
    bw.close()
  }

  "SherdogCrawler" should "retrieve the events page" in {
//    val response = SherdogRequests.events_page(125)
//    val response = SherdogRequests
//    val response = Jsoup.connect("http://www.sherdog.com/events/UFC-107-Penn-vs-Sanchez-11118").execute().body
//    writeToFile("UFC_107_Card.html", response)
  }

  "mkOrganization" should "convert event titles into organizations" in {
    Organization.mkOrganization("UFC 207") shouldBe UFC
    Organization.mkOrganization("Rizin 3") shouldBe Rizin
    Organization.mkOrganization("Joe's Backyard Brrrrrawl") shouldBe Unknown
  }

  "SherdogParser" should "parse an event listing" in {
    val responseText = Source.fromResource("eventResponse.html").mkString
    val events = SherdogParser.events(responseText)
    events.size shouldBe 5

    val knownOrgEvents = events.filterNot( e => e.org == Unknown)
    knownOrgEvents.head.org shouldBe UFC
    knownOrgEvents.last.org shouldBe Rizin
  }

  it should "work for Sherdogs pagination" in {
    val responseText = Source.fromResource("eventResponse_125.html").mkString
    val events = SherdogParser.events(responseText)
    events.size shouldBe 146
    events.filterNot( e => e.org == Unknown).size shouldBe 5
  }

  "SherdogParser" should "parse an event page" in {
    val event_page = Source.fromResource("UFC_207_Card.html").mkString
    val fights = SherdogParser.fights(event_page, dummyEvent)
    fights.size shouldBe 9
  }

  it should "parse another event page" in {
    val event_page = Source.fromResource("UFC_107_Card.html").mkString
    val fights = SherdogParser.fights(event_page, dummyEvent)
    fights.size shouldBe 11
  }

  it should "parse a fighter's page" in {
    val fighter_page = Source.fromResource("MirkoFilipovic.html").mkString
    val f = SherdogParser.fighter(fighter_page)
  }
}
