import org.apache.log4j.{Logger, Level}
import org.apache.spark.sql.{Row, SaveMode, DataFrame}
import org.apache.spark.graphx.{GraphLoader, Graph}
import org.apache.spark.rdd.RDD
import scala.collection.JavaConverters._

val log = Logger.getLogger("network-analysis")
log.setLevel(Level.INFO)

var inputDir = "file://" + sc.getConf.get("spark.driver.metadata.qa.inputDir")
if (!inputDir.endsWith("/"))
  inputDir = inputDir + "/"

var outputDir = "file://" + sc.getConf.get("spark.driver.metadata.qa.outputDir")
if (!outputDir.endsWith("/"))
  outputDir = outputDir + "/"

log.info(s"inputDir: $inputDir")
log.info(s"outputDir: $outputDir")

def write(file: String, df: DataFrame): Unit = {
  var dataDF = df.select(df.columns.map(c => df.col(c).cast("string")): _*)
  var headerDF = spark.createDataFrame(List(Row.fromSeq(dataDF.columns.toSeq)).asJava, dataDF.schema)
  var outputFolder = outputDir + file + ".csv.dir"
  headerDF.union(dataDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)
}

def pageRank(graph: Graph[Array[String],Int], suffix: String): Unit = {
  log.info("STEP 1: page rank")

  var pagerankGraph = graph.pageRank(0.001)

  // Get the attributes of the top pagerank nodes
  val infoWithPageRank = graph.outerJoinVertices(pagerankGraph.vertices) {
    case (uid, attrList, Some(pr)) => (pr, attrList.toList)
    case (uid, attrList, None) => (0.0, attrList.toList)
  }

  var df_pagerank = infoWithPageRank.vertices.map(e => (e._1, e._2._1)).toDF("id", "score")
  write("network-scores" + suffix + "-pagerank", df_pagerank)

  // page rank stat
  var dataDF = df_pagerank.select("score").summary().toDF("statistic", "value")
  write("network-scores" + suffix + "-pagerank-stat", dataDF)

  // page rank histogram
  var histogram = df_pagerank.select(round($"score", 0).as("score").cast("int")).groupBy("score").count().orderBy("score")
  write("network-scores" + suffix + "-pagerank-histogram", histogram)
}

def connectedComponents(graph: Graph[Array[String],Int], suffix: String): Unit = {
  log.info("STEP 2: connectedComponents")

  var cc = graph.connectedComponents()
  var componentDF = cc.vertices.toDF("vid", "cid")
  var componentsCount = componentDF.groupBy("cid").count().toDF("componentId", "size")
  write("network-scores" + suffix + "-components", componentsCount.orderBy(desc("count")))

  // connectedComponents stat
  var statDF = componentsCount.select("size").summary().toDF("statistic", "value")
  write("network-scores" + suffix + "-components-stat", statDF)

  var histogram = componentsCount.select("size").groupBy("size").count().orderBy("size")
  write("network-scores" + suffix + "-components-histogram", histogram)
}  

def degree(graph: Graph[Array[String],Int], suffix: String): Unit = {
  log.info("STEP 3: degree")

  var degreesRDD = graph.degrees.cache()
  var df = degreesRDD.toDF("id", "degree")
  write("network-scores" + suffix + "-degrees", df.orderBy(desc("degree")))

  var dataDF = df.select("degree").summary().toDF("statistic", "value")
  write("network-scores" + suffix + "-degrees-stat", dataDF)

  var histogram = df.select("degree").groupBy("degree").count().orderBy("degree")
  write("network-scores" + suffix + "-degrees-histogram", histogram)
}

def analyseGraph(prefix: String, suffix: String, nodes: RDD[(Long, Array[String])]): Unit = {
  // Parse the edge data which is already in userId -> userId format
  log.info("create graph")
  val file = prefix + suffix + ".csv";
  val followerGraph: Graph[Int,Int] = GraphLoader.edgeListFile(sc, inputDir + file)

  // Attach the attributes
  val graph: Graph[Array[String],Int] = followerGraph.outerJoinVertices(nodes) {
    case (uid, deg, Some(attrList)) => attrList
    // Some nodes may not have attributes so we set them as empty
    case (uid, deg, None) => Array.empty[String]
  }
  graph.cache()
  // graph.count()

  pageRank(graph, suffix)
  connectedComponents(graph, suffix)
  degree(graph, suffix)
}

// val dir = "file:///home/kiru/bin/marc/_output/gent/"
log.info("prepare")

// Load my user data and parse into tuples of user id and attribute list
val nodes: RDD[(Long, Array[String])] = (sc.textFile(inputDir + "network-nodes.csv")
  .map(line => line.split(","))
  .map(parts => (parts.head.toLong, parts.tail)))
nodes.cache()
nodes.count

// analyseGraph("network-pairs", "", nodes)

val tagsDF = spark.read.
                  option("header", true).
                  option("inferSchema", "false").
                  csv(inputDir + "network-by-concepts-tags.csv")
val tags = tagsDF.select($"tag".cast("String")).
               rdd.
               map(r => r(0).asInstanceOf[String]).
               collect()
val allTags = Array("all") ++ tags
for (i <- allTags.indices) {
  val tag = allTags(i)
  val suffix = "-" + tag
  log.info(s"==== [ ${i+1}/${tags.length}: $tag ] ====")
  analyseGraph("network-pairs", suffix, nodes)
}


log.info("DONE")

System.exit(0)