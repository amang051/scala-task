package CS3

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, expr, struct, to_json}
import org.apache.spark.sql.streaming.Trigger

object Producer {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Broadcast Join Example")
      .config("spark.hadoop.fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      .config("spark.hadoop.fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
      .config("spark.hadoop.google.cloud.auth.service.account.enable", "true")
      .config("spark.hadoop.google.cloud.auth.service.account.json.keyfile", "/Users/amangarg/spark-gcs-key.json")
      .master("local[*]")
      .getOrCreate()

    val transactionLogsPath = "gs://first-job-bucket/Day18_19/transaction_logs"

    // Read the CSV file as a static DataFrame
    val transactionDF = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(transactionLogsPath)
      .toDF("userId", "transactionId", "amount")

    // Convert the static DataFrame into a streaming DataFrame by attaching a rate stream
    val rateStream = spark.readStream
      .format("rate")
      .option("rowsPerSecond", 1) // Emit one row per second
      .load()

    // Attach an index to simulate streaming from the static DataFrame
    val indexedCsvDF = transactionDF.withColumn("index", expr("row_number() over (order by userId) - 1"))

    // Join the rate stream with the static DataFrame to emit one row per second
    val streamingDF = rateStream
      .withColumn("index", col("value"))
      .join(indexedCsvDF, "index")
      .select(col("userId").cast("string").as("key"), to_json(struct("userId", "transactionId", "amount")).as("value"))

    // Write the streaming data to Kafka
    val kafkaSink = streamingDF.writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("topic", "transactions")
      .option("checkpointLocation", "/tmp/spark-kafka-checkpoints")
      .trigger(Trigger.ProcessingTime("1 second"))
      .start()

    kafkaSink.awaitTermination()
  }
}