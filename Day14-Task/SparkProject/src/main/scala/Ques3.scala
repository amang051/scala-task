import org.apache.spark.sql.SparkSession

// Ques3 - Create an RDD from a list of integers and filter out all even numbers using a Spark program.

object Ques3 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Ques3")
      .master("local[*]")
      .getOrCreate()

    val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    val numbersRDD = spark.sparkContext.parallelize(numbers)

    val oddNumbersRDD = numbersRDD.filter(num => num % 2 == 0)

    val result = oddNumbersRDD.collect()
    println("Even Numbers:")
    result.foreach(println)

    spark.stop()
  }
}
