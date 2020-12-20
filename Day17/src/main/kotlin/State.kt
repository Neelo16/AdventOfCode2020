open class State(
    val cubes: Map<Coordinate, Char> = mapOf()
) {

    protected open fun neighbors(coordinate: Coordinate): List<Coordinate> {
        val x = coordinate.x
        val y = coordinate.y
        val z = coordinate.z
        val neighbors = mutableListOf<Coordinate>()
        for (xStep in -1..1)
            for (yStep in -1..1)
                for (zStep in -1..1)
                    if (listOf(xStep, yStep, zStep) != listOf(0, 0, 0))
                        neighbors.add(Coordinate(x + xStep, y + yStep, z + zStep))
        return neighbors
    }

    open fun simulate(): State {
        val newPositions = cubes.toMutableMap()
        cubes.keys.forEach {
            val neighbors = neighbors(it)
            neighbors.forEach { neighbor ->
                newPositions[neighbor] = cubes.getOrDefault(neighbor, INACTIVE)
            }
        }
        for (pos in newPositions.keys) {
            val cube = cubes.getOrDefault(pos, INACTIVE)
            val neighbors = neighbors(pos).map{ cubes.getOrDefault(it, INACTIVE) }
            if (isActive(cube))
                newPositions[pos] = if (neighbors.filter(::isActive).count() in listOf(2, 3))
                    ACTIVE
                else
                    INACTIVE
            else if (isInactive(cube) && neighbors.filter(::isActive).count() == 3)
                newPositions[pos] = ACTIVE
        }
        return State(newPositions)
    }

    companion object {
        const val INACTIVE = '.'
        const val ACTIVE = '#'

        fun isActive(cube: Char) = cube == ACTIVE
        fun isInactive(cube: Char) = cube == INACTIVE
    }
}
