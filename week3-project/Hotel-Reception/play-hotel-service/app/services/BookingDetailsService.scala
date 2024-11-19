package services

import models.{BookingDetails, Room}
import repositories.{BookingDetailsRepository, GuestRepository, RoomRepository}

import java.time.LocalDate
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BookingDetailsService @Inject() (bookingDetailsRepository: BookingDetailsRepository, roomRepository: RoomRepository, guestRepository: GuestRepository, kafkaProducerFactory: KafkaProducerFactory)
                             (implicit ex: ExecutionContext) {
  def create(booking: BookingDetails): Future[BookingDetails] = {
    guestRepository.getGuestById(booking.guestId).flatMap { guestDetails =>
      getAvailableRooms(booking.category, booking.checkInDate, booking.checkOutDate).flatMap { availableRooms =>
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

  def getAvailableRooms(category: String, checkInDate: LocalDate, checkOutDate: LocalDate): Future[Seq[Room]] = {
    // Step 1: Get all rooms for the specified category
    roomRepository.findRoomsByCategory(category).flatMap { rooms =>
      // Step 2: For each room, fetch the existing bookings and check for availability
      val availableRoomsFutures = rooms.map { room =>
        bookingDetailsRepository.getBookingsForRoom(room.id.getOrElse(0L)).map { bookings =>
          // Step 3: Check if there are any overlapping bookings
          val isRoomAvailable = !bookings.exists { booking =>
            (checkInDate.isBefore(booking.checkOutDate) && checkOutDate.isAfter(booking.checkInDate))
          }
          if (isRoomAvailable) Some(room) else None
        }
      }

      // Step 4: Collect all the available rooms (filter out None values)
      Future.sequence(availableRoomsFutures).map { availableRooms =>
        availableRooms.flatten
      }
    }
  }
}