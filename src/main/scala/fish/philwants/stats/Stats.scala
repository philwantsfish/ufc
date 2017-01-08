package fish.philwants.stats

import fish.philwants.stats.implicits.VectorImplicits._

object Stats {

  //
  // Functions for variance. These functions are available as implicits on Vector[Double]
  //

  def mean(data: Vector[Double]): Double = data.sum / data.size

  // Provides same calculation used by var_s and var_p
  private def variance(samples: Vector[Double], n: Double): Double = {
    val sbar = mean(samples)
    val dist_to_mean = samples.map { s => Math.pow(s - sbar, 2) }
    dist_to_mean.sum / n
  }
  def var_s(samples: Vector[Double]): Double = variance(samples, samples.size.toDouble - 1.0)
  def var_p(samples: Vector[Double]): Double = variance(samples, samples.size.toDouble)


  def stddev_s(samples: Vector[Double]): Double = Math.sqrt(var_s(samples))
  def stddev_p(samples: Vector[Double]): Double = Math.sqrt(var_p(samples))

  def coefficient_of_variance(samples: Vector[Double]): Double = (stddev_s(samples) / mean(samples)) * 100

  def covar(v1: Vector[Double], v2: Vector[Double]): Double = {
    require(v1.size == v2.size)
    val x_mean = v1.mean
    val y_mean = v2.mean
    v1.zip(v2).map { case (x,y) => (x - x_mean) * (y - y_mean) }.sum / (v1.size - 1)
  }

  def r_value(xs: Vector[Double], ys: Vector[Double]): Double = covar(xs, ys) / (stddev_s(xs) * stddev_s(ys))

  /**
    * This is a rule of thumb, if |r| > 2 / sqrt(sample_size) then a relationship exists
    * @param xs
    * @param ys
    * @return
    */
  def linear_relationship_exists(xs: Vector[Double], ys: Vector[Double]): Boolean = {
    require(xs.size == ys.size)
    val r = r_value(xs, ys)
    Math.abs(r) > (2 / Math.sqrt(xs.size))
  }

  /**
    * Calculate the linear regression.
    * @param xs The set of independent variables
    * @param ys The set of dependent variables
    * @return Returns a tuple consisting of slope, intercept, rvalue, pvalue, and stderr
    */
  def linregres(xs: Vector[Double], ys: Vector[Double]): (Double, Double, Double, Double, Double) = {
    require(xs.size == ys.size)
    val n = xs.size.toDouble
    val x_mean = xs.sum / xs.size
    val y_mean = ys.sum / ys.size

    // sum of squares for x
    val ssx = xs.map { x => Math.pow(x - x_mean, 2) }.sum
    val slope =  xs.zip(ys).map { case (x,y) => (x - x_mean) * (y - y_mean) }.sum  / ssx

    // Use the centroid point for x and y to calculate the intercept
    val intercept = y_mean - (slope * x_mean)

    // sum of squares due to error
    val sse = xs.zip(ys).map { case (x,y) =>
      val y_hat = (x * slope) + intercept
      Math.pow(y - y_hat, 2)
    }.sum

    // sum of squares due to regression
    val ssr = xs.zip(ys).map { case (x,y) =>
      val y_hat = (x * slope) + intercept
      Math.pow(y_mean - y_hat, 2)
    }.sum

    // total sum of squares, this is the same as ssr+sse
    val sst = ys.map { y => Math.pow( y - y_mean, 2) }.sum
    val r_sq = ssr / sst

    val stderr = Math.sqrt( sse / (n-2) )

    val pvalue = Integer.MAX_VALUE

    val r = r_value(xs, ys)
    (slope, intercept, r, pvalue, stderr)
  }


  /**
    * Calcualtes the chi-square for a categorical data set
    * @param xs The dataset
    * @return Returns a tuple consisting of the chi square value and the degrees of freedom
    */
  def chi_square[A](xs: Vector[A]): (Double, Double) = {
    val grouped = xs.groupBy { x => x }
    val df = grouped.keys.size
    val expected = xs.size / df
    println(s"df:$df, xs.size${xs.size}, Expected: $expected")


    val chi_square = grouped.map { case (k, v) =>
      val observed = v.size
      Math.pow(observed - expected, 2)
    }.sum / expected

    (chi_square, df)
  }


  /**
    * Given some degree of freedom and a pvalue calculate the chi square critical value
    *
    * Note: This function exists in excel as CHIINV(error, df)
    *
    * Note2: This function performs a lookup in the chi square distribution table. Either we need to hardcode the table
    * or find out how to calculate it.
    * @param df
    * @param pvalue
    * @return
    */
  def chi_square_critical_value(df: Int, pvalue: Double): Double = 1.0
}
