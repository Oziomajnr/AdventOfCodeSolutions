package year_2022.day24

import solve
import year_2022.day09.Position

fun main() = solve { lines ->
    val (elves, blockedPositions) = parseInput(lines)
    val startPosition = Position(0, 1)
    val endPosition = Position(
        lines.lastIndex, lines.last().indexOf('.')
    )
    val maxX = lines.lastIndex
    val maxY = lines.first().lastIndex

    val x1 =
        findMinimumMinutesToReachEndPositionBfs(elves, startPosition, endPosition, maxX, maxY, 0, blockedPositions) + 1
    val x2 =
        findMinimumMinutesToReachEndPositionBfs(elves, endPosition, startPosition, maxX, maxY, x1, blockedPositions)
    val x3 =
        findMinimumMinutesToReachEndPositionBfs(elves, startPosition, endPosition, maxX, maxY, x2, blockedPositions) + 1
    x3
}
