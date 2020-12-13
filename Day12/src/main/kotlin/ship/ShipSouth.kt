package ship

import general.Movement
import general.Position

class ShipSouth(value: Int, shipPosition: Position) : Movement(value, shipPosition) {
    override fun move() {
        ShipNorth(-value, shipPosition).move()
    }
}