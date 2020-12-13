import general.Movement
import general.Position
import waypoint.WaypointMovement
import kotlin.math.absoluteValue

fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

fun main() {
    val input = getResourceAsText("/input.txt").trim().lines()
    val position = Position()
    val positionWithWaypoint = Position()
    val waypointPosition = Position(10, 1)
    input.map{ Movement.build(it, position) }.forEach(Movement::move)
    input.map{ WaypointMovement.build(it, positionWithWaypoint, waypointPosition) }.forEach(Movement::move)
    println("Part 1: ${position.east.absoluteValue + position.north.absoluteValue}")
    println("Part 2: ${positionWithWaypoint.east.absoluteValue + positionWithWaypoint.north.absoluteValue}")
}