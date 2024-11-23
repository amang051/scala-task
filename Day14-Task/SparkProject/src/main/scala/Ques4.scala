import org.apache.spark.sql.SparkSession

// Ques4 - Write a Spark program to count the frequency of each character in a given collection of strings using RDD transformations.

object Ques4 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Ques4")
      .master("local[*]")
      .getOrCreate();

    val temp = List("Spark", "is", "powerful", "and", "fast");
    val data = spark.sparkContext.parallelize(temp)

    val res = data.flatMap(line => line.toCharArray)
    val result = res.map(char => (char, 1))

    val x = result.reduceByKey(_ + _);
    val y = x.collect()

    println("Character Frequencies:")
    result.foreach { case (char, count) =>
      println(s"Character '$char' -> $count")
    }
    spark.stop()
  }
}
