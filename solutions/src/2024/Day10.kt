package `2024`

import utils.Point
import java.io.File

fun main() {
    val input =
        File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText().split("\n")
    val points = input.mapIndexed { x, s ->
        s.mapIndexed { y, c ->
            Trail(c.digitToInt(), Point(x, y))
        }
    }
    println(
        "Part1: ${
            points.flatten().filter { it.value == 0 }.sumOf {
                calculateScore(
                    it, points, mutableSetOf(), true
                )
            }
        }"
    )
    println(
        "Part2: ${
            points.flatten().filter { it.value == 0 }.sumOf {
                calculateScore(
                    it, points, mutableSetOf(), false
                )
            }
        }"
    )
}

fun calculateScore(trail: Trail, trails: List<List<Trail>>, visited: Set<Point>, part1: Boolean): Int {
    if (trail.value == 9) return 1
    return listOf(0 to 1, 1 to 0, -1 to 0, 0 to -1).map {
        Point(x = trail.position.x + it.first, y = trail.position.y + it.second)
    }.mapNotNull {
        if (it.x in trails.indices && it.y in trails.first().indices
            && !visited.contains(trails[it.x][it.y].position) && trails[it.x][it.y].value - trail.value == 1
        ) {
            (visited as MutableSet).add(it)
            calculateScore(trails[it.x][it.y], trails, if (part1) visited else visited + it, part1)
        } else null
    }.sum()
}

data class Trail(val value: Int, val position: Point)