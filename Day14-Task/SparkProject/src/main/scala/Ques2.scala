import org.apache.spark.sql.SparkSession

// Ques2 - Create two RDDs containing numbers and write a Spark program to compute their Cartesian product using RDD transformations.

object Ques2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Ques2")
      .master("local[*]")
      .getOrCreate()

    val numbers1 = List(1, 2, 3)
    val numbers2 = List(4, 5, 6)

    val rdd1 = spark.sparkContext.parallelize(numbers1)
    val rdd2 = spark.sparkContext.parallelize(numbers2)

    val cartesianProduct = rdd1.cartesian(rdd2)

    val result = cartesianProduct.collect()
    println("Cartesian Product of RDDs:")
    result.foreach(println)

    spark.stop()
  }
}
