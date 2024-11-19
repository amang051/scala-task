package models

import play.api.libs.json.{Json, OFormat}

case class Menu(id: Option[Long] = None, foodName: String, price: Double)

object Menu {
  implicit val format: OFormat[Menu] = Json.format[Menu]
}
