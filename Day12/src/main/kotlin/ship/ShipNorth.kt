package ship

import general.Movement
import general.Position

class ShipNorth(value: Int, shipPosition: Position) : Movement(value, shipPosition) {
    override fun move() {
        shipPosition.north += value
    }
}