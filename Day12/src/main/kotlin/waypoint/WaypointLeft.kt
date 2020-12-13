package waypoint

import general.Position
import java.lang.Math.floorMod

class WaypointLeft(
    value: Int,
    shipPosition: Position,
    waypointPosition: Position
): WaypointMovement(value, shipPosition, waypointPosition) {
    override fun move() {
        val east = waypointPosition.east
        val north = waypointPosition.north
        val value = floorMod(this.value, 360) / 90
        val rotation = listOf(
            Pair(east, north),
            Pair(-north, east),
            Pair(-east, -north),
            Pair(north, -east)
        )[value]
        waypointPosition.east = rotation.first
        waypointPosition.north = rotation.second
    }
}