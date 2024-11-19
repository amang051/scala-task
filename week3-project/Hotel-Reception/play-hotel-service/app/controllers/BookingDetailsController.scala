package controllers

import models.BookingDetails
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import services.BookingDetailsService

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BookingDetailsController @Inject()(cc: ControllerComponents, bookingDetailsService: BookingDetailsService)
                               (implicit ec: ExecutionContext) extends AbstractController(cc) {

  // Register booking details
  def registerBookingDetails(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[BookingDetails] match {
      case JsSuccess(booking, _) =>
        bookingDetailsService.create(booking).map(id =>
          Created(Json.obj("id" -> id, "message" -> "CREATED")))
      case JsError(errors) =>
        Future.successful(BadRequest(Json.obj(
          "message" -> "Invalid Booking data",
          "errors" -> JsError.toJson(errors))))
    }
  }
}
