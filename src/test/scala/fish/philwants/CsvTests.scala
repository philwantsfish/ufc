package fish.philwants

import java.io.File

import fish.philwants.crawler.sherdog.SherdogParser
import fish.philwants.crawler.UFC
import fish.philwants.csv.Csv
import fish.philwants.data._
import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}
import scala.io.Source

class CsvTests extends FlatSpec with Matchers {
  "Csv" should "read and write events as csv" in {
    val events = SherdogParser.events(Source.fromResource("eventResponse.html").mkString)

    // Write events to a temporary file
    val f = File.createTempFile("EventToCsvTest", ".csv")
    Csv.write(f.getAbsolutePath, events)

    // Read events back from the temporary file
    val events2 = Csv.read[Event](f.getAbsolutePath)

    // Confirm the csv read/write did not alter any of the events
    events shouldBe events2
  }

  it should "read and write fights as csv" in {
    val dummyEvent = new Event(new DateTime, UFC, "", "", "", "")
    val fights = SherdogParser.fights(Source.fromResource("UFC_207_Card.html").mkString, dummyEvent)

    // Write fights in csv form into a temporary file
    val f = File.createTempFile("FightToCsvTest", ".csv")
    Csv.write(f.getAbsolutePath, fights)

    // Read the fights back from the csv file
    val fights2 = Csv.read[Fight](f.getAbsolutePath)

    // Confirm the read/write did not alter the data
    fights shouldBe fights2
  }

  it should "read and write fighters in csv" in {
    val fighter = Vector(SherdogParser.fighter(Source.fromResource("MirkoFilipovic.html").mkString))

    val f = File.createTempFile("FighterToCsvTest", ".csv")
    Csv.write(f.getAbsolutePath, fighter)

    val fighter2 = Csv.read[Fighter](f.getAbsolutePath)

    println(fighter)
    fighter shouldBe fighter2
  }
}
