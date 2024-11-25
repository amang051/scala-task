package Exercise1

import org.apache.spark.sql.SparkSession
import scala.util.Random

/*
Exercise 1: Understanding RDD and Partitioning
  Objective: Create and manipulate an RDD while understanding its partitions.
  Task:
  Load a large text file (or create one programmatically with millions of random numbers).
Perform the following:
  Check the number of partitions for the RDD.
Repartition the RDD into 4 partitions and analyze how the data is distributed.
  Coalesce the RDD back into 2 partitions.
Print the first 5 elements from each partition.
  Expected Analysis:
  View the effect of repartition and coalesce in the Spark UI (stages, tasks).
*/

object Exercise1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("RDD Partitioning")
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val data = (1 to 1000000).map(_ => Random.nextInt(1000).toString)

    val rdd = sc.parallelize(data)
    println(s"Number of partitions before repartitioning: ${rdd.getNumPartitions}")

    val rddRepartitioned = rdd.repartition(4)
    println(s"Number of partitions after repartitioning: ${rddRepartitioned.getNumPartitions}")

    val partitionsAfterRepartition = rddRepartitioned.glom().collect()
    partitionsAfterRepartition.zipWithIndex.foreach { case (partition, index) =>
      println(s"Number of integers in ${index + 1} partition: ${partition.length}")
    }

    val rddCoalesced = rddRepartitioned.coalesce(2)
    println(s"Number of partitions after coalescing: ${rddCoalesced.getNumPartitions}")

    val partitions = rddCoalesced.glom().collect()
    partitions.zipWithIndex.foreach { case (partition, index) =>
      println(s"First 5 elements from partition ${index + 1}: ${partition.take(5).mkString(", ")}")
    }

    Thread.sleep(3000000) // Waiting to analyze in Spark UI

    spark.stop()
  }
}

/*
Output in console:

Number of partitions before repartitioning: 12
Number of partitions after repartitioning: 4
Number of integers in 1 partition: 250000
Number of integers in 2 partition: 250001
Number of integers in 3 partition: 250000
Number of integers in 4 partition: 249999
Number of partitions after coalescing: 2
First 5 elements from partition 1: 800, 926, 968, 863, 793
First 5 elements from partition 2: 164, 755, 818, 356, 21
*/
