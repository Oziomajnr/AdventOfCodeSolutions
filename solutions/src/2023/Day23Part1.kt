package com.ozioma

val lines = input.split("\n")
val points = lines.indices.map { x ->
    lines[x].indices.map { y ->
        Point(x, y)
    }
}

fun main() {
    val startingPoint = points[0].single {
        lines[it.x][it.y] == '.'
    }
    println(findLongestPath(startingPoint, emptySet()))
}

fun findLongestPath(currentPoint: Point, visitedPoints: Set<Point>): Int {
    if (currentPoint.x == lines.lastIndex) return visitedPoints.size
    val possibleNextPoints = when (lines[currentPoint.x][currentPoint.y]) {
        '.' -> {
            listOf(
                currentPoint.copy(x = currentPoint.x + 1),
                currentPoint.copy(x = currentPoint.x - 1),
                currentPoint.copy(y = currentPoint.y + 1),
                currentPoint.copy(y = currentPoint.y - 1)
            )
        }

        '<' -> listOf(currentPoint.copy(y = currentPoint.y - 1))
        '>' -> listOf(currentPoint.copy(y = currentPoint.y + 1))
        '^' -> listOf(currentPoint.copy(x = currentPoint.x - 1))
        'v' -> listOf(currentPoint.copy(x = currentPoint.x + 1))
        else -> error("Invalid point")
    }.filter {
        it.x in points.indices && it.y in points[0].indices && !visitedPoints.contains(it) && lines[it.x][it.y] != '#'
    }
    if (possibleNextPoints.isEmpty())
        return 0
    return possibleNextPoints.maxOf {
        findLongestPath(it, visitedPoints + currentPoint)
    }
}
