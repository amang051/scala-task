import org.apache.spark.sql.SparkSession

//Ques9 - Create an RDD of integers from 1 to 100 and write a Spark program to compute their sum using an RDD action.

object Ques9 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Ques9")
      .master("local[*]")
      .getOrCreate()


    val nums = spark.sparkContext.parallelize(1 to 100)

    val sum = nums.reduce(_ + _)

    println(s"Total sum : $sum")

    spark.stop()
  }
}
