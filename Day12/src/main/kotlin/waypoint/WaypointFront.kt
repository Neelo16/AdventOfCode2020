package waypoint

import general.Movement
import general.Position

class WaypointFront(
    value: Int,
    shipPosition: Position,
    waypointPosition: Position
): WaypointMovement(value, shipPosition, waypointPosition) {
    override fun move() {
        shipPosition.north += value *  waypointPosition.north
        shipPosition.east += value * waypointPosition.east
    }
}