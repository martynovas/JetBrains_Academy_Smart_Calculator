import java.math.BigInteger
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val a = BigInteger(scanner.nextLine())
    val b = BigInteger(scanner.nextLine())
    val c = BigInteger(scanner.nextLine())
    val d = BigInteger(scanner.nextLine())

    val result = (-a) * b + c - d
    println(result)
}