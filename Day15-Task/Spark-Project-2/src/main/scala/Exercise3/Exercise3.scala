package Exercise3

import org.apache.spark.sql.SparkSession

/*
Exercise 3: Analyzing Tasks and Executors
Objective: Understand how tasks are distributed across executors in local mode.
Task:

Create an RDD of strings with at least 1 million lines (e.g., lorem ipsum or repetitive text).
Perform a transformation pipeline:
Split each string into words.
Map each word to (word, 1).
Reduce by key to count word occurrences.
Set spark.executor.instances to 2 and observe task distribution in the Spark UI.
Expected Analysis:

Compare task execution times across partitions and stages in the UI.
Understand executor and task allocation for a local mode Spark job.
*/

object Exercise3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("Analyzing Tasks and Executors")
      .master("local[2]")
      .config("spark.executor.instances", "2")  // Set number of executors
      .getOrCreate()

    val sc = spark.sparkContext

    // Create an RDD of strings with 1 million lines
    val text = "lorem ipsum dolor sit amet consectetur adipiscing elit"
    val linesRDD = sc.parallelize(Seq.fill(1000000)(text))

    val wordsRDD = linesRDD.flatMap(line => line.split(" "))
    val wordCountsRDD = wordsRDD.map(word => (word, 1))
    val wordCountResults = wordCountsRDD.reduceByKey(_ + _)

    wordCountResults.saveAsTextFile("src/main/scala/Exercise3/word_count_output")

    wordCountResults.take(10).foreach(println)

    Thread.sleep(3000000) // Waiting to analyze in Spark UI

    spark.stop()
  }
}

