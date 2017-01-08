package fish.philwants.stats.implicits

import fish.philwants.stats.Stats

object VectorImplicits {
  implicit class VectorVariancePimp(v: Vector[Double]) {
    def mean: Double = Stats.mean(v)
    def var_s: Double = Stats.var_s(v)
    def var_p: Double = Stats.var_p(v)
    def stddev_s: Double = Stats.stddev_s(v)
    def stddev_p: Double = Stats.stddev_p(v)
    def coefficient_of_variance: Double = Stats.coefficient_of_variance(v)
  }
}
