name := "Metadata QA MARC"

version := "0.4"

scalaVersion := "2.12.4"
val sparkVersion = "2.4.7"  // "3.0.1"

// additional libraries
libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core" % sparkVersion,
  // "org.apache.spark" % "spark-sql_2.12" % sparkVersion,
  "org.apache.commons" % "commons-math3" % "3.6.1"
)
