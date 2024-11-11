trait GetStarted {
    def prepare(): Unit = {
        println("Inside GetStarted prepare")
    }

    def prepare1(): Unit = {
        println("Inside GetStarted prepare1")
    }
}

trait Cook extends GetStarted {
    override def prepare(): Unit = {
        super.prepare()
        println("Inside Cook prepare")
    }

    override def prepare1(): Unit = { // we cannot make super.prepare1() call because it's implementation is not defined
        println("Inside Cook prepare1")
    }
}

trait Seasoning {
    def applySeasoning(): Unit = {
        println("Inside Seasoning applySeasoning")
    }
}

class Food extends Cook with Seasoning {
    def prepareFood(): Unit = {
        prepare()
        applySeasoning()
    }

    def prepareFood1(): Unit = {
        prepare1()
        applySeasoning()
    }
}

object Main {
    @main def start(): Unit = {
        val food = new Food
        food.prepareFood()
        println()
        food.prepareFood1()
    }
}

// there is no complimentary need to use abstract override.
// We can use it if we want to specify some implementation and later on we need the next function which extends to define the final implementation