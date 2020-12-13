package ship

import general.Movement
import general.Position

class ShipFront(value: Int, shipPosition: Position): Movement(value, shipPosition) {
    override fun move() {
        when (shipPosition.rotation) {
            0 -> shipPosition.east += value
            90 -> shipPosition.north += value
            180 -> shipPosition.east -= value
            270 -> shipPosition.north -= value
            else -> throw RuntimeException("Can't handle rotation value ${shipPosition.rotation}")
        }
    }
}