package repositories

import models.Room

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

@Singleton
class RoomRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private class RoomTable(tag: Tag) extends Table[Room](tag, "room") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def floor = column[Int]("floor")
    def category = column[String]("category")
    def price = column[Double]("price")

    def * = (id.?, floor, category, price) <> ((Room.apply _).tupled, Room.unapply)
  }

  private val rooms = TableQuery[RoomTable]

  def create(room: Room): Future[Room] = {
    val insertQuery = rooms returning rooms.map(_.id) into ((roomData, id) => roomData.copy(id = Some(id)))

    db.run(insertQuery += room)
  }

  private def getRoomById(roomId: Long): Future[Room] = {
    db.run(rooms.filter(_.id === roomId).result.head)
  }

  def findRoomsByCategory(category: String): Future[Seq[Room]] = {
    db.run(rooms.filter(room => room.category === category).result)
  }
}
