package services

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class StartupTasks @Inject()(guestService: GuestService, kafkaProducerFactory: KafkaProducerFactory)
                            (implicit ec: ExecutionContext) {

  // Initial time after the application startup when this runs
  private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
  startDailyOverdueCheck()

  def startDailyOverdueCheck(): Unit = {
    // Schedule the task to run daily at the specified time
    scheduler.scheduleAtFixedRate(
      () => {
//        checkEventDayAlert()
      },
      0L,
      TimeUnit.DAYS.toSeconds(1), // Repeat every 24 hours
      TimeUnit.SECONDS
    )
  }

  // Method to send menu
//  private def checkEventDayAlert()(implicit ec: ExecutionContext): Seq[Unit] = {
//    // Retrieve current guests asynchronously
//    guestService.getCurrentGuests.flatMap { guests =>
//      // Use Future.sequence to send Kafka messages for each guest
//      Future.sequence(
//        guests.map { guest =>
//          kafkaProducerFactory.sendMenu(guest) // Assuming sendMenu returns Future[Unit]
//        }
//      )
//    }.recover {
//      case ex: Exception =>
//        // Log the exception
//        println(s"Failed to check overdue allocations: ${ex.getMessage}")
//    }
//  }
}