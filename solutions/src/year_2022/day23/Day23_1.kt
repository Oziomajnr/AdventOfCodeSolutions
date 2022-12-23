package year_2022.day23

import solve
import year_2022.common.Position
import kotlin.math.abs

fun main() = solve { lines ->
    val elves = parseInput(lines).toMutableSet()

    repeat(10) {
        val proposals = mutableMapOf<Position, MutableList<Position>>()

        elves.forEach { position ->
            val proposal = position.getProposal(elves, it)
            if (proposal != null) {
                if (proposals[proposal] == null) {
                    proposals[proposal] = mutableListOf(position)
                } else {
                    proposals[proposal]!!.add(position)
                }
            }
        }
        proposals.filter {
            it.value.size == 1
        }.forEach {
            elves.add(it.key)
            elves.remove(it.value.single())
        }
    }

    val maxX = elves.maxOf {
        it.x
    }
    val maxY = elves.maxOf {
        it.y
    }
    val minX = elves.minOf {
        it.x
    }
    val minY = elves.minOf {
        it.y
    }

    val area = (abs(maxX - minX) + 1) * (abs(maxY - minY) + 1)
    area - elves.size

}

fun parseInput(lines: List<String>): Set<Position> {
    return lines.mapIndexed { x, line ->
        line.mapIndexed { y, character ->
            if (character == '#') {
                Position(x, y)
            } else {
                null
            }
        }.filterNotNull()
    }.flatten().toSet()
}

fun Position.getProposal(elves: Set<Position>, round: Int): Position? {
    val north = Position(x - 1, y)
    val south = Position(x + 1, y)
    val east = Position(x, y + 1)
    val west = Position(x, y - 1)
    val northWest = Position(x - 1, y - 1)
    val northEast = Position(x - 1, y + 1)
    val southWest = Position(x + 1, y - 1)
    val southEast = Position(x + 1, y + 1)

    val functions = buildList {
        add(ProposalFunction {
            if (!elves.contains(north) && !elves.contains(northEast) && !elves.contains(northWest)) {
                north
            } else {
                null
            }
        })
        add(ProposalFunction {
            if (!elves.contains(south) && !elves.contains(southEast) && !elves.contains(southWest)) {
                south
            } else {
                null
            }
        })
        add(ProposalFunction {
            if (!elves.contains(west) && !elves.contains(northWest) && !elves.contains(southWest)) {
                west
            } else {
                null
            }
        })

        add(ProposalFunction {
            if (!elves.contains(east) && !elves.contains(northEast) && !elves.contains(southEast)) {
                east
            } else {
                null
            }
        })
    }
    functions.forEachIndexed { index, function ->
        if (functions.last() == function) {
            function.next = functions.first()
        } else {
            function.next = functions[index + 1]
        }
    }

    val neighbors = setOf(north, south, east, west, northWest, northEast, southWest, southEast)
    return if (neighbors.intersect(elves).isEmpty()) {
        null
    } else {
        val index = round % 4
        var startFunction = functions[index]
        repeat(4) {
            val result = startFunction.function.invoke(elves)
            if (result == null) {
                startFunction = startFunction.next
            } else {
                return result
            }
        }
        null
    }
}

data class ProposalFunction(val function: (Set<Position>) -> (Position?)) {
    lateinit var next: ProposalFunction
}

