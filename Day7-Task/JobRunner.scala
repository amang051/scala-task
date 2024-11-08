import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class JobRunner(jobName: String, timeInSeconds: Int) {
    def runJob(block: => Unit): Unit = {
        println(s"Job '$jobName' will start after $timeInSeconds seconds...")
        Future {
            Thread.sleep(timeInSeconds * 1000)
            block
        }
    }
}

object JobRunner {
    def apply(jobName: String, timeInSeconds: Int)(block: => Unit): Unit = {
        val jobRunner = new JobRunner(jobName, timeInSeconds)
        jobRunner.runJob(block)
    }
}

object Main {
    @main def start(): Unit = {
        JobRunner("Sample Job", 5) {
            println("Job is running after the specified delay.")
        }
        Thread.sleep(6000) // Ensure the program doesn't exit before the job is executed
    }
}