package year_2022.day12

import solve

fun main() = solve { lines ->
    val heightMap = parseInput(lines)
    connectPoints(heightMap)
     val cache = mutableMapOf<Point, Pair<Int, Point>>()
    heightMap.flatten().filter {
        it.value == 2
    }.minOfOrNull {
        val visitedPoints = ShortestPath(it, heightMap, cache).findBestPathFromTopToBottomEdge()
        heightMap.forEach {
            it.forEach {
                if (visitedPoints.contains(it)) {
                    print((it.value + 'a'.code - 2).toChar())
                } else {
                    print(".")
                }
            }
            println()
        }
        visitedPoints.size - 1
    }


}
