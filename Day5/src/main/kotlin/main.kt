fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

data class Region(val lo: Int, val hi: Int)
data class Seat(val row: Int, val column: Int) {
    val id = row*8 + column
}

fun partition(region: Region, direction: Char): Region {
    val half: Int = (region.hi - region.lo + 1) / 2
    return when (direction) {
        in "FL" -> Region(region.lo, region.hi - half)
        in "BR" -> Region(region.lo + half, region.hi)
        else -> throw IllegalArgumentException("$direction is not a valid direction")
    }
}

fun main() {
    val takenSeats = Array(1024) { false }
    getResourceAsText("/input.txt").trim().lines().forEach { line ->
        var rowRegion = Region(0, 127)
        line.subSequence(0, 7).forEach { rowRegion = partition(rowRegion, it) }
        var columnRegion = Region(0, 7)
        line.subSequence(7, 10).forEach { columnRegion = partition(columnRegion, it) }
        val row = if (line[6] == 'F') rowRegion.lo else rowRegion.hi
        val column = if (line.last() == 'L') columnRegion.lo else columnRegion.hi
        takenSeats[row*8 + column] = true
    }
    println("Part 1: ${takenSeats.indexOfLast{ it }}")
    for ((id, seat) in takenSeats.withIndex())
        if (!seat && takenSeats.getOrNull(id - 1) == true && takenSeats.getOrNull(id + 1) == true)
            println("Part 2: $id")
}