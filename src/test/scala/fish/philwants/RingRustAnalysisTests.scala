package fish.philwants

import java.io.File

import org.jfree.chart.{ChartFactory, ChartPanel, ChartUtilities}
import org.jfree.chart.plot.{CategoryPlot, PlotOrientation}
import org.jfree.data.category.CategoryDataset
import org.jfree.data.general.DefaultKeyedValues2DDataset
import org.scalatest.{FlatSpec, Matchers}

class RingRustAnalysisTests extends FlatSpec with Matchers {
  def createGraph(dataset: DefaultKeyedValues2DDataset): Unit = {
    val stacked = ChartFactory.createStackedBarChart("title",
      "Difference in timeout",
      "Result",
      dataset,
      PlotOrientation.HORIZONTAL,
      true,
      false,
      false)


    val chart = ChartFactory.createBarChart("Title",
    "Difference in timeout",
    "Result",
      dataset,
      PlotOrientation.HORIZONTAL,
      true,
      true,
      true)

    val plot: CategoryPlot = chart.getCategoryPlot()
    ChartUtilities.saveChartAsJPEG(new File("./chart.jpg"), 1, chart, 1000, 1000)

    val plot2 = stacked.getPlot()
    ChartUtilities.saveChartAsJPEG(new File("./chart2.jpg"), 1, stacked, 1000, 1000)
  }


  it should "create graphs" in {
    createGraph(RingRustAnalysis.createDataset(RingRustAnalysis.monthlyResults.toMap))
  }

  it should "print monthly and total results" in {
    RingRustAnalysis.monthlyResults.foreach { case (month, results) =>
      println(s"Month $month - Wins:${results.count(r => r)}, Losses:${results.count(r => !r)}")
    }

    println(s"Total in most simple way, Wins:${RingRustAnalysis.totalResults.count(r => r)}, Losses:${RingRustAnalysis.totalResults.count(r => !r)}")
  }

  it should "be successfully predict fights" in {
    val percent: Double = (RingRustAnalysis.totalResults.count(r => r).toDouble / RingRustAnalysis.totalResults.size.toDouble) * 100
    println(s"Corrected predicted $percent percent fights")
    percent should be > 60.0
  }
}
