package services

import models.Room
import repositories.RoomRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RoomService @Inject() (roomRepository: RoomRepository)(implicit ex: ExecutionContext) {
  def create(room: Room): Future[Room] = {
    roomRepository.create(room)
  }
}
