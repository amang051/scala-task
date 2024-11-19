package services

import models.{BookingDetails, Room}
import repositories.{BookingDetailsRepository, GuestRepository, RoomRepository}

import java.time.LocalDate
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BookingDetailsService @Inject() (bookingDetailsRepository: BookingDetailsRepository, roomService: RoomService,
                                       guestRepository: GuestRepository, kafkaProducerFactory: KafkaProducerFactory)
                             (implicit ex: ExecutionContext) {
  def create(booking: BookingDetails): Future[BookingDetails] = {
    guestRepository.getGuestById(booking.guestId).flatMap { guestDetails =>
      roomService.getAvailableRooms(booking.category, booking.checkInDate, booking.checkOutDate).flatMap { availableRooms =>
        availableRooms.headOption match {
          case Some(firstAvailableRoom) =>
            val updatedBooking = booking.copy(roomId = Some(firstAvailableRoom.id.getOrElse(0L)))

            bookingDetailsRepository.create(updatedBooking).map { savedBooking =>
              kafkaProducerFactory.sendGuestBookingMessage(guestDetails, savedBooking)
              savedBooking
            }

          case None =>
            Future.failed(new Exception("No available rooms for the selected dates and category"))
        }
      }
    }
  }

  def findBookingById(bookingId: Long): Future[BookingDetails] = {
    bookingDetailsRepository.findBookingById(bookingId)
  }

  def getBookingsByDate(date: LocalDate): Future[Seq[BookingDetails]] = {
    bookingDetailsRepository.getBookingsByDate(date)
  }
}