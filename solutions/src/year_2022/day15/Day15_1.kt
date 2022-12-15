package year_2022.day15

import solve
import kotlin.math.abs

fun main() = solve { lines ->
    val sensors = parseInput(lines)
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

