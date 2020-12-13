package ship

import general.Movement
import general.Position

class ShipWest(value: Int, shipPosition: Position) : Movement(value, shipPosition) {
    override fun move() {
        ShipEast(-value, shipPosition)
    }
}