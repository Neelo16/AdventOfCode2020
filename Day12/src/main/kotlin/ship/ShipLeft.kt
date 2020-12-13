package ship

import general.Movement
import general.Position
import java.lang.Math.floorMod

class ShipLeft(value: Int, shipPosition: Position) : Movement(value, shipPosition) {
    override fun move() {
        shipPosition.rotation = floorMod(shipPosition.rotation + value, 360)
    }
}