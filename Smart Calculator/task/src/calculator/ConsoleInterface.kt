package calculator

import java.util.*

object ConsoleInterface {
    private val calculator = Calculator()
    private val scanner = Scanner(System.`in`)
    var isWorking: Boolean = false

    private const val COMMAND = """/[a-zA-Z]+"""
    private const val VARIABLE = """[a-zA-Z]+=(([+-]?\d+)|[a-zA-Z]+)"""
    private const val EXPRESSION = """(\+|\-|\*|\/|\d|\(|\)|[a-zA-Z])+"""
    private const val START_EXPRESSION = """(\+|\-|\d|\(|[a-zA-Z])+.*"""

    private fun commandProcessing(line: String) {
        when (line) {
            "/help" -> {
                println("The program calculates anything")
            }
            "/exit" -> {
                println("Bye!")
                isWorking = false
            }
            else -> println("Unknown command")
        }
    }

    private fun variableProcessing(line: String) {
        val key = Regex("""[a-zA-Z]+=""").find(line)!!.value.replace("=", "")
        val valueNumber = Regex("""=[+-]?\d+""").find(line)?.value?.replace("=", "")?.toBigInteger()
        val valueOtherVariable = Regex("""=[a-zA-Z]+""").find(line)?.value?.replace("=", "")
        if (valueNumber != null)
            calculator.addVariable(key, valueNumber)
        else
            try {
                calculator.addVariable(key, valueOtherVariable!!)
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
    }

    private fun expressionProcessing(line: String) {
        try {
            calculator.setExpression(line)
            println(calculator.calculate())
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }

    fun run() {
        isWorking = true
        while (isWorking) {
            val line = scanner.nextLine().replace(" ", "")
            if (line == "")
                continue
            else if (line.matches(Regex(COMMAND)))
                commandProcessing(line)
            else if (line.matches(Regex(VARIABLE)))
                variableProcessing(line)
            else expressionProcessing(line)
        }
    }
}
