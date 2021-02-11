package models

import play.api.data.Form
import play.api.data.Forms.{bigDecimal, mapping, nonEmptyText, number}
import slick.jdbc.MySQLProfile.api._


case class Item(id: Int = 1, name: String, price: BigDecimal)

case class Items(tag: Tag) extends Table[Item](tag, "items") {

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("NAME")

  def price = column[BigDecimal]("PRICE")

  def * = (id, name, price) <> (Item.tupled, Item.unapply)
}

object ItemObj {
  val createItemForm: Form[Item] = Form(
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
      "price" -> bigDecimal
    )(Item.apply)(Item.unapply)
  )
}
