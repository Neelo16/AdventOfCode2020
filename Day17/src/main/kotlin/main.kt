fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

fun main() {
    val input = getResourceAsText("/input.txt").trim().lines()
    val initialStateMap = mutableMapOf<Coordinate, Char>()
    for ((y, line) in input.withIndex())
        for ((x, char) in line.withIndex())
            initialStateMap[Coordinate(x, y, 0)] = char
    val finalState = (0 until 6).fold(State(initialStateMap)) { state, _ ->
        state.simulate()
    }
    println("Part 1: ${finalState.cubes.values.filter(State::isActive).count()}")
    val final4DState = (0 until 6).fold(FourDState(initialStateMap)) { state, _ ->
        state.simulate() as FourDState
    }
    println("Part 2: ${final4DState.cubes.values.filter(State::isActive).count()}")
}
