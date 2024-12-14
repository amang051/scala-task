package producer

import akka.actor.{Actor, ActorSystem, Props}
import akka.kafka.ProducerSettings
import org.apache.kafka.clients.producer.{Producer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import spray.json._

import java.time.Instant
import scala.concurrent.duration._
import scala.util.Random

// Case class to represent a movie rating
case class MovieRating(userId: Int, movieId: Int, rating: Double, timestamp: Long)

// JSON protocol for serializing MovieRating
object MovieRatingJsonProtocol extends DefaultJsonProtocol {
  implicit val movieRatingFormat: RootJsonFormat[MovieRating] = jsonFormat4(MovieRating)
}

import producer.MovieRatingJsonProtocol._

// Producer Actor class
class MovieRatingProducer extends Actor {
  import context.dispatcher // Required for scheduling

  private val random = new Random()

  // Generate a random movie rating
  private def generateRandomRating(): MovieRating = {
    val userId = random.nextInt(943) + 1         // User IDs between 1 and 1000
    val movieId = random.nextInt(1682) + 1       // Movie IDs between 1 and 100
    val rating = (random.nextInt(10) + 1) * 0.5  // Ratings between 0.5 and 5.0 (step of 0.5)
    val timestamp = Instant.now().toEpochMilli   // Current timestamp
    MovieRating(userId, movieId, rating, timestamp)
  }

  override def preStart(): Unit = {
    // Schedule the generateAndSendRating method every 50 milliseconds
    context.system.scheduler.scheduleWithFixedDelay(0.milliseconds, 5000.milliseconds, self, "generate")
  }

  def receive: Receive = {
    case "generate" =>
      val rating = generateRandomRating()
      val jsonRating = rating.toJson.compactPrint
      val record = new ProducerRecord[String, String]("movie-ratings", jsonRating)
      KafkaProducer.producer.send(record)
      println(s"Published rating: $jsonRating")
  }
}

// Kafka producer
object KafkaProducer {
  private val producerSettings: ProducerSettings[String, String] = ProducerSettings(
    ActorSystem("KafkaProducerSystem"),
    new StringSerializer,
    new StringSerializer
  ).withBootstrapServers("localhost:9092")

  val producer: Producer[String, String] = producerSettings.createKafkaProducer()
}

object MovieRatingProducerApp extends App {
  private val system = ActorSystem("MovieRatingSystem")

  val producer = system.actorOf(Props[MovieRatingProducer], "MovieRatingProducer")
}