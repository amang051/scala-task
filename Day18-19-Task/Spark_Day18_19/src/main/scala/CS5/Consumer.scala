package CS5

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{broadcast, col, from_json}
import org.apache.spark.sql.types.{DoubleType, StringType, StructType}

object Consumer {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Generate Enriched Data")
      .config("spark.hadoop.fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      .config("spark.hadoop.fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
      .config("spark.hadoop.google.cloud.auth.service.account.enable", "true")
      .config("spark.hadoop.google.cloud.auth.service.account.json.keyfile", "/Users/amangarg/spark-gcs-key.json")
      .master("local[*]")
      .getOrCreate()

    val userDetailsPath = "gs://first-job-bucket/Day18_19/user_details"
    val outputPath = "gs://first-job-bucket/Day18_19/enriched_orders"

    // Set log level
    spark.sparkContext.setLogLevel("WARN")

    val kafkaSchema = new StructType()
      .add("orderId", StringType)
      .add("userId", StringType)
      .add("amount", DoubleType)

    // Load the user details dataset from GCS
    val userDetailsDF = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(userDetailsPath)
      .cache()

    // Read streaming data from Kafka
    val kafkaStreamDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "orders")
      .option("startingOffsets", "latest")
      .load()

    // Parse Kafka messages (JSON) and extract fields
    val parsedStreamDF = kafkaStreamDF
      .selectExpr("CAST(value AS STRING) as jsonString")
      .select(from_json(col("jsonString"), kafkaSchema).as("data"))
      .select("data.*")

    // Enrich Kafka data with user details using a join
    val enrichedStreamDF = parsedStreamDF.join(broadcast(userDetailsDF), Seq("userId"), "left_outer").drop(userDetailsDF("userId"))

    // Write the enriched data to GCS in JSON format
    val query = enrichedStreamDF.writeStream
      .outputMode("append")
      .format("json")
      .option("path", outputPath)
      .option("checkpointLocation", "/tmp/spark-kafka-enrichment-checkpoints")
      .start()

    query.awaitTermination()
  }
}
