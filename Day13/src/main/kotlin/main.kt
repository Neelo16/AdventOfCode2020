fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

data class Bus(val id: Int, val schedule: Sequence<Int> = generateSequence(id) { it + id })

fun main() {
    val input = getResourceAsText("/input.txt").trim().also(::println).lines()
    val timestamp = input.first().toInt()
    val buses = input.drop(1).first().split(",").filter{ it != "x" }.map(String::toInt)
    val schedules = buses.map(::Bus).map{ bus -> Bus(bus.id, bus.schedule.dropWhile { it < timestamp }) }
    val minSchedule = schedules.minByOrNull { it.schedule.first() }!!
    println("Part 1: ${minSchedule.id * (minSchedule.schedule.first() - timestamp)}")
    val equations = input.drop(1).first().split(",").mapIndexed { index, s ->
        if (s == "x")
            null
        else
            Equation(s.toLong(), index.toLong())
    }.filterNotNull()
    val t = sieve(equations.sortedByDescending { it.mod })
    println("Part 2: $t")
}



