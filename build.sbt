name := "UfcAnalytics"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "ch.qos.logback" %  "logback-classic" % "1.1.7",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.github.tototoshi" %% "scala-csv" % "1.3.4",
  "org.jfree" % "jfreechart" % "1.0.14",
  "com.github.wookietreiber" %% "scala-chart" % "latest.integration",
  "org.jsoup" % "jsoup" % "1.10.1",
  "joda-time" % "joda-time" % "2.9.7"
)