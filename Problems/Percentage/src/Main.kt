import java.math.BigInteger

fun main() {
    val a = BigInteger(readLine())
    val b = BigInteger(readLine())
    val sumAB = (a + b)
    val hundred = BigInteger("100")
    println("${a * hundred / sumAB}% ${b * hundred / sumAB}%")
}