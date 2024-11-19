package services

import models.Guest
import repositories.{BookingDetailsRepository, GuestRepository}

import java.time.LocalDate
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GuestService @Inject() (guestRepository: GuestRepository, bookingDetailsRepository: BookingDetailsRepository)
                             (implicit ex: ExecutionContext) {
  def create(guest: Guest): Future[Guest] = {
    guestRepository.create(guest)
  }

  def getCurrentGuests: Future[Seq[Guest]] = {
    val date = LocalDate.now()
    val currentBookings = bookingDetailsRepository.getCurrentBookingsByDate(date)
    currentBookings.flatMap { bookings =>
      val guestIds = bookings.map(_.guestId).distinct
      guestRepository.getGuestsByIds(guestIds)
    }
  }
}