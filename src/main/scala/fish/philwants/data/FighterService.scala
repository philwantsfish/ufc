package fish.philwants.data

import com.typesafe.scalalogging.LazyLogging
import fish.philwants.crawler.sherdog.Sherdog
import fish.philwants.csv.Csv
import org.joda.time.{Days, Instant, Minutes}

object FighterService extends LazyLogging {
  val fighters = Csv.read[Fighter]("./data/fighters.csv")

  val fighterMap = fighters.map { f => (f.name, f) }.toMap

  // The number of days in order to update a fighter instance
  val staleDuration = 21

  /**
    * Returns a instance of a Fighter if available. This function will:
    * 1) Check the local cache for the instance
    * 2) On a stale cache entry, retrieve and persist the new entry
    * 3) On a cache miss attempt to retrieve a new instance
    * @param name The fighter's name
    * @return An instance of the fighter if available
    */
  def fighter(name: String): Option[Fighter] = {
    val fighter = fighterMap.get(name).map { f =>
      if (shouldBeUpdated(f)) {
        val updatedInstance = Sherdog.fighter(f.url)
        logger.info("You need to add code to persist the updated fighter instance!!!")
        updatedInstance
      }
      else f
    }
    fighter
  }

  def shouldBeUpdated(fighter: Fighter): Boolean = {
    Days.daysBetween(fighter.lastUpdated.toInstant, Instant.now()).getDays > staleDuration
  }
}
