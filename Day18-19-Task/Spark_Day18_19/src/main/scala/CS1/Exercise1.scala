package CS1

import org.apache.spark.sql.SparkSession

import scala.util.Random

object Exercise1 {

  private val USER_DETAILS_PATH = "gs://first-job-bucket/Day18_19/user_details"
  private val TRANSACTION_LOGS_PATH = "gs://first-job-bucket/Day18_19/transaction_logs"

  private def generateDataset(spark: SparkSession): Unit = {
    import spark.implicits._
    // Function to generate random user details
    def generateUserDetails(numUsers: Int): Seq[(Int, String)] = {
      val random = new Random()
      (1 to numUsers).map { id =>
        (id, s"User_${random.alphanumeric.take(5).mkString}")
      }
    }

    // Function to generate random transaction logs
    def generateTransactionLogs(numTransactions: Int, numUsers: Int): Seq[(Int, String, Double)] = {
      val random = new Random()
      (1 to numTransactions).map { _ =>
        val userId = random.nextInt(numUsers) + 1
        val transactionId = s"txn_${random.alphanumeric.take(6).mkString}"
        val amount = random.nextDouble() * 1000
        (userId, transactionId, amount)
      }
    }

    // Generate random data
    val numUsers = 100
    val numTransactions = 10000

    val userDetailsDF = generateUserDetails(numUsers).toDF("userId", "userName")
    val transactionLogsDF = generateTransactionLogs(numTransactions, numUsers).toDF("userId", "transactionId", "amount")

    // Write data to GCS
    userDetailsDF.write
      .mode("overwrite")
      .option("header", "true")
      .csv(USER_DETAILS_PATH)

    transactionLogsDF.write
      .mode("overwrite")
      .option("header", "true")
      .csv(TRANSACTION_LOGS_PATH)

    println(s"Data successfully written to GCS at $USER_DETAILS_PATH and $TRANSACTION_LOGS_PATH")
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Broadcast Join Example")
      .config("spark.hadoop.fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      .config("spark.hadoop.fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
      .config("spark.hadoop.google.cloud.auth.service.account.enable", "true")
      .config("spark.hadoop.google.cloud.auth.service.account.json.keyfile", "/Users/amangarg/spark-gcs-key.json")
      .master("local[*]")
      .getOrCreate()

    generateDataset(spark)

    val userDetails = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(USER_DETAILS_PATH)
      .toDF("user_id", "name")

    val transactionLogs = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(TRANSACTION_LOGS_PATH)
      .toDF("user_id", "transaction_type", "amount")

    val startTime = System.nanoTime()
    // Use broadcasting for the small dataset
    val joinedDataDF = transactionLogs
      .join(userDetails, Seq("user_Id"))
    val endTime = System.nanoTime()
    val elapsedTime = (endTime - startTime) / 1e6
    println(s"Time taken without broadcast: $elapsedTime ms")

    val startTimeBroadcast = System.nanoTime()
    // Use broadcasting for the small dataset
    val joinedDataDFBroadcast = transactionLogs
      .join(org.apache.spark.sql.functions.broadcast(userDetails), Seq("user_Id"))
    val endTimeBroadcast = System.nanoTime()
    val elapsedTimeBroadcast = (endTimeBroadcast - startTimeBroadcast) / 1e6
    println(s"Time taken via broadcast: $elapsedTimeBroadcast ms")

    spark.stop()
  }
}
