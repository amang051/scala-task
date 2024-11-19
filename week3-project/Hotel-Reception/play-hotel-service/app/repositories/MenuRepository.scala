package repositories

import models.Menu
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random
import scala.util.Random.javaRandomToRandom

@Singleton
class MenuRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private class MenuTable(tag: Tag) extends Table[Menu](tag, "menu") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def foodName = column[String]("foodName")
    def price = column[Double]("price")
    def * = (id.?, foodName, price) <> ((Menu.apply _).tupled, Menu.unapply)
  }

  private val menus = TableQuery[MenuTable]

  def listMenu: Future[Seq[Menu]] = {
    // Raw SQL query to get 4 random records from the `menu` table
    val query = menus.take(10)  // Fetch a larger number to simulate random selection

    // Execute the query and randomly order the results in-memory
    db.run(query.result).map { results =>
      Random.shuffle(results).take(4)  // Shuffle and take the first 4 items
    }
  }
}

