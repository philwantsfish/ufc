package fish.philwants.old

import java.io.File

import com.github.tototoshi.csv.CSVReader

/**
  * Created by okeefephil on 2017-01-02.
  */
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
