fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

fun isEmpty(seat: Char) = seat == 'L'
fun isOccupied(seat: Char) = seat == '#'
fun isFloor(seat: Char) = seat == '.'

fun nextState(
    state: Array<CharArray>,
    width: Int,
    height: Int,
    neighborFunction: (coordinate: Coordinate, state: Array<CharArray>) -> Collection<Coordinate>,
    neighborTolerance: Int
): Array<CharArray> {
    val next = state.map(CharArray::clone).toTypedArray()
    for ((line, seats) in state.withIndex())
        for ((column, seat) in seats.withIndex()) {
            val coordinate = Coordinate(line, column, width, height)
            if (isFloor(seat)) continue
            val neighbors = neighborFunction(coordinate, state).map { state[it.line][it.column] }
            if (isEmpty(seat) && neighbors.none(::isOccupied))
                next[line][column] = '#'
            else if (isOccupied(seat) && neighbors.count(::isOccupied) >= neighborTolerance)
                next[line][column] = 'L'
    }
    return next
}

data class Coordinate(val line: Int, val column: Int, val width: Int, val height: Int) {
    fun isValid() = line in 0 until height && column in 0 until width

    companion object {
        fun builder(width: Int, height: Int): (line: Int, column: Int) -> Coordinate {
            return { line, column -> Coordinate(line, column, width, height) }
        }
    }
}

@Suppress("UNUSED_PARAMETER")
fun adjacentSeats(coordinate: Coordinate, state: Array<CharArray>): Collection<Coordinate> {
    return lookaheadFunctions(coordinate).map { it(1) }.filter(Coordinate::isValid)
}

fun visibleSeats(coordinate: Coordinate, state: Array<CharArray>): Collection<Coordinate> {
    val lookaheadFunctions = lookaheadFunctions(coordinate)
    val neighbors = mutableListOf<Coordinate>()
    for (lookaheadFunction in lookaheadFunctions)
        for (i in 1 until maxOf(coordinate.width, coordinate.height)) {
            val newCoordinate = lookaheadFunction(i)
            if (!newCoordinate.isValid())
                break
            if (!isFloor(state[newCoordinate.line][newCoordinate.column])) {
                neighbors.add(newCoordinate)
                break
            }
        }
    return neighbors
}

fun lookaheadFunctions(coordinate: Coordinate): List<(Int) -> Coordinate> {
    val line = coordinate.line
    val column = coordinate.column
    val coordinateBuilder = Coordinate.builder(coordinate.width, coordinate.height)
    return listOf(
        { step -> coordinateBuilder(line +    0, column + step) },
        { step -> coordinateBuilder(line +    0, column - step) },
        { step -> coordinateBuilder(line + step, column +    0) },
        { step -> coordinateBuilder(line + step, column + step) },
        { step -> coordinateBuilder(line + step, column - step) },
        { step -> coordinateBuilder(line - step, column +    0) },
        { step -> coordinateBuilder(line - step, column + step) },
        { step -> coordinateBuilder(line - step, column - step) },
    )
}

fun simulate(
    initialState: Array<CharArray>,
    neighborFunction: (coordinate: Coordinate, state: Array<CharArray>) -> Collection<Coordinate>,
    neighborTolerance: Int
): Array<CharArray> {
    val width = initialState.first().size
    val height = initialState.size
    var previousState = initialState
    val previousStates = mutableSetOf<String>()
    while (previousStates.add(previousState.joinToString("", transform = CharArray::concatToString))) {
        previousState = nextState(
            previousState,
            width,
            height,
            neighborFunction,
            neighborTolerance
        )
    }
    return previousState
}

fun main() {
    val initialState = getResourceAsText("/input.txt").trim().lines().map(String::toCharArray).toTypedArray()
    println("Part 1: ${simulate(initialState, ::adjacentSeats, 4).map { it.count(::isOccupied) }.reduce(Int::plus)}")
    println("Part 2: ${simulate(initialState, ::visibleSeats, 5).map { it.count(::isOccupied) }.reduce(Int::plus)}")
}
