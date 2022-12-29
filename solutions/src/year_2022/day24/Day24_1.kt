package year_2022.day24

import solve
import year_2022.day09.Position
import java.util.LinkedList

fun main() = solve { lines ->
    val (elves, blockedPositions) = parseInput(lines)
    val startPosition = Position(0, 1)
    val endPosition = Position(
        lines.lastIndex, lines.last().indexOf('.')
    )
    val maxX = lines.lastIndex
    val maxY = lines.first().lastIndex
    findMinimumMinutesToReachEndPositionBfs(elves, startPosition, endPosition, maxX, maxY, 0, blockedPositions) + 1
}

fun parseInput(lines: List<String>): Pair<Set<Elf>, Set<Position>> {
    val elves = mutableSetOf<Elf>()
    val blockedPositions = mutableSetOf<Position>()
    lines.forEachIndexed { x, line ->
        line.forEachIndexed { y, character ->
            when (character) {
                '^' -> {
                    elves.add(Elf(Position(x, y), Direction.Up))
                }

                '<' -> {
                    elves.add(Elf(Position(x, y), Direction.Left))
                }

                '>' -> {
                    elves.add(Elf(Position(x, y), Direction.Right))
                }

                'v' -> {
                    elves.add(Elf(Position(x, y), Direction.Down))
                }

                '#' -> {
                    blockedPositions.add(Position(x, y))
                }
            }
        }
    }
    return elves to blockedPositions
}

val cache = mutableSetOf<Pair<Position, Int>>()
val minuteToElves = mutableMapOf<Int, Set<Position>>()

fun findMinimumMinutesToReachEndPositionBfs(
    elves: Set<Elf>,
    startPosition: Position,
    endPosition: Position,
    maxX: Int,
    maxY: Int,
    initialMinute: Int,
    blockedPositions: Set<Position>
): Int {
    val queue = LinkedList<Pair<Int, Position>>()
    queue.offer(initialMinute to startPosition)
    while (queue.isNotEmpty()) {
        val currentEntry = queue.poll()
        val currentMinute = currentEntry.first
        val currentPosition = currentEntry.second

        if (minuteToElves[currentMinute % (maxX * maxY) + 1] == null) {
            elves.forEach {
                it.moveForward(maxX, maxY)
            }
            minuteToElves[currentMinute + 1] = elves.map {
                val newElf = it.copy()
                newElf.currentPosition = it.currentPosition.copy()
                newElf.currentPosition
            }.toSet()
        }

        if (!cache.contains(currentPosition to currentMinute % ((maxX * maxY) + 1))) {
            val validCandidates = listOf(
                currentPosition.copy(x = currentPosition.x + 1),
                currentPosition.copy(y = currentPosition.y + 1),
                currentPosition.copy(y = currentPosition.y - 1),
                currentPosition,
                currentPosition.copy(x = currentPosition.x - 1),
            ).filter { possibleNextMove ->
                possibleNextMove.x >= 0 && possibleNextMove.y >= 0 && possibleNextMove.x <= maxX && possibleNextMove.y <= maxY && !minuteToElves[currentMinute + 1]!!.contains(
                    possibleNextMove
                ) && !blockedPositions.contains(possibleNextMove)
            }
            validCandidates.forEach {
                if (it == endPosition) {
                    return currentMinute
                }
                queue.offer(currentMinute + 1 to it)
            }
            cache.add(currentPosition to currentMinute % ((maxX * maxY) + 1))
        }
    }
    error("Path now found")
}

data class Elf(val initialPosition: Position, val direction: Direction) {
    var currentPosition: Position = initialPosition
    fun moveForward(maxX: Int, maxY: Int) {
        when (direction) {
            Direction.Up -> {
                currentPosition = if (currentPosition.x - 1 == 0) {
                    currentPosition.copy(x = maxX - 1)
                } else {
                    currentPosition.copy(x = currentPosition.x - 1)
                }
            }

            Direction.Down -> {
                currentPosition = if (currentPosition.x + 1 == maxX) {
                    currentPosition.copy(x = 1)
                } else {
                    currentPosition.copy(x = currentPosition.x + 1)
                }
            }

            Direction.Right -> {
                currentPosition = if (currentPosition.y + 1 == maxY) {
                    currentPosition.copy(y = 1)
                } else {
                    currentPosition.copy(y = currentPosition.y + 1)
                }
            }

            Direction.Left -> {
                currentPosition = if (currentPosition.y - 1 == 0) {
                    currentPosition.copy(y = maxY - 1)
                } else {
                    currentPosition.copy(y = currentPosition.y - 1)
                }
            }
        }
    }
}

enum class Direction {
    Up, Down, Right, Left
}
