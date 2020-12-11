fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

fun main() {
    val takenSeats = Array(1024) { false }
    getResourceAsText("/input.txt").trim().lines().forEach { line ->
        val id = line.replace(Regex("[FL]"), "0").
            replace(Regex("[BR]"), "1")
            .toInt(2)
        takenSeats[id] = true
    }
    println("Part 1: ${takenSeats.indexOfLast{ it }}")
    for ((id, seat) in takenSeats.withIndex())
        if (!seat && takenSeats.getOrNull(id - 1) == true && takenSeats.getOrNull(id + 1) == true)
            println("Part 2: $id")
}