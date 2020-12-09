fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

fun getPreamble(numbers: LongArray, startIndex: Int, preambleSize: Int = 25): LongArray {
    val dropAmount = startIndex - preambleSize
    return numbers.drop(dropAmount).take(preambleSize).sorted().toLongArray()
}

fun hasSum(value: Long, preamble: LongArray): Boolean {
    for ((i, firstValue) in preamble.withIndex())
        for (secondValue in preamble.drop(i))
            if (firstValue + secondValue == value)
                return true
            else if (firstValue + secondValue > value)
                break
    return false
}

fun findContiguousSum(numbers: LongArray, goal: Long): LongArray {
    for (i in numbers.indices)
        for (j in i+1 until numbers.size) {
            val contiguousArray = numbers.slice(i..j)
            if (contiguousArray.sum() == goal) {
                return contiguousArray.toLongArray()
            }
        }
    return longArrayOf()
}

fun main() {
    val input = getResourceAsText("/input.txt").trim().lines().map(String::toLong).toLongArray()
    var invalidNumber: Long = 0
    for ((i, value) in input.drop(25).withIndex()) {
        val preamble = getPreamble(input, i + 25)
        if (!hasSum(value, preamble)) {
            invalidNumber = value
            break
        }
    }
    println("Part 1: $invalidNumber")
    val contiguousRange = findContiguousSum(input, invalidNumber).sorted()
    println("Part 2: ${contiguousRange.first() + contiguousRange.last()}")
}