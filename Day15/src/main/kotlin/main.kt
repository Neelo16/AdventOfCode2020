fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

fun main() {
    val input = getResourceAsText("/input.txt").trim().split(",").map(String::toInt)
    val spokenValues = mutableMapOf<Int, Int>().also { map -> input.dropLast(1).forEachIndexed{ index, value -> map[value] = index } }
    var lastNumber = input.last()
    val spoken = mutableListOf<Int>().also { it.addAll(input.dropLast(1)) }
    spokenValues.values.size.until(30000000).forEach { turn ->
        val nextNumber = spokenValues[lastNumber]?.let { turn - it } ?: 0
        spokenValues[lastNumber] = turn
        spoken.add(lastNumber)
        lastNumber = nextNumber
    }
    println("Part 1: ${spoken[2019]}")
    println("Part 2: ${spoken.last()}")
}
