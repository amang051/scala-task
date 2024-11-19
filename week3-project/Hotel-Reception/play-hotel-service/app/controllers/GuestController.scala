package controllers

import models.Guest

import javax.inject._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import services.GuestService

@Singleton
class GuestController @Inject()(cc: ControllerComponents, guestService: GuestService)
                               (implicit ec: ExecutionContext) extends AbstractController(cc) {

  // Create a guest
  def create(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Guest] match {
      case JsSuccess(guest, _) =>
        guestService.create(guest).map(id =>
          Created(Json.obj("id" -> id, "message" -> "CREATED")))
      case JsError(errors) =>
        Future.successful(BadRequest(Json.obj(
          "message" -> "Invalid Guest data",
          "errors" -> JsError.toJson(errors))))
    }
  }

  // Get current guests
  def getCurrentGuests: Action[AnyContent] = Action.async {
    guestService.getCurrentGuests.map(created =>
      Ok(Json.toJson(created)))
  }
}
