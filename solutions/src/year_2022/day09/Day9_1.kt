package year_2022.day09

import solve
import kotlin.math.abs

fun main() = solve { lines ->

    getVisitedPoints(parseInput(lines), buildList {
        repeat((2)) {
            add(Position(500, 500))
        }
    })
}

fun getVisitedPoints(headMovement: List<Movement>, knotPositions: List<Position>): Int {
    val grid = MutableList(1000) {
        MutableList(1000) { false }
    }

    grid[knotPositions.first().y][knotPositions.first().x] = true

    headMovement.forEach {
        when (it.direction) {
            Direction.Vertical -> {
                (1..abs(it.value)).forEach { motionValue ->
                    knotPositions.first().x += (it.value / abs(it.value))
                    simulateMovement(knotPositions)
                    grid[knotPositions.last().y][knotPositions.last().x] = true
                }
            }

            Direction.Horizontal -> {
                (1..abs(it.value)).forEach { motionValue ->
                    knotPositions.first().y += (it.value / abs(it.value))
                    simulateMovement(knotPositions)
                    grid[knotPositions.last().y][knotPositions.last().x] = true
                }
            }
        }
    }
    return grid.sumOf {
        it.count { it }
    }
}

private fun simulateMovement(knotPositions: List<Position>) {
    knotPositions.forEachIndexed { index, value ->
        if (index > 0) {
            val newPosition = getTailPositionFromHead(knotPositions[index - 1], value)
            value.x = newPosition.x
            value.y = newPosition.y
        }
    }
}

fun parseInput(lines: List<String>) = lines.map {
    it.split(" ").run {
        when (this[0]) {
            "R" -> Movement(Direction.Horizontal, this[1].toInt())
            "L" -> Movement(Direction.Horizontal, -this[1].toInt())
            "U" -> Movement(Direction.Vertical, this[1].toInt())
            "D" -> Movement(Direction.Vertical, -this[1].toInt())
            else -> {
                error("Invalid direction")
            }
        }
    }
}


fun getTailPositionFromHead(hPosition: Position, tPosition: Position): Position {
    var finalX = tPosition.x
    var finalY = tPosition.y
    if (abs(hPosition.x - tPosition.x) > 1 && abs(hPosition.y - tPosition.y) >= 1 || (abs(hPosition.y - tPosition.y) > 1 && abs(
            hPosition.x - tPosition.x
        ) >= 1)
    ) {
        if (hPosition.x > tPosition.x) {
            finalX++
        } else {
            finalX--
        }
        if (hPosition.y > tPosition.y) {
            finalY++
        } else {
            finalY--
        }
    } else if (abs(hPosition.x - tPosition.x) > 1) {
        if (hPosition.x > tPosition.x) {
            finalX++
        } else {
            finalX--
        }
    } else if (abs(hPosition.y - tPosition.y) > 1) {
        if (hPosition.y > tPosition.y) {
            finalY++
        } else {
            finalY--
        }
    }

    return Position(finalX, finalY)
}

enum class Direction {
    Vertical, Horizontal
}

data class Movement(val direction: Direction, val value: Int)
data class Position(var x: Int, var y: Int)
