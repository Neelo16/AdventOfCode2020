import java.math.BigInteger

fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

fun factorial(n: Int): BigInteger = if (n <= 1)
        BigInteger.ONE
    else
        factorial(n-1) * n.toBigInteger()

fun main() {
    val input =  getResourceAsText("/input.txt").trim().lines().map(String::toInt).sorted()
    println(input.size)
    println(factorial(input.size))
    var previous = 0
    val joltageDifferenceCount = Array(3) { 0 }
    for (adapter in input) {
        val joltageDifference = adapter - previous
        joltageDifferenceCount[joltageDifference - 1]++
        previous = adapter
    }
    joltageDifferenceCount[2]++ // Device's built-in adapter
    println("Part 1: ${joltageDifferenceCount[0] * joltageDifferenceCount[2]}")
    val arrangements = Array(input.last() + 1) { 0L }
    arrangements[0] = 1
    for (joltage in input) {
        var possibilities = arrangements[joltage - 1]
        if (joltage > 1)
            possibilities += arrangements[joltage - 2]
        if (joltage > 2)
            possibilities += arrangements[joltage - 3]
        arrangements[joltage] = possibilities
    }
    println("Part 2: ${arrangements.last()}")
}