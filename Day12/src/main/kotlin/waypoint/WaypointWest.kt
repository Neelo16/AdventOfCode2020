package waypoint

import general.Position

class WaypointWest(
    value: Int,
    shipPosition: Position,
    waypointPosition: Position
): WaypointMovement(value, shipPosition, waypointPosition) {
    override fun move() {
        WaypointEast(-value, shipPosition, waypointPosition).move()
    }
}