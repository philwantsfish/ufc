package fish.philwants.crawler

import com.typesafe.scalalogging.LazyLogging
import fish.philwants.crawler.sherdog._
import fish.philwants.csv.Csv
import fish.philwants.data._

/**
  * This class contains the main style functions. Tying all the classes togther.
  */
object Crawler extends LazyLogging {
  def printExecutionTime(millis: Long): Unit = {
    val totalSeconds = millis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    logger.info(s"Command finished in $minutes min $seconds sec")
  }

  def getAndPersistAllFights(path: String): Unit = {
    val startTime = System.currentTimeMillis()

    val fights = Sherdog.getAllFights()
    Csv.write[Fight](path, fights)

    val endTime = System.currentTimeMillis()
    printExecutionTime(endTime - startTime)
  }

  private def eventsUntil(e: Event): Vector[Event] = {
    def eventsUntil(es: Vector[Event], page_num: Int): Vector[Event] = {
      val newEvents = Sherdog.events(page_num)
      if(newEvents.contains(e)) {
        // Page with event found.
        newEvents ++ es
      } else {
        eventsUntil(newEvents ++ es, page_num + 1)
      }
    }
    eventsUntil(Vector(), 1)
  }

  def updateFights(path: String): Unit = {
    val startTime = System.currentTimeMillis()

    // Get the latest event parsed
    val fights = Csv.read[Fight](path)
    val latestEvent = fights.head.event
    logger.info(s"Latest known event: $latestEvent")

    // collect events on each page to the page with the latestEvent
    val newEventSet = eventsUntil(latestEvent).filterNot { e => e.org == Unknown }.toSet

    // Remove all known fights from the collected ones
    val allEvents = fights.map { f => f.event }.toSet
    val newEvents = newEventSet -- allEvents
    logger.info(s"Found ${newEvents.size} new events")

    // Combine the two sets and persist them
    val newFights = newEvents.flatMap { e => Sherdog.fights(e) }.toVector
    Csv.write(path, newFights ++ fights)

    val endTime = System.currentTimeMillis()
    printExecutionTime(endTime - startTime)
  }
}
