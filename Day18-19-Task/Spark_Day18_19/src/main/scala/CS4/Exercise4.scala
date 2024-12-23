package CS4

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

import scala.util.Random

object Exercise4 {
  private val GCS_INPUT_PATH = "gs://first-job-bucket/Day18_19/cs4_input_data"
  private val GCS_OUTPUT_PATH = "gs://first-job-bucket/Day18_19/cs4_output_data"

  private def generateDataset(spark: SparkSession): Unit = {
    import spark.implicits._

    val random = new Random()
    val data = (1 to 10000).map { id =>
      val status = if(random.nextBoolean()) "completed" else "pending"
      val amount = random.nextDouble() * 1000
      (id, status, amount)
    }

    val df = data.toDF("id", "status", "amount")

    // Write DataFrame as a Parquet file to GCS
    df.write
      .mode("overwrite")
      .parquet(GCS_INPUT_PATH)

    println(s"Parquet file with 10,000 rows successfully written to $GCS_INPUT_PATH")
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Generate Data GCP Cloud")
      .config("spark.hadoop.fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      .config("spark.hadoop.fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
      .config("spark.hadoop.google.cloud.auth.service.account.enable", "true")
      .config("spark.hadoop.google.cloud.auth.service.account.json.keyfile", "/Users/amangarg/spark-gcs-key.json")
      .master("local[*]")
      .getOrCreate()

    generateDataset(spark)

    val transactionInputDF = spark.read.parquet(GCS_INPUT_PATH)
    val processedData = transactionInputDF.filter(col("status") === "completed")
    processedData.write.mode("overwrite").parquet(GCS_OUTPUT_PATH)

    println(s"Processed data successfully written to $GCS_OUTPUT_PATH")

    spark.stop()
  }
}
