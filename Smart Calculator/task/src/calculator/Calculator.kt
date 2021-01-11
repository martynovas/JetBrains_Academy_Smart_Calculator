package calculator

import java.math.BigInteger

class Calculator {
    private var variables = mutableMapOf<String, BigInteger>()
    private var expression = listOf<Any>()
    private val stack = Stack<BigInteger>()

    fun addVariable(key: String, value: BigInteger) {
        variables[key] = value
    }

    fun addVariable(key: String, anotherVariableKey: String) {
        variables[key] = getVariable(anotherVariableKey)
    }

    fun getVariable(key: String): BigInteger {
        val value = variables.get(key.replace("-", "")) ?: throw IllegalArgumentException("Unknown variable")
        return value * if (key[0] == '-') - BigInteger.ONE else BigInteger.ONE
    }

    private fun normalizeExpression(line: String): String {
        var nLine = line
        while (nLine.contains(Regex("""(\+|-)(\+|-)""")))
            nLine = nLine.replace("-+", "-")
                    .replace("+-", "-")
                    .replace("--", "+")
                    .replace("++", "+")
        nLine = Regex("""(^|\()\+""").replace(nLine, """$1""")
        nLine = Regex("""(^|\()-(\d+|[a-zA-Z]+)""").replace(nLine, """($0)""")
        return nLine
    }

    fun validateExpression(line: String) {
        var vLine = "(" + line + ")"
        do {
            val operation = Regex("""\(([^()])+\)""").findAll(vLine).toList()
            if (operation.isEmpty())
                throw IllegalArgumentException("Invalid expression")

            for (i in operation) {
                if (i.value.matches(Regex("""\(\+?\-?(\d|[a-zA-Z])+((\+|\-|\*|\/)(\d|[a-zA-Z])+)*\)""")))
                    vLine = vLine.replace(i.value, "exp")
                else
                    throw IllegalArgumentException("Invalid expression")
            }
        } while (vLine != "exp")

    }

    fun setExpression(line: String) {
        val nLine = normalizeExpression(line)
        validateExpression(nLine)
        val infixExpression = Regex("""(\(-(\d+|[a-zA-Z]+)\))|\+|-|\*|/|\(|\)|\d+|[a-zA-Z]+""").findAll(nLine).map { it.value }.filterNotNull().toList()
        val postfixExpression = PostfixConverter.convert(infixExpression)
        expression = postfixExpression.map { OperationType.get(it) ?: it.toBigIntegerOrNull() ?: it as Any }
    }

    fun calculate(): BigInteger {
        for (element in expression) when (element) {
            is String -> stack.push(getVariable(element))
            is BigInteger -> stack.push(element)
            is OperationType -> stack.push(element.calc(b = stack.pop(), a = stack.pop()))
        }

        return stack.pop()
    }

}