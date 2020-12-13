package ship

import general.Movement
import general.Position

class ShipRight(value: Int, shipPosition: Position) : Movement(value, shipPosition) {
    override fun move() {
        ShipLeft(-value, this.shipPosition).move()
    }
}