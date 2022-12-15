package year_2022.day15

import solve
import year_2022.common.Position
import kotlin.math.abs

fun main() = solve { lines ->

    val sensors = lines.map {
        it.drop(10).split(": closest beacon is at ").run {
            val sensorToBeaconPair = Pair(this[0].parseToPosition(), this[1].parseToPosition())
            Sensor(sensorToBeaconPair.first, Beacon(sensorToBeaconPair.second))
        }
    }
    val rowValue = 2000000

    val pointThatCannotHaveABeacon = mutableSetOf<IntRange>()

    sensors.forEach { sensor ->
        val manhattanDistance = sensor.getManhattanDistance()
        val y = abs(sensor.position.y - rowValue)
        val startValue = sensor.position.x - (manhattanDistance - y)
        val endValue = sensor.position.x + (manhattanDistance - y)
        pointThatCannotHaveABeacon.add(startValue..endValue)
    }
    val result = mutableSetOf<Int>()
    pointThatCannotHaveABeacon.forEach { result.addAll(it.toSet()) }
    result.count() - 1
}

private fun Sensor.getManhattanDistance(): Int {
    return abs(this.position.x - this.closesBeacon.position.x) + abs(this.position.y - this.closesBeacon.position.y)
}

private fun String.parseToPosition(): Position {
    val xYValue = this.split(", ")
    val x = xYValue[0].drop(2).toInt()
    val y = xYValue[1].drop(2).toInt()
    return Position(x, y)
}

