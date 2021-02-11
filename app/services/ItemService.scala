package services

import models.{Item, Items}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ItemService {

  val db = Database.forConfig("myDB")
  val itemDb = TableQuery[Items]

  def readAll(): Future[Seq[Item]] = {
    db.run(itemDb.result)
  }

  def create(t: Item): Future[String] = {
    db.run(itemDb += t).map(f => "Item has been successfully added").recover {
      case exception: Exception => exception.getCause.getMessage
    }
  }

}
