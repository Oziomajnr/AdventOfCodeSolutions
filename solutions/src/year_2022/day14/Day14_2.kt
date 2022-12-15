package year_2022.day14

import solve
import year_2022.common.Position
import java.util.*

fun main() = solve { lines ->
    val input = getPositions(lines)
    val filledPoints = mutableSetOf<Position>()
    val sandPoints = mutableSetOf<Position>()
    val startPosition = Position(0, 500)
    input.forEach {
        it.windowed(2).forEach { positions ->
            val firstPosition = positions[0]
            val secondPosition = positions[1]
            if (firstPosition.x == secondPosition.x) {
                if (firstPosition.y > secondPosition.y) {
                    for (y in secondPosition.y..firstPosition.y) {
                        filledPoints.add(Position(firstPosition.x, y))
                    }
                } else {
                    for (y in firstPosition.y..secondPosition.y) {
                        filledPoints.add(Position(firstPosition.x, y))
                    }
                }
            } else if (firstPosition.y == secondPosition.y) {
                if (firstPosition.x > secondPosition.x) {
                    for (x in secondPosition.x..firstPosition.x) {
                        filledPoints.add(Position(x, firstPosition.y))
                    }
                } else {
                    for (x in firstPosition.x..secondPosition.x) {
                        filledPoints.add(Position(x, firstPosition.y))
                    }
                }
            }
        }
    }

    val maxXIndex = input.flatten().maxOf { it.x } + 2
    simulateSandFalling(filledPoints, startPosition, maxXIndex, sandPoints)
    sandPoints.size
}

private fun simulateSandFalling(
    filledPoints: MutableSet<Position>, startPosition: Position, maxXIndex: Int, sandPoints: MutableSet<Position>
) {
    val stack = Stack<Position>()
    stack.push(startPosition)

    while (stack.isNotEmpty()) {
        if (canMoveToPosition(filledPoints, Position(stack.peek().x + 1, stack.peek().y), maxXIndex)) {
            stack.push(Position(stack.peek().x + 1, stack.peek().y))
        } else if (canMoveToPosition(filledPoints, Position(stack.peek().x + 1, stack.peek().y - 1), maxXIndex)) {
            stack.push(Position(stack.peek().x + 1, stack.peek().y - 1))
        } else if (canMoveToPosition(filledPoints, Position(stack.peek().x + 1, stack.peek().y + 1), maxXIndex)) {
            stack.push(Position(stack.peek().x + 1, stack.peek().y + 1))
        } else {
            val position = stack.pop()
            filledPoints.add(position)
            sandPoints.add(position)
        }
    }
}

fun getPositions(lines: List<String>): List<List<Position>> {
    return lines.map {
        it.split(" -> ").map {
            it.split(",").run {
                Position(this[1].toInt(), this[0].toInt())
            }
        }
    }
}

private fun canMoveToPosition(filledPoints: MutableSet<Position>, position: Position, maxXIndex: Int): Boolean {
    return position.x < maxXIndex && !filledPoints.contains(position)
}
