package CS2

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{avg, sum}

import scala.util.Random

object Exercise2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark DataFrame Caching Example")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Generate a sample dataset for sales data
    val numRecords = 1000000
    val salesData = (1 to numRecords).map { id =>
      val region = List("North", "South", "East", "West")(Random.nextInt(4))
      val amount = Random.nextDouble() * 1000
      val category = List("Electronics", "Clothing", "Groceries")(Random.nextInt(3))
      (id, region, amount, category)
    }.toDF("saleId", "region", "amount", "category")

    // Transformation operation
    def performTransformations(df: org.apache.spark.sql.DataFrame): Unit = {
      val byRegion = df.groupBy("region").agg(avg("amount").as("avgAmount"))
      val byCategory = df.groupBy("category").agg(sum("amount").as("totalSales"))
      byRegion.show(5)
      byCategory.show(5)
    }

    // Measure execution time
    def time[T](block: => T): Double = {
      val start = System.nanoTime()
      block
      (System.nanoTime() - start) / 1e6
    }

    // Without caching
    val timeWithoutCache = time {
      performTransformations(salesData)
      performTransformations(salesData)
    }
    println(s"Time taken without caching: $timeWithoutCache ms")

    // With caching
    salesData.cache() // Cache the DataFrame
    val timeWithCache = time {
      performTransformations(salesData)
      performTransformations(salesData)
    }
    println(s"Time taken with caching: $timeWithCache ms")

    spark.stop()
  }
}
