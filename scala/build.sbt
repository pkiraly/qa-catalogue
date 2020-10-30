name := "Metadata QA MARC"

version := "0.4"

scalaVersion := "2.11.12"
val sparkVersion = "2.4.3"  // 2.4.3, 2.4.7, 3.0.1

// additional libraries
libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % sparkVersion,
  "org.apache.spark" % "spark-sql_2.11" % sparkVersion,
  "org.apache.spark" % "spark-graphx_2.11" % sparkVersion,
  "org.apache.commons" % "commons-math3" % "3.6.1"
)
