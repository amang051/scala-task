package models

import java.time.LocalDate
import play.api.libs.json.{Json, OFormat}

case class BookingDetails(id: Option[Long] = None, guestId: Long, roomId: Option[Long] = None, category: String,
                          checkInDate: LocalDate, checkOutDate: LocalDate)

object BookingDetails {
  implicit val format: OFormat[BookingDetails] = Json.format[BookingDetails]
}
