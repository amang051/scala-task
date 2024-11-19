package services

import models.{BookingDetails, Guest, KafkaMessageFormat}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import play.api.libs.json._
import services.MessageTeam.{GUEST, RESTAURANT_SERVICE, ROOM_SERVICE, WIFI_SERVICE}

import java.util.Properties
import javax.inject._

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

  def sendMenu(guest: Guest): Unit = {
    val record: ProducerRecord[String, String] = {
      val kafkaMessageFormat = KafkaMessageFormat(
        receiver=GUEST,
        messageType="Menu",
        message= s"Today's Menu is Biryani, for guest: ${guest.name}"
      )

      val jsonMessage: String = Json.stringify(Json.toJson(kafkaMessageFormat))
      new ProducerRecord[String, String]("hotel-topic", jsonMessage)
    }

    producer.send(record)
  }

}