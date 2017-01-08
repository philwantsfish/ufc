package fish.philwants.stats

import org.scalatest.{FlatSpec, Matchers}

class TablePrinterTests extends FlatSpec with Matchers {
  "TablePrinter" should "print a table of data" in {
    val data = Seq(Seq("Header 1", "Header 2", "Header 3"), Seq(4,5,6), Seq(7,8,9))
    val output = TablePrinter.format(data)
    output shouldBe """+--------+--------+--------+
                      ||Header 1|Header 2|Header 3|
                      |+--------+--------+--------+
                      ||       4|       5|       6|
                      ||       7|       8|       9|
                      |+--------+--------+--------+""".stripMargin
  }

}
