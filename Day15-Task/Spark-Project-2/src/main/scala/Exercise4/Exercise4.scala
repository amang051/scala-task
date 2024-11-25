package Exercise4

import org.apache.spark.sql.SparkSession

/*
Exercise 4: Exploring DAG and Spark UI
Objective: Analyze the DAG and understand the stages involved in a complex Spark job.
Task:

Create an RDD of integers from 1 to 10,000.
Perform a series of transformations:

Filter: Keep only even numbers.
Map: Multiply each number by 10.
Map: Generate a tuple where the first element is the remainder when dividing the number by 100 (key), and the second is the number itself (value).
ReduceByKey: Group by the remainder (key) and compute the sum of the values.
Finally, perform an action to collect the results and display them.
Expected Analysis:

Analyze the DAG generated for the job and how Spark breaks it into stages.
Compare execution times of stages and tasks in the Spark UI.
*/

object Exercise4 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("DAG Analysis and Spark UI")
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val numbersRDD = sc.parallelize(1 to 10000)

    val evenNumbersRDD = numbersRDD.filter(_ % 2 == 0)
    val multipliedRDD = evenNumbersRDD.map(_ * 10)
    val keyValueRDD = multipliedRDD.map(num => (num % 100, num))
    val reducedByKeyRDD = keyValueRDD.reduceByKey(_ + _)

    val results = reducedByKeyRDD.collect()

    results.foreach(println)

    Thread.sleep(3000000) // Waiting to analyze in Spark UI

    spark.stop()
  }
}
