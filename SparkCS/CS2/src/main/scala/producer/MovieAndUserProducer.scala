package producer

import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import spray.json._
import org.apache.commons.csv.CSVFormat

import java.io.File
import scala.concurrent.duration.DurationInt
import scala.io.Source.fromFile
import scala.util.{Random, Try}

// Case classes
case class MovieMetadata(movieId: Int, title: String, genre: String)
case class UserDemographic(userId: Int, gender: String, age: Int, occupation: String, zipCode: String)

// JSON serialization
object JsonProtocol extends DefaultJsonProtocol {
  implicit val movieMetadataFormat: RootJsonFormat[MovieMetadata] = jsonFormat3(MovieMetadata)
  implicit val userDemographicFormat: RootJsonFormat[UserDemographic] = jsonFormat5(UserDemographic)
}
import JsonProtocol._

object KafkaCsvProducerApp extends App {
  implicit val system: ActorSystem = ActorSystem("KafkaCsvProducerSystem")

  // Kafka Producer settings
  val producerSettings: ProducerSettings[String, String] = ProducerSettings(
    system,
    new StringSerializer,
    new StringSerializer
  ).withBootstrapServers("localhost:9092")

  // Helper: Create a ProducerRecord
  private def createProducerRecord(topic: String, key: String, value: String): ProducerRecord[String, String] =
    new ProducerRecord[String, String](topic, key, value)

  // Read CSV: Generic function
  private def readCsv[T](filePath: String, parseLine: Array[String] => T): List[T] = {
    val reader = fromFile(filePath)
    try {
      reader.getLines().drop(1).map(_.split(",")).map(parseLine).toList
    } finally {
      reader.close()
    }
  }

  // Parse CSV files
  private val movies = readCsv("/Users/amangarg/Downloads/ml-1m/movies.csv", cols => MovieMetadata(cols(0).toInt, cols(1), cols(2)))
  private val users = readCsv("/Users/amangarg/Downloads/ml-1m/users.csv", cols => UserDemographic(cols(0).toInt, cols(1), cols(2).toInt, cols(3), cols(4)))

  // Create Akka Sources
  private val movieSource = Source(movies).map { movie =>
    val json = movie.toJson.compactPrint
    createProducerRecord("movies-metadata", movie.movieId.toString, json)
  }

  private val userSource = Source(users).map { user =>
    val json = user.toJson.compactPrint
    createProducerRecord("users-demographics", user.userId.toString, json)
  }

  // Publish to Kafka topics
  movieSource.runWith(Producer.plainSink(producerSettings))
  userSource.runWith(Producer.plainSink(producerSettings))

  println("Publishing data from CSV files to Kafka topics...")
}
