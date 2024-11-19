package services

import models.Room
import repositories.{BookingDetailsRepository, RoomRepository}

import java.time.LocalDate
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RoomService @Inject() (roomRepository: RoomRepository, bookingDetailsRepository: BookingDetailsRepository)
                            (implicit ex: ExecutionContext) {
  def create(room: Room): Future[Room] = {
    roomRepository.create(room)
  }

  def getAvailableRooms(category: String, checkInDate: LocalDate, checkOutDate: LocalDate): Future[Seq[Room]] = {
    roomRepository.findRoomsByCategory(category).flatMap { rooms =>
      val availableRoomsFutures = rooms.map { room =>
        bookingDetailsRepository.getBookingsForRoom(room.id.getOrElse(0L)).map { bookings =>
          val isRoomAvailable = !bookings.exists { booking =>
            checkInDate.isBefore(booking.checkOutDate) && checkOutDate.isAfter(booking.checkInDate)
          }
          if (isRoomAvailable) Some(room) else None
        }
      }

      Future.sequence(availableRoomsFutures).map { availableRooms =>
        availableRooms.flatten
      }
    }
  }
}
