package waypoint

import general.Movement
import general.Position

class WaypointNorth(
    value: Int,
    shipPosition: Position,
    waypointPosition: Position
): WaypointMovement(value, shipPosition, waypointPosition) {
    override fun move() {
        waypointPosition.north += value
    }
}