import org.apache.log4j.{Logger, Level}
import org.apache.spark.sql.{Row, SaveMode, DataFrame}
import org.apache.spark.graphx.{GraphLoader, Graph}
import org.apache.spark.rdd.RDD
import scala.collection.JavaConverters._

val log = Logger.getLogger("network-analysis")
log.setLevel(Level.INFO)

val dir = "file://" + sc.getConf.get("spark.driver.metadata.qa.dir") + "/"
log.info(dir)

def write(file: String, df: DataFrame): Unit = {
  var dataDF = df.select(df.columns.map(c => df.col(c).cast("string")): _*)
  var headerDF = spark.createDataFrame(List(Row.fromSeq(dataDF.columns.toSeq)).asJava, dataDF.schema)
  var outputFolder = dir + file + ".csv.dir"
  headerDF.union(dataDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)
}

def pageRank(graph: Graph[Array[String],Int]): Unit = {
  log.info("STEP 1: page rank")

  var pagerankGraph = graph.pageRank(0.001)

  // Get the attributes of the top pagerank nodes
  val infoWithPageRank = graph.outerJoinVertices(pagerankGraph.vertices) {
    case (uid, attrList, Some(pr)) => (pr, attrList.toList)
    case (uid, attrList, None) => (0.0, attrList.toList)
  }

  var df_pagerank = infoWithPageRank.vertices.map(e => (e._1, e._2._1)).toDF("id", "score")
  write("network-nodes-pagerank", df_pagerank)

  // page rank stat
  var dataDF = df_pagerank.select("score").summary().toDF("statistic", "value")
  write("network-nodes-pagerank-stat", dataDF)

  // page rank histogram
  var histogram = df_pagerank.select(round($"score", 0).as("score").cast("int")).groupBy("score").count().orderBy("score")
  write("network-nodes-pagerank-histogram", histogram)
}

def connectedComponents(graph: Graph[Array[String],Int]): Unit = {
  log.info("STEP 2: connectedComponents")

  var cc = graph.connectedComponents()
  var componentDF = cc.vertices.toDF("vid", "cid")
  var componentsCount = componentDF.groupBy("cid").count().toDF("componentId", "size")
  write("network-nodes-components", componentsCount.orderBy(desc("count")))

  // connectedComponents stat
  var statDF = componentsCount.select("size").summary().toDF("statistic", "value")
  write("network-nodes-components-stat", statDF)

  var histogram = componentsCount.select("size").groupBy("size").count().orderBy("size")
  write("network-nodes-components-histogram", histogram)
}  

def degree(graph: Graph[Array[String],Int]): Unit = {
  log.info("STEP 3: degree")

  var degreesRDD = graph.degrees.cache()
  var df = degreesRDD.toDF("id", "degree")
  write("network-nodes-degrees", df.orderBy(desc("degree")))

  var dataDF = df.select("degree").summary().toDF("statistic", "value")
  write("network-nodes-degrees-stat", dataDF)

  var histogram = df.select("degree").groupBy("degree").count().orderBy("degree")
  write("network-nodes-degrees-histogram", histogram)
}

def analyseGraph(fileName: String, nodes: RDD[(Long, Array[String])]): Unit = {
  // Parse the edge data which is already in userId -> userId format
  val followerGraph: Graph[Int,Int] = GraphLoader.edgeListFile(sc, dir + fileName)

  // Attach the attributes
  val graph: Graph[Array[String],Int] = followerGraph.outerJoinVertices(nodes) {
    case (uid, deg, Some(attrList)) => attrList
    // Some nodes may not have attributes so we set them as empty
    case (uid, deg, None) => Array.empty[String]
  }

  pageRank(graph)
  connectedComponents(graph)
  degree(graph)
}

// val dir = "file:///home/kiru/bin/marc/_output/gent/"
log.info("prepare")

// Load my user data and parse into tuples of user id and attribute list
val nodes: RDD[(Long, Array[String])] = (sc.textFile(dir + "network-nodes.csv")
  .map(line => line.split(","))
  .map(parts => (parts.head.toLong, parts.tail)))

analyseGraph("network-pairs.csv", nodes)


/*
// STEP 5: clustering coefficients
log.info("STEP 5: clustering coefficients")

val triCountGraph = graph.triangleCount()
triCountGraph.vertices.map(x => x._2).stats()
val tricountDF = triCountGraph.vertices.toDF("id", "count")

val maxTrisGraph = degreesRDD.mapValues(d => d * (d - 1) / 2.0)
val maxTrisDF = maxTrisGraph.toDF("id", "theoreticalMax")

val clusterCoef = triCountGraph.vertices.innerJoin(maxTrisGraph) { 
  (vertexId, triCount, maxTris) => {
    val coef = if (maxTris == 0) 0 else triCount / maxTris
    (triCount, maxTris, coef)
  }
}
val networkAverageClusteringCoefficient = clusterCoef.map(_._2._3).sum() / graph.vertices.count()

val naccDF = sc.parallelize(Seq(networkAverageClusteringCoefficient)).toDF("average-clustering-coefficient")
var outputFolder = dir + "network-nodes-average-clustering-coefficient.csv.dir"
naccDF.write.option("header", "true").mode(SaveMode.Overwrite).csv(outputFolder)
*/

log.info("DONE")

System.exit(0)