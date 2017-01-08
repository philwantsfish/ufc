package fish.philwants.crawler

import java.io.{BufferedWriter, File, FileWriter}

import com.github.tototoshi.csv.{CSVReader, CSVWriter}
import com.typesafe.scalalogging.LazyLogging
import fish.philwants.analysis.TimeoutAnalysis
import org.scalatest.{FlatSpec, Matchers}
import fish.philwants.csv.{Csv, CsvFormatter}
import fish.philwants.data._
import org.joda.time.DateTime
import org.jsoup.Jsoup

class CrawlerTests extends FlatSpec with Matchers with LazyLogging {

  def writeToFile(path: String, text: String): Unit = {
    val file = new File(path)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(text)
    bw.close()
  }

  "The Crawler" should "download and persist all fights" in {
    val path = "./fights.csv"
//    Crawler.getAndPersistAllFights(path)
//    Crawler.updateFights(path)
  }

  it should "be a main function" in {
    val path = "./data/fights.csv"
    //    Crawler.updateFights(path)

//    val fights = Csv.read[Fight](path)
//    val data: Vector[(Long, Boolean)] = fights.flatMap { f => TimeoutAnalysis.datapoint(f) }.flatten
//
//    val dataFormatted = data.map { case(t,r) => Vector(t.toString, if (r) "1" else "0") }
//
//    val f = new File("./timeout-byMonth-2year.csv")
//    val writer = CSVWriter.open(f)
//    writer.writeAll(dataFormatted)

    val data = CSVReader.open(new File("./timeout-byMonth.csv")).all()
    val filteredData = data.filter { list =>
      val time = Integer.parseInt(list.head)
      time >= -24 && time <= 24
    }

    CSVWriter.open(new File("./timeout-ByMonth-2year.csv")).writeAll(filteredData)
  }
}
