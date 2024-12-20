package `2024`

import common.Direction
import utils.Point
import java.io.File

data class Perimeter(val point: Point, val direction: Direction)

fun main() {
    val input =
        File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText().split("\n")
    ('A'..'Z').filter { xx -> input.any { it.contains(xx) } }.map { symbol ->
        val visited = mutableSetOf<Point>()
        var result1 = 0
        var result2 = 0
        while (input.mapIndexed { x, s -> s.mapIndexed { y, c -> Point(x, y) } }.flatten()
                .find { input[it.x][it.y] == symbol && !visited.contains(it) } != null
        ) {
            val kk = input.mapIndexed { x, s -> s.mapIndexed { y, c -> Point(x, y) } }.flatten()
                .find { input[it.x][it.y] == symbol && !visited.contains(it) }!!
            val newVisited = mutableSetOf<Point>()
            newVisited.add(kk)
            val perimeterCount = mutableMapOf<Char, List<Perimeter>>()
            findAreaParameter(symbol, input, newVisited, kk, perimeterCount)
            val sides = perimeterCount.getOrDefault(symbol, emptyList()).let { perimeters ->
                perimeters.filter { it.direction == Direction.Up || it.direction == Direction.Down }
                    .groupBy { it.direction to it.point.x }
                    .mapValues { it.value.sortedBy { it.point.y } }.mapValues {
                        it.value.windowed(2).count { it[1].point.y - it[0].point.y > 1 } + 1
                    }.values.sum() + perimeters.filter { it.direction == Direction.Right || it.direction == Direction.Left }
                    .groupBy { it.direction to it.point.y }
                    .mapValues { it.value.sortedBy { it.point.x } }.mapValues {
                        it.value.windowed(2).count { it[1].point.x - it[0].point.x > 1 } + 1
                    }.values.sum()
            }
            visited.addAll(newVisited)

            result1 += perimeterCount[symbol]!!.size * newVisited.size
            result2 += sides * newVisited.size
        }
        result1 to result2
    }.reduce { a, acc -> a.first + acc.first to a.second + acc.second }.also {
        println("Part1:${it.first}  Part2: ${it.second}")
    }
}


fun findAreaParameter(
    symbol: Char,
    input: List<String>,
    visited: MutableSet<Point>,
    currentPoint: Point,
    perimeterCount: MutableMap<Char, List<Perimeter>>
) {
    val candidates = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
    perimeterCount[symbol] = perimeterCount.getOrDefault(symbol, emptyList()) + candidates.filter {
        val newPoint = currentPoint.copy(x = currentPoint.x + it.first, y = currentPoint.y + it.second)
        newPoint.x !in input.indices || newPoint.y !in input.first().indices || input.getOrNull(newPoint.x)
            ?.getOrNull(newPoint.y) != symbol
    }.map {
        when (it) {
            0 to 1 -> Perimeter(currentPoint, Direction.Right)
            1 to 0 -> Perimeter(currentPoint, Direction.Down)
            0 to -1 -> Perimeter(currentPoint, Direction.Left)
            -1 to 0 -> Perimeter(currentPoint, Direction.Up)
            else -> error("")
        }
    }
    candidates.map { currentPoint.x + it.first to currentPoint.y + it.second }
        .filter { it.first in input.indices && it.second in input.first().indices && input[it.first][it.second] == symbol }
        .forEach {
            if (!visited.contains(Point(it.first, it.second))) {
                visited.add(Point(it.first, it.second))
                findAreaParameter(symbol, input, visited, Point(it.first, it.second), perimeterCount)
            }
        }

}