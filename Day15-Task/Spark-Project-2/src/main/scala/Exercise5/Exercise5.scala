package Exercise5

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/*
Exercise 5: Partitioning Impact on Performance
Objective: Understand the impact of partitioning on performance and data shuffling.
Task:

Load a large dataset (e.g., a CSV or JSON file) into an RDD.
Partition the RDD into 2, 4, and 8 partitions separately and perform the following tasks:
Count the number of rows in the RDD.
Sort the data using a wide transformation.
Write the output back to disk.
Compare execution times for different partition sizes.
Expected Analysis:

Observe how partition sizes affect shuffle size and task distribution in the Spark UI.
Understand the trade-off between too many and too few partitions.
*/

object Exercise5 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("Partitioning Impact on Performance")
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val filePath = "/Users/amangarg/Downloads/VS Code/all_stock_data.csv"

    val dataRDD: RDD[String] = sc.textFile(filePath)

    def processData(rdd: RDD[String], numPartitions: Int): Unit = {
      val repartitionedRDD = rdd.repartition(numPartitions)

      val rowCount = repartitionedRDD.count()
      println(s"Number of rows in RDD with $numPartitions partitions: $rowCount")

      val sortedRDD = repartitionedRDD.sortBy(identity)

      val outputDir = s"src/main/scala/Exercise5/partitioned_data_$numPartitions"
      sortedRDD.saveAsTextFile(outputDir)
    }

    for (numPartitions <- List(2, 4, 8)) {
      val startTime = System.nanoTime()

      // Call the processing function with the specific number of partitions
      processData(dataRDD, numPartitions)

      val endTime = System.nanoTime()
      val duration = (endTime - startTime) / 1e9
      println(s"Time taken for $numPartitions partitions: $duration seconds")
    }

    Thread.sleep(3000000) // Waiting to analyze in Spark UI

    spark.stop()
  }
}


/*
Output in console:

Number of rows in RDD with 2 partitions: 34646259
Time taken for 2 partitions: 73.432903875 seconds

Number of rows in RDD with 4 partitions: 34646259
Time taken for 4 partitions: 46.205958625 seconds

Number of rows in RDD with 8 partitions: 34646259
Time taken for 8 partitions: 36.464426833 seconds
 */
