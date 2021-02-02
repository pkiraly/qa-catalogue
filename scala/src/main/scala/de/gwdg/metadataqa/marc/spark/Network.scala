package de.gwdg.metadataqa.marc.spark

import org.apache.log4j.{Logger, Level}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SparkSession, Row, SaveMode, DataFrame}
import org.apache.spark.sql.functions.{round, desc, max, lit}
import org.apache.spark.graphx.{GraphLoader, Graph}
import org.apache.spark.rdd.RDD
import scala.collection.JavaConverters._

object Network {
  val log = Logger.getLogger("network-analysis")
  log.setLevel(Level.INFO)

  val spark = SparkSession.builder.appName("Network").getOrCreate()
  val sc: SparkContext = spark.sparkContext

  import spark.implicits._

  var inputDir: String = "file://" + sc.getConf.get("spark.driver.metadata.qa.inputDir")
  var outputDir: String = "file://" + sc.getConf.get("spark.driver.metadata.qa.outputDir")
  var runPageRank: Boolean = sc.getConf
    .getBoolean("spark.driver.metadata.qa.runPageRank",false)
  var runClusteringCoefficient: Boolean = sc.getConf
    .getBoolean("spark.driver.metadata.qa.runClusteringCoefficient",false)
  var absMaxDegree: Int = 0

  def main(args: Array[String]): Unit = {
    spark.sparkContext.getConf.getAll.foreach(log.info)

    if (!this.inputDir.endsWith("/"))
      this.inputDir = this.inputDir + "/"

    if (!this.outputDir.endsWith("/"))
      this.outputDir = this.outputDir + "/"

    log.info(s"inputDir: $inputDir")
    log.info(s"outputDir: $outputDir")

    log.info("prepare")

    // Load my user data and parse into tuples of user id and attribute list
    val nodesFile = inputDir + "network-nodes.csv"
    log.info(nodesFile)
    val nodes: RDD[(Long, Array[String])] = (sc.textFile(nodesFile)
      .map(line => line.split(","))
      .map(parts => (parts.head.toLong, parts.tail)))
    nodes.cache()
    nodes.count

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
      this.analyseGraph("network-pairs", suffix, nodes)
    }

    log.info("DONE")
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

    this.density(graph, suffix)
    if (runPageRank)
      this.pageRank(graph, suffix)
    this.connectedComponents(graph, suffix)
    this.degree(graph, suffix)
    if (runClusteringCoefficient)
      this.clusteringCoefficient(graph, suffix)
  }

  def density(graph: Graph[Array[String],Int], suffix: String): Unit = {
    var density = (2.0 * graph.numEdges) / (graph.numVertices * (graph.numVertices-1))
    var avgDegree = (2.0 * graph.numEdges) / graph.numVertices
    var dataDF = sc.parallelize(Seq(Seq(graph.numVertices, graph.numEdges, density, avgDegree)))
      .map(x => (x(0), x(1), x(2), x(3)))
      .toDF("records", "links", "density", "avgDegree")
    this.write("network-scores" + suffix + "-density", dataDF)
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
    this.write("network-scores" + suffix + "-pagerank", df_pagerank)

    // page rank stat
    var dataDF = df_pagerank.select("score").summary().toDF("statistic", "value")
    this.write("network-scores" + suffix + "-pagerank-stat", dataDF)

    // page rank histogram
    var histogram = df_pagerank.select(round($"score", 0).as("score").cast("int")).groupBy("score").count().orderBy("score")
    this.write("network-scores" + suffix + "-pagerank-histogram", histogram)
  }

  def connectedComponents(graph: Graph[Array[String],Int], suffix: String): Unit = {
    log.info("STEP 2: connectedComponents")

    var cc = graph.connectedComponents()
    var componentDF = cc.vertices.toDF("vid", "cid")
    var componentsCount = componentDF.groupBy("cid").count().toDF("componentId", "size")
    this.write("network-scores" + suffix + "-components", componentsCount.orderBy(desc("count")))

    // connectedComponents stat
    var statDF = componentsCount.select("size").summary().toDF("statistic", "value")
    this.write("network-scores" + suffix + "-components-stat", statDF)

    var histogram = componentsCount.select("size").groupBy("size").count().orderBy("size")
    this.write("network-scores" + suffix + "-components-histogram", histogram)
  }

  def degree(graph: Graph[Array[String],Int], suffix: String): Unit = {
    log.info("STEP 3: degree")

    var degreesRDD = graph.degrees.cache()
    var df = degreesRDD.toDF("id", "degree")
    val maxDF = df.select(max($"degree").as("max"))
    val hasMax = !maxDF.first.isNullAt(0)
    if (hasMax) {
      // Hill -> Ochoa-Duval -> Newman-Watts-Barabási, The Structure and Dynamics of Networks (Princeton, 2006)
      val maxDegree = maxDF.first.getInt(0)
      if (suffix.equals("-all"))
        this.absMaxDegree = maxDegree;
      df = df.withColumn("qlink", $"degree" / maxDegree)
    } else {
      df = df.withColumn("qlink", lit(0))
    }
    df = df.withColumn("qlinkAbs", $"degree" / this.absMaxDegree)
    this.write("network-scores" + suffix + "-degrees", df.orderBy(desc("degree")))

    var dataDF = df.select("degree").summary().toDF("statistic", "value")
    this.write("network-scores" + suffix + "-degrees-stat", dataDF)

    dataDF = df.select("qlink").summary().toDF("statistic", "value")
    this.write("network-scores" + suffix + "-qlink-stat", dataDF)

    dataDF = df.select("qlinkAbs").summary().toDF("statistic", "value")
    this.write("network-scores" + suffix + "-qlinkabs-stat", dataDF)

    var histogram = df.select("degree").groupBy("degree").count().orderBy("degree")
    this.write("network-scores" + suffix + "-degrees-histogram", histogram)

    histogram = df.select("qlink").groupBy("qlink").count().orderBy("qlink")
    this.write("network-scores" + suffix + "-qlink-histogram", histogram)

    histogram = df.select("qlinkAbs").groupBy("qlinkAbs").count().orderBy("qlinkAbs")
    this.write("network-scores" + suffix + "-qlinkabs-histogram", histogram)
  }

  def clusteringCoefficient(graph: Graph[Array[String],Int], suffix: String): Unit = {
    log.info("STEP 5: clustering coefficients")

    val triCountGraph = graph.triangleCount()
    val triCountGraphstat = triCountGraph.vertices
      .map(x => x._2)
      .toDF("x")
      .select("x")
      .summary().toDF("statistic", "value")
    this.write("network-scores" + suffix + "-triCountGraph-stat", triCountGraphstat)
    // val tricountDF = triCountGraph.vertices.toDF("id", "count")

    // var degreesRDD = graph.degrees.cache()
    /*
    val maxTrisGraph = graph.degrees.mapValues(d => d * (d - 1) / 2.0)
    val maxTrisDF = maxTrisGraph.toDF("id", "theoreticalMax")

    val clusterCoef = triCountGraph.vertices.innerJoin(maxTrisGraph) {
      (vertexId, triCount, maxTris) => {
        val coef = if (maxTris == 0) 0 else triCount / maxTris
        (triCount, maxTris, coef)
      }
    }
    val total = clusterCoef.map(_._2._3).sum();
    val arageClusteringCoefficient = total / graph.vertices.count()

    val dataDF = Seq(arageClusteringCoefficient).toDF("average-clustering-coefficient")
    this.write("network-scores" + suffix + "-clustering-coefficient", dataDF)
     */
  }

  def write(file: String, df: DataFrame): Unit = {
    var dataDF = df.select(df.columns.map(c => df.col(c).cast("string")): _*)
    var headerDF = spark.createDataFrame(List(Row.fromSeq(dataDF.columns.toSeq)).asJava, dataDF.schema)
    var outputFolder = outputDir + file + ".csv.dir"
    headerDF.union(dataDF).write.option("header", "false").mode(SaveMode.Overwrite).csv(outputFolder)
  }
}
