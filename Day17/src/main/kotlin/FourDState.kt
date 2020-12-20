class FourDState(
    cubes: Map<Coordinate, Char> = mapOf()
): State(cubes) {

    override fun neighbors(coordinate: Coordinate): List<Coordinate> {
        val x = coordinate.x
        val y = coordinate.y
        val z = coordinate.z
        val w = coordinate.w
        val neighbors = mutableListOf<Coordinate>()
        for (xStep in -1..1)
            for (yStep in -1..1)
                for (zStep in -1..1)
                    for (wStep in -1..1)
                    if (listOf(xStep, yStep, zStep, wStep) != listOf(0, 0, 0, 0))
                        neighbors.add(Coordinate(x + xStep, y + yStep, z + zStep, w + wStep))
        return neighbors
    }

    override fun simulate(): State = FourDState(super.simulate().cubes)
}