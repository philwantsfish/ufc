package fish.philwants.stats

import org.scalatest.{FlatSpec, Matchers}

class StatsTests extends FlatSpec with Matchers {

  "Stats.linregress" should "perform linear regression" in {
    val xs = Vector(1, 2, 3, 5, 6, 10, 11).map(_.toDouble)
    val ys = Vector(100, 200, 310, 540, 650, 1100, 1111).map(_.toDouble)

    val (slope, intercept, r, pvalue, stderr) = Stats.linregres(xs, ys)
    slope shouldBe 105.63535031847132
    intercept shouldBe -0.449044585987167
    r shouldBe 0.9974566675446038
    stderr shouldBe 31.974317918752046
  }

  "Stats.{mean,var_s,stddev_s}" should "provide basic functions" in {
    val sample_data = Vector(42,43,39,36,33,32,36,36,34,37,40,34,32,38,33,42,39,31,31,36,38,36,38,35,36,34,37,31,42,38,31,35,34,26,24).map(_.toDouble)
    Stats.mean(sample_data) shouldBe 35.40
    Stats.var_s(sample_data) shouldBe 17.894117647058827
    Stats.stddev_s(sample_data) shouldBe 4.2301439274637955
    Stats.coefficient_of_variance(sample_data) shouldBe 11.949559117129366
  }

  it should "also work as implicits" in {
    import fish.philwants.stats.implicits.VectorImplicits._
    val sample_data = Vector(42,43,39,36,33,32,36,36,34,37,40,34,32,38,33,42,39,31,31,36,38,36,38,35,36,34,37,31,42,38,31,35,34,26,24).map(_.toDouble)
    sample_data.mean shouldBe 35.40
    sample_data.var_s shouldBe 17.894117647058827
    sample_data.stddev_s shouldBe 4.2301439274637955
    sample_data.coefficient_of_variance shouldBe 11.949559117129366
  }

  "Covariance" should "provide valid results" in {
    val v1 = Vector(12,30,15,24,14,18,28,26,19,27).map(_.toDouble)
    val v2 = Vector(20,60,27,50,21,30,61,54,32,57).map(_.toDouble)
    Stats.covar(v1,v2) shouldBe 106.93333333333334
  }

  "chi-square" should "provide a valid chi-square value" in {
    val dataBuilder = Vector.newBuilder[Double]
    (1 to 111).foreach { _ => dataBuilder += 1}
    (1 to 90).foreach { _ => dataBuilder += 2}
    (1 to 81).foreach { _ => dataBuilder += 3}
    (1 to 102).foreach { _ => dataBuilder += 4}
    (1 to 124).foreach { _ => dataBuilder += 5}
    (1 to 92).foreach { _ => dataBuilder += 6}
    val data = dataBuilder.result

    val (chisquare, df) = Stats.chi_square(data)
    chisquare shouldBe 12.26
    df shouldBe 6
  }
}
