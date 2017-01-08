package fish.philwants.crawler.sherdog

import com.typesafe.scalalogging.LazyLogging
import fish.philwants.data.Event
import org.jsoup.Jsoup

object SherdogRequests extends LazyLogging {
  /**
    * Sends a create to Sherdog for the page of events. Supports pagination.
    *
    * Note: 0 and 1 retrieve the same page. Update the function to return empty for page_num 0 and less
    * @param page_num
    * @return
    */
  def event_listing(page_num: Int = 0): String = {
    logger.debug(s"Sending request for event listing, Page $page_num")
    val url = if (page_num == 0) "http://www.sherdog.com/events"
    else s"http://www.sherdog.com/events/recent/$page_num-page"
    val resp = Jsoup.connect(url).execute()
    resp.body
  }

  def fights(event: Event): String = {
    logger.debug(s"Sending request for event details, ${event.name}: ${event.title}")
    Jsoup.connect(event.url).execute().body
  }

  def request(url: String): String = Jsoup.connect(url).execute().body
}
