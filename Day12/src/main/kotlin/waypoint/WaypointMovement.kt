package waypoint

import general.Movement
import general.Position

abstract class WaypointMovement(
    value: Int,
    shipPosition: Position,
    val waypointPosition: Position
) : Movement(value, shipPosition) {
    companion object {
        fun build(
            line: String,
            shipPosition: Position,
            waypointPosition: Position
        ) : WaypointMovement {
            val value = line.drop(1).toInt()
            val constructor = when (line.first()) {
                'F' -> ::WaypointFront
                'L' -> ::WaypointLeft
                'R' -> ::WaypointRight
                'N' -> ::WaypointNorth
                'S' -> ::WaypointSouth
                'E' -> ::WaypointEast
                'W' -> ::WaypointWest
                else -> throw IllegalArgumentException("${line.first()} is not a valid direction")
            }
            return constructor(value, shipPosition, waypointPosition)
        }
    }
}