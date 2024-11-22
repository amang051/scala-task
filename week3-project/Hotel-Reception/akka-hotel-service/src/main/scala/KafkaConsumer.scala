import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.Sink
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import java.io.{BufferedWriter, FileWriter}
import spray.json._
import JsonFormats._

object HotelReceptionMsgReceivers {
  val ROOM_SERVICE = "ROOM_SERVICE"
  val WIFI_SERVICE = "WIFI_SERVICE"
  val RESTAURANT_SERVICE = "RESTAURANT_SERVICE"
  val GUEST = "GUEST"
}

object MessageTopics {
  val HOTEL_RECEPTION_TOPIC = "hotel-topic"
}

class HotelReceptionFileWriterActor() extends Actor {
  def receive: Receive = {
    case (fileName: String, messageType: String, message: String) =>
      val bw = new BufferedWriter(new FileWriter(fileName, true))
      bw.write(s"$messageType :: $message")
      bw.newLine()
      bw.close()
  }
}

class RoomMessageListener(fileWriterActor: ActorRef) extends Actor {
  override def receive: Receive = {
    case msg: KafkaMessageFormat =>
      println("Room Service Message Listener consumes the message")
      fileWriterActor ! ("src/main/scala/messages/hotelReception/roomService.txt", msg.messageType, msg.message)
  }
}

class WifiMessageListener(fileWriterActor: ActorRef) extends Actor {
  override def receive: Receive = {
    case msg: KafkaMessageFormat =>
      println("Wifi service Message Listener consumes the message")
      fileWriterActor ! ("src/main/scala/messages/hotelReception/wifiService.txt", msg.messageType, msg.message)
  }
}

class RestaurantMessageListener(fileWriterActor: ActorRef) extends Actor {
  override def receive: Receive = {
    case msg: KafkaMessageFormat =>
      println("Restaurant Message Listener consumes the message")
      fileWriterActor ! ("src/main/scala/messages/hotelReception/restaurantService.txt", msg.messageType, msg.message)
  }
}

class GuestMessageListener(fileWriterActor: ActorRef) extends Actor {
  override def receive: Receive = {
    case msg: KafkaMessageFormat =>
      println("Guest Message Listener consumes the message")
      fileWriterActor ! ("src/main/scala/messages/hotelReception/guest.txt", msg.messageType, msg.message)
  }
}

class HotelReceptionListener(roomMessageListener: ActorRef,
                              wifiMessageListener: ActorRef,
                              restaurantMessageListener: ActorRef,
                              guestMessageListener: ActorRef
                             )extends Actor {
  override def receive: Receive = {
    case msg: KafkaMessageFormat => msg.receiver match {
      case HotelReceptionMsgReceivers.ROOM_SERVICE =>
        roomMessageListener ! msg
      case HotelReceptionMsgReceivers.WIFI_SERVICE =>
        wifiMessageListener ! msg
      case HotelReceptionMsgReceivers.RESTAURANT_SERVICE =>
        restaurantMessageListener ! msg
      case HotelReceptionMsgReceivers.GUEST =>
        guestMessageListener ! msg
    }
  }
}

object KafkaConsumer {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("MessagingConsumerSystem")

    val emFileWriterActor: ActorRef = system.actorOf(Props[HotelReceptionFileWriterActor], "HotelReceptionFileWriterActor")

    // Create the actors for all the event management listeners
    val roomMessageListener: ActorRef = system.actorOf(Props(new RoomMessageListener(emFileWriterActor)), "RoomMessageListener")
    val wifiMessageListener: ActorRef = system.actorOf(Props(new WifiMessageListener(emFileWriterActor)), "WifiMessageListener")
    val restaurantMessageListener: ActorRef = system.actorOf(Props(new RestaurantMessageListener(emFileWriterActor)), "RestaurantMessageListener")
    val guestMessageListener: ActorRef = system.actorOf(Props(new GuestMessageListener(emFileWriterActor)), "GuestMessageListener")

    // Create the actor for project: event-management
    val hotelReceptionListener: ActorRef = system.actorOf(Props(new HotelReceptionListener(
      roomMessageListener, wifiMessageListener, restaurantMessageListener, guestMessageListener
    )), "HotelReceptionListener")

    val consumerSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers("kafka1"+":9092")
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    // Create and start the consumers (i.e, messageListeners)
    def listeners(topic: String, listener: ActorRef): Unit = {
      Consumer
        .plainSource(consumerSettings, Subscriptions.topics(topic))
        .map{ record => record.value().parseJson.convertTo[KafkaMessageFormat] }
        .runWith(
          Sink.actorRef[KafkaMessageFormat](
            ref = listener,
            onCompleteMessage = "complete",
            onFailureMessage = (throwable: Throwable) => s"Exception encountered"
          )
        )
    }

    // Configure listeners
    listeners(MessageTopics.HOTEL_RECEPTION_TOPIC, hotelReceptionListener)
  }
}