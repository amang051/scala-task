package controllers

import models.Guest
import play.api.libs.Files.TemporaryFile

import javax.inject._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import services.GuestService

import java.nio.file.Files

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

  // create guest with ID proof
  def checkInWithFile: Action[MultipartFormData[TemporaryFile]] = Action.async(parse.multipartFormData) { request =>
    request.body.dataParts.get("guestDetails") match {
      case Some(guestDetailsSeq) if guestDetailsSeq.nonEmpty =>
        val jsonString = guestDetailsSeq.head
        Json.parse(jsonString).validate[Guest] match {
          case JsSuccess(guestCheckInRequest, _) =>
            request.body.file("idProof").map { file =>
              val idProofBytes = Files.readAllBytes(file.ref.path)

              val updatedGuestRequest = guestCheckInRequest.copy(idProof = Some(idProofBytes))

              guestService.create(updatedGuestRequest).map { guest =>
                Ok(Json.toJson(guest))
              }
            }.getOrElse {
              Future.successful(BadRequest("Missing file"))
            }

          case JsError(errors) =>
            Future.successful(BadRequest("Invalid JSON format"))
        }

      case _ =>
        Future.successful(BadRequest("Missing or empty guestDetails in request"))
    }
  }

  // Get current guests
  def getCurrentGuests: Action[AnyContent] = Action.async {
    guestService.getCurrentGuests.map(created =>
      Ok(Json.toJson(created)))
  }
}
