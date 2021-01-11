import java.math.BigInteger

fun main() {
    val countExabyte = BigInteger(readLine()!!)
    println(countExabyte * BigInteger("9223372036854775808"))
}