package services

import models.{BookingDetails, Guest, KafkaMessageFormat, Menu}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import play.api.libs.json._
import services.MessageTeam.{GUEST, RESTAURANT_SERVICE, ROOM_SERVICE, WIFI_SERVICE}

import java.util.Properties
import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

object MessageTeam {
  val ROOM_SERVICE = "ROOM_SERVICE"
  val WIFI_SERVICE = "WIFI_SERVICE"
  val RESTAURANT_SERVICE = "RESTAURANT_SERVICE"
  val GUEST = "GUEST"
}

@Singleton
class KafkaProducerFactory @Inject() {
  private val props = new Properties()

  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  private val producer = new KafkaProducer[String, String](props)

  def sendGuestBookingMessage(guest: Guest, bookingDetails: BookingDetails): Unit = {
    val bookingInfoMessage = s"Booking successful for Guest: ${guest.name}, Room ID: ${bookingDetails.roomId.getOrElse(0L)}, Check-in: ${bookingDetails.checkInDate}, Check-out: ${bookingDetails.checkOutDate}"

    val guestInfoMessage = s"Guest Name: ${guest.name}, Email: ${guest.email}, Address: ${guest.address}"

    val teams = Seq(WIFI_SERVICE, ROOM_SERVICE, RESTAURANT_SERVICE)

    val producerRecords = teams.map { team =>
      val kafkaMessageFormat = KafkaMessageFormat(
        receiver = team,
        messageType = "ROOM_ALLOCATION",
        message = team match {
          case WIFI_SERVICE => s"$guestInfoMessage\n$bookingInfoMessage"
          case ROOM_SERVICE => s"$guestInfoMessage\n$bookingInfoMessage"
          case RESTAURANT_SERVICE => s"$guestInfoMessage\n$bookingInfoMessage"
        }
      )

      val jsonMessage: String = Json.stringify(Json.toJson(kafkaMessageFormat))
      new ProducerRecord[String, String]("hotel-topic", jsonMessage)
    }

    // Send each message to Kafka
    producerRecords.foreach { record =>
      producer.send(record)
    }
  }

  def sendMenu(guest: Guest, menuItems: Future[Seq[Menu]]): Unit = {
    val msg = createMessage(menuItems)
    val record: ProducerRecord[String, String] = {
      val kafkaMessageFormat = KafkaMessageFormat(
        receiver=GUEST,
        messageType="Menu",
        message= s"Today's Menu is ${msg}, for guest: ${guest.id} : ${guest.name}"
      )

      val jsonMessage: String = Json.stringify(Json.toJson(kafkaMessageFormat))
      new ProducerRecord[String, String]("hotel-topic", jsonMessage)
    }

    producer.send(record)
  }

  private def createMessage(menuItems: Future[Seq[Menu]]): String = {
    val items = Await.result(menuItems, 10.seconds)

    // Build the message string
    val messageBuilder = new StringBuilder

    items.foreach { menu =>
      messageBuilder.append(s"${menu.foodName} - ${menu.price}, ")
    }

    // Remove the trailing comma and space at the end
    if (messageBuilder.nonEmpty) {
      messageBuilder.setLength(messageBuilder.length - 2)
    }

    messageBuilder.toString()
  }

}