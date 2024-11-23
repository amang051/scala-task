import org.apache.spark.sql.SparkSession

//Ques1 - Given a collection of strings, write a Spark program to count the total number of words in the collection using RDD transformations and actions.

object Ques1 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Ques1")
      .master("local[*]")
      .getOrCreate()

    val temp = List(
      "Spark is fast and reliable",
      "RDDs provide distributed data structures",
      "Word count example using RDD"
    )

    val parallel = spark.sparkContext.parallelize(temp);
    val words = parallel.flatMap(line => line.split(" "))
    val count = words.count()


    println(s"Total number of words: $count")

    spark.stop()
  }
}
