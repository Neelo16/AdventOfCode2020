fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

data class XY(val x: Int, val y: Int)

fun main() {
    val input = getResourceAsText("/input.txt").trim().lines()
    val edge = input.first().length
    val bottom = input.size
    val slopes = listOf(XY(1, 1), XY(3, 1), XY(5, 1), XY(7, 1), XY(1, 2))
    var total: Long = 1
    for (slope in slopes) {
        var trees = 0
        for ((it, y) in (0 until bottom step slope.y).withIndex()) {
            val line = input[y]
            if (line[it * slope.x % edge] == '#')
                trees++
        }
        if (slope == XY(3, 1))
            println("Part 1: $trees")
        total *= trees
    }
    println("Part 2: $total")
}