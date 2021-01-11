package calculator

import java.math.BigInteger

enum class OperationType(val operation: (a: BigInteger, b: BigInteger) -> BigInteger) {
    PLUS({a: BigInteger, b: BigInteger -> a + b}),
    MINUS({a: BigInteger, b: BigInteger -> a - b}),
    MULTIPLY({a: BigInteger, b: BigInteger -> a * b}),
    DIVIDE({a: BigInteger, b: BigInteger -> a / b});

    companion object {
        fun get(c: String) = when (c) {
            "+" -> PLUS
            "-" -> MINUS
            "*" -> MULTIPLY
            "/" -> DIVIDE
            else -> null
        }
    }

    fun calc(a: BigInteger, b: BigInteger) = operation(a, b)
}