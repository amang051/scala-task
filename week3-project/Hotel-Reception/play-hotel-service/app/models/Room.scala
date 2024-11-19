package models

import play.api.libs.json.{Json, OFormat}

case class Room(id: Option[Long] = None, floor: Int, category: String, price: Double)

object Room {
  implicit val format: OFormat[Room] = Json.format[Room]
}
