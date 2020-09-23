import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.graphx.GraphLoader
import org.apache.spark.sql.Row
import org.apache.spark.sql.SaveMode
import scala.collection.JavaConverters._

val log = Logger.getLogger("network-analysis")
log.setLevel(Level.INFO)

log.info("prepare")

val dir = "file://" + sc.getConf.get("spark.driver.metadata.qa.dir") + "/"
log.info(dir)

// val dir = "file:///home/kiru/bin/marc/_output/gent/"

// Load my user data and parse into tuples of user id and attribute list
val nodes = (sc.textFile(dir + "network-nodes.csv")
  .map(line => line.split(","))
  .map(parts => (parts.head.toLong, parts.tail)))

// Parse the edge data which is already in userId -> userId format
val followerGraph = GraphLoader.edgeListFile(sc, dir + "network-pairs.csv")

// Attach the attributes
val graph = followerGraph.outerJoinVertices(nodes) {
  case (uid, deg, Some(attrList)) => attrList
  // Some nodes may not have attributes so we set them as empty
  case (uid, deg, None) => Array.empty[String]
}

// STEP 1: indegree
log.info("STEP 1: indegree")

// indegree
// var df = graph.inDegrees.toDF("id","degree")
// var dataDF = df.select(df.columns.map(c => df.col(c).cast("string")): _*)
// var headerDF = spark.createDataFrame(List(Row.fromSeq(dataDF.columns.toSeq)).asJava, dataDF.schema)
// var outputFolder = dir + "network-nodes-indegrees.csv.dir"
// headerDF.union(dataDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)

// indegree stat
// var dataDF = df.select("degree").summary().toDF(Seq("statistic", "value"): _*)
// var headerDF = spark.createDataFrame(List(Row.fromSeq(dataDF.columns.toSeq)).asJava, dataDF.schema)
// var outputFolder = dir + "network-nodes-indegree-stat.csv.dir"
// headerDF.union(dataDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)

// STEP 2: page rank
log.info("STEP 2: page rank")

// var pagerankGraph = graph.pageRank(0.001)

// Get the attributes of the top pagerank nodes
// val infoWithPageRank = graph.outerJoinVertices(pagerankGraph.vertices) {
//   case (uid, attrList, Some(pr)) => (pr, attrList.toList)
//   case (uid, attrList, None) => (0.0, attrList.toList)
// }

// var df_pagerank = infoWithPageRank.vertices.map(e => (e._1, e._2._1)).toDF("id", "score")
// var dataDF = df_pagerank.select(df_pagerank.columns.map(c => df_pagerank.col(c).cast("string")): _*)
// var headerDF = spark.createDataFrame(List(Row.fromSeq(dataDF.columns.toSeq)).asJava, dataDF.schema)
// var outputFolder = dir + "network-nodes-pagerank.csv.dir"
// headerDF.union(dataDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)

// page rank stat
// var dataDF = df_pagerank.select("score").summary().toDF(Seq("statistic", "value"): _*)
// var headerDF = spark.createDataFrame(List(Row.fromSeq(dataDF.columns.toSeq)).asJava, dataDF.schema)
// var outputFolder = dir + "network-nodes-pagerank-stat.csv.dir"
// headerDF.union(dataDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)

// page rank histogram
// var histogram = df_pagerank.select(round($"score", 0).as("score").cast("int")).groupBy("score").count().orderBy("score")
// var histogramDF = histogram.select(histogram.columns.map(c => histogram.col(c).cast("string")): _*)
// var headerDF = spark.createDataFrame(List(Row.fromSeq(histogramDF.columns.toSeq)).asJava, histogramDF.schema)
// var outputFolder = dir + "network-nodes-pagerank-histogram.csv.dir"
// headerDF.union(histogramDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)

// STEP 3: connectedComponents
log.info("STEP 3: connectedComponents")

var cc = graph.connectedComponents()
var componentDF = cc.vertices.toDF("vid", "cid")
var componentsCount = componentDF.groupBy("cid").count().toDF("componentId", "size")
var dataDF = componentsCount.orderBy(desc("count")).select(componentsCount.columns.map(c => componentsCount.col(c).cast("string")): _*)
var headerDF = spark.createDataFrame(List(Row.fromSeq(dataDF.columns.toSeq)).asJava, dataDF.schema)
var outputFolder = dir + "network-nodes-components.csv.dir"
headerDF.union(dataDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)

// connectedComponents stat
var statDF = componentsCount.select("size").summary().toDF("statistic", "value")
var headerDF = spark.createDataFrame(List(Row.fromSeq(statDF.columns.toSeq)).asJava, statDF.schema)
var outputFolder = dir + "network-nodes-components-stat.csv.dir"
headerDF.union(statDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)

var histogram = componentsCount.select("size").groupBy("size").count().orderBy("size")
var histogramDF = histogram.select(histogram.columns.map(c => histogram.col(c).cast("string")): _*)
var headerDF = spark.createDataFrame(List(Row.fromSeq(histogramDF.columns.toSeq)).asJava, histogramDF.schema)
var outputFolder = dir + "network-nodes-components-histogram.csv.dir"
headerDF.union(histogramDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)


// STEP 4: degree
log.info("STEP 4: degree")

var degreesRDD = graph.degrees.cache()
var df = degreesRDD.toDF("id", "degree")
var dataDF = df.orderBy(desc("degree")).select(df.columns.map(c => df.col(c).cast("string")): _*)
var headerDF = spark.createDataFrame(List(Row.fromSeq(dataDF.columns.toSeq)).asJava, dataDF.schema)
var outputFolder = dir + "network-nodes-degrees.csv.dir"
headerDF.union(dataDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)

var dataDF = df.select("degree").summary().toDF("statistic", "value")
var headerDF = spark.createDataFrame(List(Row.fromSeq(dataDF.columns.toSeq)).asJava, dataDF.schema)
var outputFolder = dir + "network-nodes-degrees-stat.csv.dir"
headerDF.union(dataDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)

var histogram = df.select("degree").groupBy("degree").count().orderBy("degree")
var histogramDF = histogram.select(histogram.columns.map(c => histogram.col(c).cast("string")): _*)
var headerDF = spark.createDataFrame(List(Row.fromSeq(histogramDF.columns.toSeq)).asJava, histogramDF.schema)
var outputFolder = dir + "network-nodes-degrees-histogram.csv.dir"
headerDF.union(histogramDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)

log.info("DONE")

System.exit(0)
