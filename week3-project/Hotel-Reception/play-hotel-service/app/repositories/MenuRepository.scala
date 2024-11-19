package repositories

import models.Menu
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MenuRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private class MenuTable(tag: Tag) extends Table[Menu](tag, "menu") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def foodItem = column[String]("foodItem")
    def foodType = column[String]("foodType")
    def price = column[Double]("price")
    def * = (id.?, foodItem, foodType, price) <> ((Menu.apply _).tupled, Menu.unapply)
  }

  private val menus = TableQuery[MenuTable]

//  def listMenu(fodType: Option[String]): Future[Seq[Menu]] = {
//    val query = menus.filterOpt(fodType) { case (menu, s) => menu.foodType === s }
//
//    db.run(query.result)
//  }
//
//  def insertMenuItem(insertList: Seq[Menu]): Future[Option[Int]] = db.run {
//    menus.delete.andThen(menus ++= insertList)
//  }
}

