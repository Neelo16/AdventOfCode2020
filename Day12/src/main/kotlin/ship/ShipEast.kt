package ship

import general.Movement
import general.Position

class ShipEast(value: Int, shipPosition: Position) : Movement(value, shipPosition) {
    override fun move() {
        shipPosition.east += value
    }
}