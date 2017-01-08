package fish.philwants.crawler.sherdog

import com.typesafe.scalalogging.LazyLogging

import scala.util.Try
import fish.philwants.crawler.Unknown
import fish.philwants.data._

/**
  * A Sherdog API. This class combines the functionality of SherdogParser and SherdogRequests.
  */
object Sherdog extends LazyLogging {
  def getAllFights(): Vector[Fight] = {
    def requestFights(fights: Vector[Fight], page_num: Int): Vector[Fight] = {
      // Get list of events on page
      val resp = SherdogRequests.event_listing(page_num)

      // Filter for organizations we know about
      val events = SherdogParser.events(resp).filterNot(e => e.org == Unknown)

      // Concatenate previous found fights with fights on the requested page
      val allFights = fights ++ Try(events.flatMap { e =>
        val eresp = SherdogRequests.fights(e)
        SherdogParser.fights(eresp, e)
      }).getOrElse(Vector())

      // Let's consider UFC 1 the epoch of fighting. Keep sending requests until we find event.name == "UFC 1".
      // At the time of development this was on page 279.
      if (events.exists { e => e.name.equals("UFC 1") }) {
        allFights
      } else {
        requestFights(allFights, page_num + 1)
      }
    }

   requestFights(Vector(), 1)
  }

  def events(page_num: Int): Vector[Event] = {
    val resp = SherdogRequests.event_listing(page_num)
    SherdogParser.events(resp)
  }

  def fights(event: Event): Vector[Fight] = {
    val resp = SherdogRequests.fights(event)
    SherdogParser.fights(resp, event)
  }


  def fighter(url: String): Fighter = {
    val base = "http://www.sherdog.com"
    logger.info(s"Requesting page for $url")
    val resp = SherdogRequests.request(base + url)
    SherdogParser.fighter(resp)
  }

  def fighters(urls: Vector[String]): Vector[Fighter] = urls.map { url => fighter(url) }
}
