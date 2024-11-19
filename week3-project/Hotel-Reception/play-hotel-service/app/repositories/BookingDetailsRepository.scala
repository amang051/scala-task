package repositories

import models.BookingDetails

import java.time.LocalDate
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

@Singleton
class BookingDetailsRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private class BookingDetailsTable(tag: Tag) extends Table[BookingDetails](tag, "bookingDetails") {
    def id = column[Long]("bookingId", O.PrimaryKey, O.AutoInc)
    def guestId = column[Long]("guestId")
    def roomId = column[Option[Long]]("roomId")
    def category = column[String]("category")
    def startDate = column[LocalDate]("startDate")
    def endDate = column[LocalDate]("endDate")

    def * = (id.?, guestId, roomId, category, startDate, endDate) <> ((BookingDetails.apply _).tupled, BookingDetails.unapply)
  }

  private val bookingDetails = TableQuery[BookingDetailsTable]

  def create(booking: BookingDetails): Future[BookingDetails] = {
    val insertQuery = bookingDetails returning bookingDetails.map(_.id) into ((bookingData, id) => bookingData.copy(id = Some(id)))

    db.run(insertQuery += booking)
  }

  def findBookingById(bookingId: Long): Future[BookingDetails] = {
    db.run(bookingDetails.filter(_.id === bookingId).result.head)
  }

  def getBookingsByDate(date: LocalDate): Future[Seq[BookingDetails]] = {
    db.run(bookingDetails.filter(booking => booking.endDate === date).result)
  }

  def getCurrentBookingsByDate(date: LocalDate): Future[Seq[BookingDetails]] = {
    db.run(bookingDetails.filter(booking => booking.endDate >= date && booking.startDate <= date).result)
  }

  def getBookingsForRoom(roomId: Long): Future[Seq[BookingDetails]] = {
    db.run(bookingDetails.filter(booking => booking.roomId === roomId).result)
  }
}
