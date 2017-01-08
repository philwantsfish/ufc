package fish.philwants.csv

import java.io.File

import com.github.tototoshi.csv.{CSVReader, CSVWriter}

/**
  * A typeclass that converts to/from CSV format.
  * @tparam T
  */
trait CsvFormatter[T] {
  def toCSV(data: T): Vector[String]
  def fromCSV(data: Vector[String]): T
}

/**
  * A class that reads and writes CSV files and arbitrary Scala classes. Requires a CsvFormatter for the class
  */
object Csv {
  def read[T](path: String)(implicit f: CsvFormatter[T]): Vector[T] = {
    val reader = CSVReader.open(new File(path))
    val builder = Vector.newBuilder[T]
    reader.foreach { r =>
      builder += f.fromCSV(r.toVector)
    }
    reader.close()
    builder.result()
  }

  def write[T](path: String, data: Vector[T])(implicit f: CsvFormatter[T]): Unit = {
    val writer = CSVWriter.open(new File(path))
    val ds = data.map { d => f.toCSV(d) }
    writer.writeAll(ds)
    writer.close()
  }
}
