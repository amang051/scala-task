import org.apache.spark.sql.SparkSession

// Ques7 - Write a Spark program to perform a union operation on two RDDs of integers and remove duplicate elements from the resulting RDD.

object Ques7 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Ques7")
      .master("local[*]")
      .getOrCreate()

    val list1 = List(1, 2, 3, 4, 5)
    val list2 = List(4, 5, 6, 7, 8)

    val l1 = spark.sparkContext.parallelize(list1)
    val l2 = spark.sparkContext.parallelize(list2)

    val res = l1.union(l2)
    val result = res.collect()

    println(s"Union :")
    result.foreach(println)

    spark.stop()
  }
}
