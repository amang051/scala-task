package controllers

import javax.inject._
import play.api.mvc._
import services.RoomService

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json, OFormat}
import models.Room

import java.time.LocalDate

@Singleton
class RoomController @Inject()(cc: ControllerComponents, roomService: RoomService)
                               (implicit ec: ExecutionContext) extends AbstractController(cc) {

  // Create room
  def create(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Room] match {
      case JsSuccess(room, _) =>
        roomService.create(room).map(id =>
          Created(Json.obj("id" -> id, "message" -> "CREATED")))
      case JsError(errors) =>
        Future.successful(BadRequest(Json.obj(
          "message" -> "Invalid Guest data",
          "errors" -> JsError.toJson(errors))))
    }
  }

  case class RoomAvailabilityRequest(category: String, checkInDate: LocalDate, checkOutDate: LocalDate)

  object RoomAvailabilityRequest {
    implicit val format: OFormat[RoomAvailabilityRequest] = Json.format[RoomAvailabilityRequest]
  }

  // Get available rooms based on category and dates
  def getAvailableRoomsByCategory: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[RoomAvailabilityRequest].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> "Invalid data", "errors" -> JsError.toJson(errors))))
      },
      roomAvailabilityRequest => {
        roomService.getAvailableRooms(roomAvailabilityRequest.category, roomAvailabilityRequest.checkInDate,
          roomAvailabilityRequest.checkOutDate).map { availableRooms =>
            Ok(Json.toJson(availableRooms))
        }.recover {
          case ex: Exception =>
            InternalServerError(Json.obj("message" -> "Error fetching available rooms", "error" -> ex.getMessage))
        }
      }
    )
  }
}



