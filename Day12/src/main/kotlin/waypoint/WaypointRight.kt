package waypoint

import general.Position

class WaypointRight(
    value: Int,
    shipPosition: Position,
    waypointPosition: Position
): WaypointMovement(value, shipPosition, waypointPosition) {
    override fun move() {
        WaypointLeft(-value, shipPosition, waypointPosition).move()
    }
}