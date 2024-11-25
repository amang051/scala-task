package Exercise2

import org.apache.spark.sql.SparkSession

/*
Exercise 2: Narrow vs Wide Transformations
Objective: Differentiate between narrow and wide transformations in Spark.
Task:

Create an RDD of numbers from 1 to 1000.
Apply narrow transformations: map, filter.
Apply a wide transformation: groupByKey or reduceByKey (simulate by mapping numbers into key-value pairs, e.g., (number % 10, number)).
Save the results to a text file.
Expected Analysis:

Identify how narrow transformations execute within a single partition, while wide transformations cause shuffles.
Observe the DAG in the Spark UI, focusing on stages and shuffle operations.
*/

object Exercise2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("Narrow vs Wide Transformations")
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val numbersRDD = sc.parallelize(1 to 1000)
    val mappedRDD = numbersRDD.map(x => (x % 10, x))
    val filteredRDD = mappedRDD.filter { case (key, value) => value % 2 == 0 }

    val groupedRDD = filteredRDD.groupByKey()
    groupedRDD.saveAsTextFile("src/main/scala/Exercise2/narrow_wide_groupByKey_transformations_output")
    groupedRDD.collect().foreach(println)

    val reducedByKeyRDD = filteredRDD.reduceByKey(_ + _)
    reducedByKeyRDD.saveAsTextFile("src/main/scala/Exercise2/narrow_wide_reduceByKey_transformations_output")
    reducedByKeyRDD.collect().foreach(println)

    Thread.sleep(3000000) // Waiting to analyze in Spark UI

    spark.stop()
  }
}

