import java.math.BigInteger
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val result = when (listOf(
            scanner.nextBigInteger(),
            scanner.nextBigInteger(),
            scanner.nextBigInteger()).groupBy { it }.size) {
        3 -> 0
        2 -> 2
        else -> 3
    }
    println(result)
}