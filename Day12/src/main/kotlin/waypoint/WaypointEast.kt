package waypoint

import general.Position

class WaypointEast(
    value: Int,
    shipPosition: Position,
    waypointPosition: Position
): WaypointMovement(value, shipPosition, waypointPosition) {
    override fun move() {
        waypointPosition.east += value
    }
}