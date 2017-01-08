//package fish.philwants.analysis
//
//import fish.philwants.old.Data
//import org.scalatest.{FlatSpec, Matchers}
//
//class RunAnalysis extends FlatSpec with Matchers {
//  def dataPointToCsvPoint(tup: Tuple2[Any, String]): String = {
//    s"${tup._1},${tup._2}"
//  }
//
//  "AgeAnalysis2" should "yield a valid model" in {
//    val data = Data.fights.flatMap(AgeAnalysis2.dataPoint)
////    data.foreach( d => println(dataPointToCsvPoint(d)))
//
//    /**
//      * The age analysis gives the follow output for logistic regression in R
//      *
//Loading required package: methods
//
//Call:
//glm(formula = result ~ age_diff, family = "binomial", data = mydata)
//
//Deviance Residuals:
//    Min       1Q   Median       3Q      Max
//-3.0254   0.1683   0.1820   0.2021   0.2832
//
//Coefficients:
//            Estimate Std. Error z value Pr(>|z|)
//(Intercept)  4.03934    0.13495  29.932   <2e-16 ***
//age_diff    -0.05268    0.02638  -1.997   0.0459 *
//---
//Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
//
//(Dispersion parameter for binomial family taken to be 1)
//
//    Null deviance: 575.24  on 3286  degrees of freedom
//Residual deviance: 571.24  on 3285  degrees of freedom
//AIC: 575.24
//
//Number of Fisher Scoring iterations: 7
//      *
//      */
//  }
//
//
//  "RingRustAnalysis2" should "yield a valid model" in {
//    val data = Data.fights.flatMap(RingRustAnalysis2.dataPointForDays)
//    data
////      .filter { case(timeout,result) => timeout >= 12 && timeout < 24}
//        .foreach { d => println(dataPointToCsvPoint(d)) }
//
//    /**
//      * Loading required package: methods
//
//Call:
//glm(formula = result ~ timeout, family = "binomial", data = mydata)
//
//Deviance Residuals:
//    Min       1Q   Median       3Q      Max
//-3.0614   0.1819   0.1840   0.1854   0.3084
//
//Coefficients:
//            Estimate Std. Error z value Pr(>|z|)
//(Intercept) 4.070740   0.159511  25.520   <2e-16 ***
//timeout     0.007769   0.013519   0.575    0.566
//---
//Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
//
//(Dispersion parameter for binomial family taken to be 1)
//
//    Null deviance: 406.47  on 2387  degrees of freedom
//Residual deviance: 406.16  on 2386  degrees of freedom
//AIC: 410.16
//
//Number of Fisher Scoring iterations: 7
//      */
//  }
//}
