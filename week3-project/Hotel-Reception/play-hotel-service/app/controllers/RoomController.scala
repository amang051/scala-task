package controllers

import javax.inject._
import play.api.mvc._
import services.RoomService

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import models.Room

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
}



