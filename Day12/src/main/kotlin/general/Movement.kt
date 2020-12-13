package general

import ship.ShipEast
import ship.ShipFront
import ship.ShipLeft
import ship.ShipNorth
import ship.ShipRight
import ship.ShipSouth
import ship.ShipWest
import waypoint.WaypointEast
import waypoint.WaypointFront
import waypoint.WaypointLeft
import waypoint.WaypointNorth
import waypoint.WaypointRight
import waypoint.WaypointSouth
import waypoint.WaypointWest

abstract class Movement(
    val value: Int,
    val shipPosition: Position,
) {
    abstract fun move()

    companion object {
        fun build(line: String, position: Position): Movement {
            val value = line.drop(1).toInt()
                val constructor = when (line.first()) {
                    'F' -> ::ShipFront
                    'L' -> ::ShipLeft
                    'R' -> ::ShipRight
                    'N' -> ::ShipNorth
                    'S' -> ::ShipSouth
                    'E' -> ::ShipEast
                    'W' -> ::ShipWest
                    else -> throw IllegalArgumentException("${line.first()} is not a valid direction")
                }
                return constructor(value, position)
            }
        }
}