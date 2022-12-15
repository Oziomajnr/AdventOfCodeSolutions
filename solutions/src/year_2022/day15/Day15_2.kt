package year_2022.day15

import solve
import year_2022.common.Position
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() = solve { lines ->

    val sensors = lines.map {
        it.drop(10).split(": closest beacon is at ").run {
            val sensorToBeaconPair = Pair(this[0].parseToPosition(), this[1].parseToPosition())
            Sensor(sensorToBeaconPair.first, Beacon(sensorToBeaconPair.second))
        }
    }
    val maxValue = 4000000
    (0..maxValue).toList().forEach { rowValue ->

        val pointThatCannotHaveABeacon = mutableSetOf<IntRange>()

        sensors.forEach { sensor ->
            val manhattanDistance = sensor.getManhattanDistance()
            val y = abs(sensor.position.y - rowValue)
            val startValue = max(0, sensor.position.x - (manhattanDistance - y))
            val endValue = max(0, min(maxValue, sensor.position.x + (manhattanDistance - y)))
            pointThatCannotHaveABeacon.add(startValue..endValue)
        }

        val numberOfMissingPoints = pointThatCannotHaveABeacon.sortedWith { a, b ->
            if (a.first.compareTo(b.first) != 0) {
                a.last.compareTo(b.last)
            } else {
                a.first.compareTo(b.first)
            }
        }.windowed(2) {
            val missingPoints = max(0, it[1].first - it[0].last)
            if (missingPoints == 2) {
                it[0].last + 1
            } else {
                -1
            }
        }.filter { it != -1 }


        if (numberOfMissingPoints.filter { misingPoint ->
                pointThatCannotHaveABeacon.firstOrNull {
                    it.first <= misingPoint && it.last >= misingPoint
                } == null
            }.size == 1) {
            val resultPoints = mutableSetOf<Int>()
            pointThatCannotHaveABeacon.forEach {
                resultPoints.addAll(it)
            }
            val column = (0..maxValue).toSet() - resultPoints.toSet()
            println(column.single().toLong() * 4000000L + rowValue.toLong())
        }
    }
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

data class Sensor(val position: Position, val closesBeacon: Beacon)

data class Beacon(val position: Position)
