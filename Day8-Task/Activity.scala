trait Task {
    def doTask(): Unit = {
        println("Task doTask")
    }
}

trait Cook extends Task {
    override def doTask(): Unit = {
        super.doTask()
        println("Cook doTask")
    }
}

trait Garnish extends Cook {
    override def doTask(): Unit = {
        super.doTask()
        println("Garnish doTask")
    }
}

trait Pack extends Garnish {
    override def doTask(): Unit = {
        super.doTask()
        println("Pack doTask")
    }
}

class Activity extends Task {
    def doActivity(): Unit = {
        doTask()
        println("Activity doActivity")
    }
}

object Main {
    @main def start(): Unit = {
        val obj1: Task = new Activity with Cook with Garnish with Pack
        val obj2: Task = new Activity with Garnish with Pack with Cook
        val obj3: Task = new Activity with Pack with Cook with Garnish

        obj1.doTask()
        println()
        obj2.doTask()
        println()
        obj3.doTask()
    }
}