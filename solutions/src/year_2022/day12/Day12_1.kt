package year_2022.day12

import solve

fun main() = solve { lines ->
    val heightMap = parseInput(lines)
    connectPoints(heightMap)
    val cache = mutableMapOf<Point, Pair<Int, Point>>()
    val visitedPoints =
        ShortestPath(heightMap.flatten().find { it.isStartPoint }!!, heightMap, cache).findBestPathFromTopToBottomEdge()
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

fun connectPoints(heightMap: List<List<Point>>) {
    heightMap.forEachIndexed { pointsIndex, points ->
        points.forEachIndexed { pointIndex, point ->
            if (point.x - 1 >= 0 && heightMap[point.y][point.x - 1].value - point.value <= 1) {
                point.siblings.add(heightMap[point.y][point.x - 1])
            }
            if (point.x + 1 <= points.lastIndex && heightMap[point.y][point.x + 1].value - point.value <= 1) {
                point.siblings.add(heightMap[point.y][point.x + 1])
            }
            if (point.y - 1 >= 0 && heightMap[point.y - 1][point.x].value - point.value <= 1) {
                point.siblings.add(heightMap[point.y - 1][point.x])
            }
            if (point.y + 1 <= heightMap.lastIndex && heightMap[point.y + 1][point.x].value - point.value <= 1) {
                point.siblings.add(heightMap[point.y + 1][point.x])
            }
        }
    }
}

fun parseInput(lines: List<String>): List<List<Point>> {
    return lines.mapIndexed { index, line ->
        line.mapIndexed { heightIndex, height ->
            when (height) {
                'S' -> {
                    Point(index, heightIndex, isStartPoint = true).apply {
                        value = 2
                    }
                }

                'E' -> {
                    Point(index, heightIndex, isEndPoint = true).apply {
                        value = 27
                    }
                }

                else -> {
                    Point(index, heightIndex).apply {
                        value = height.code - 'a'.code + 2
                    }
                }
            }
        }
    }
}

data class Point(
    val y: Int, val x: Int, val isStartPoint: Boolean = false, val isEndPoint: Boolean = false
) {

    var value: Int = 0
    val siblings = mutableListOf<Point>()
}