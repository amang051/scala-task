package repositories

import models.Guest

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

@Singleton
class GuestRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private class GuestTable(tag: Tag) extends Table[Guest](tag, "guest") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def age = column[Int]("age")
    def email = column[String]("email")
    def address = column[String]("address")
    def idProof = column[Option[Array[Byte]]]("idProof")
    def * = (id.?, name, age, email, address, idProof) <> ((Guest.apply _).tupled, Guest.unapply)
  }

  private val guests = TableQuery[GuestTable]

  def create(guest: Guest): Future[Guest] = {
    val insertQuery = guests returning guests.map(_.id) into ((bookingData, id) => bookingData.copy(id = Some(id)))

    db.run(insertQuery += guest)
  }

  def getGuestById(guestId: Long): Future[Guest] = {
    db.run(guests.filter(_.id === guestId).result.head)
  }

  def getGuestsByIds(guestIds: Seq[Long]): Future[Seq[Guest]] = {
    db.run {
      guests.filter(_.id inSet guestIds).result
    }
  }
}
