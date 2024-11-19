package services

import repositories.MenuRepository

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import javax.inject.Inject
import scala.concurrent.ExecutionContext

class StartupTasks @Inject()(guestService: GuestService, menuRepository: MenuRepository,
                             kafkaProducerFactory: KafkaProducerFactory)(implicit ec: ExecutionContext) {

  // Initial time after the application startup when this runs
  private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
  startDailyOverdueCheck()

  def startDailyOverdueCheck(): Unit = {
    // Schedule the task to run daily at the specified time
    scheduler.scheduleAtFixedRate(
      () => {
        checkEventDayAlert()
      },
      0L,
      TimeUnit.MINUTES.toSeconds(1), // Repeat every 1 minute
      TimeUnit.SECONDS
    )
  }

  // Method to send menu
  private def checkEventDayAlert(): Unit = {
    // Retrieve current guests
    val menuItems = menuRepository.listMenu;
    guestService.getCurrentGuests.map { guests =>
      guests.foreach { guest =>
        kafkaProducerFactory.sendMenu(guest, menuItems)
      }
    }.recover {
      case ex: Exception =>
        println(s"Failed to check overdue allocations: ${ex.getMessage}")
    }
  }
}