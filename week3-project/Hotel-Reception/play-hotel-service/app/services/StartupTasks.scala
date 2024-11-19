package services

import repositories.MenuRepository

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import javax.inject.Inject
import scala.concurrent.ExecutionContext

class StartupTasks @Inject()(guestService: GuestService, menuRepository: MenuRepository,
                             kafkaProducerFactory: KafkaProducerFactory)(implicit ec: ExecutionContext) {

  private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
  startDailyNotification()

  private def startDailyNotification(): Unit = {
    // Schedule the task to run daily at the specified time
    scheduler.scheduleAtFixedRate(
      () => {
        sendDailyMenu()
      },
      0L,
      TimeUnit.MINUTES.toSeconds(5), // Repeat every 5 minute
      TimeUnit.SECONDS
    )
  }

  // Method to send menu
  private def sendDailyMenu(): Unit = {
    // Retrieve current guests
    val menuItems = menuRepository.listMenu;
    guestService.getCurrentGuests.map { guests =>
      guests.foreach { guest =>
        kafkaProducerFactory.sendMenu(guest, menuItems)
      }
    }.recover {
      case ex: Exception =>
        println(s"Failed to send notification: ${ex.getMessage}")
    }
  }
}