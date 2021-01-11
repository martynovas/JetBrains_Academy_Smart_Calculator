package calculator

object PostfixConverter {

    fun convert(expression: List<String>): List<String> {
        val stack = Stack<String>()

        var result = mutableListOf<String>()

        for (element in expression) {

            if (element.matches(Regex("""(\(-(\d+|[a-zA-Z]+)\))""")))
            //Add operands (numbers and variables witg minus in parenthesis) to the result (postfix notation) as they arrive.
                result.add(element.replace("(","").replace(")",""))
            else if (element.matches(Regex("""\d+|[a-zA-Z]+""")))
            //Add operands (numbers and variables) to the result (postfix notation) as they arrive.
                result.add(element)
            else {
                //If the stack is empty or contains a left parenthesis on top, push the incoming operator on the stack.
                //If the incoming operator has higher precedence than the top of the stack, push it on the stack.
                //If the incoming element is a left parenthesis, push it on the stack.
                if ((stack.isEmpty() || stack.top() == "(")
                        || (element in listOf("*", "/") && stack.top() in listOf("+", "-"))
                        || (element == "("))
                    stack.push(element)
                else
                //If the incoming operator has lower or equal precedence than or to the top of the stack,
                //pop the stack and add operators to the result until you see an operator that has a smaller precedence
                //or a left parenthesis on the top of the stack; then add the incoming operator to the stack.
                    if (element in listOf("+", "-", "*", "/") && !(element in listOf("*", "/") && stack.top() in listOf("+", "-"))) {
                        while (!stack.isEmpty() && stack.top() != "(" && !(element in listOf("*", "/") && stack.top() in listOf("+", "-")))
                            result.add(stack.pop())
                        stack.push(element)
                    } else
                    //If the incoming element is a right parenthesis, pop the stack and add operators to the result
                    //until you see a left parenthesis. Discard the pair of parentheses.
                        if (element == ")") {
                            while (!stack.isEmpty() && stack.top() != "(")
                                stack.pop().takeIf { it in "+-*/" }.let { result.add(it!!) }
                            stack.pop()
                        }
            }
        }

        //At the end of the expression, pop the stack and add all operators to the result.
        while (!stack.isEmpty())
            result.add(stack.pop())

        return result
    }
}

fun main() {
    val nLine = "2*(-3)"
    val infixExpression = Regex("""(\(-(\d+|[a-zA-Z]+)\))|\+|-|\*|/|\(|\)|\d+|[a-zA-Z]+""").findAll(nLine).map { it.value }.filterNotNull().toList()
    println(infixExpression)
    println(PostfixConverter.convert(infixExpression).joinToString())
}