name := "Metadata QA MARC"

version := "0.4"

scalaVersion := "2.12.4"

// additional libraries
libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.12" % "3.0.1" % "provided",
  "org.apache.spark" % "spark-sql_2.12" % "3.0.1" % "provided",
  "org.apache.commons" % "commons-math3" % "3.6.1"
)
