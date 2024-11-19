package models

import play.api.libs.json.{Json, OFormat}

case class Guest(id: Option[Long] = None, name: String, age: Int, email: String, address: String,
                 idProof: Option[Array[Byte]] = None  /*For storing the ID proof as binary data*/)

object Guest {
  implicit val format: OFormat[Guest] = Json.format[Guest]
}
