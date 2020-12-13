package waypoint

import general.Position

class WaypointSouth(
    value: Int,
    shipPosition: Position,
    waypointPosition: Position
): WaypointMovement(value, shipPosition, waypointPosition) {
    override fun move() {
        WaypointNorth(-value, shipPosition, waypointPosition).move()
    }
}