import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random
import scala.concurrent.duration._
import scala.util.{Success, Failure}

object RandomNumberThreadExecutor {
    def apply(): Future[String] = {
        val promise = Promise[String]()
        val future = promise.future

        @volatile var stopThreads = false

        def generateRandomNumbers(threadName: String): Unit = {
            while (!stopThreads) {
                val randomNumber = Random.nextInt(2000)
                println(s"$threadName generated: $randomNumber")
        
                if (randomNumber == 1567) {
                    promise.success(s"$threadName has generated 1567")
                    stopThreads = true
                }

                Thread.sleep(100)
            }
        }

        val firstThread = new Thread(() => generateRandomNumbers("firstThread"))
        val secondThread = new Thread(() => generateRandomNumbers("secondThread"))
        val thirdThread = new Thread(() => generateRandomNumbers("thirdThread"))

        firstThread.start()
        secondThread.start()
        thirdThread.start()

        future.onComplete {
            case Success(message) =>
                println(s"Success: $message")
            case Failure(exception) =>
                println(s"Failed: ${exception.getMessage}")
        }

        future
    }

    def main(args: Array[String]): Unit = {
        val resultFuture = RandomNumberThreadExecutor()

        Await.result(resultFuture, Duration.Inf)
    }
}
