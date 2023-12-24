package com.ozioma

import kotlin.collections.MutableSet as MutableSet

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
    val endPoint = points.last().single {
        lines[it.x][it.y] == '.'
    }
    val graph = mutableMapOf(startingPoint to mutableSetOf<Edge>())
    val junctions = points.flatten().filter {
        lines[it.x][it.y] != '#' &&
                listOf(
                    it.copy(x = it.x + 1),
                    it.copy(x = it.x - 1),
                    it.copy(y = it.y + 1),
                    it.copy(y = it.y - 1)
                ).filter {
                    it.x in points.indices && it.y in points[0].indices && lines[it.x][it.y] != '#'
                }.size > 2
    } + startingPoint + endPoint
    junctions.forEach {
        buildGraphFromJunction(it, graph, junctions.toSet())
    }
    println(findLongestPath(startingPoint, graph, emptySet(), 0))
}

fun buildGraphFromJunction(junction: Point, currentGraph: MutableMap<Point, MutableSet<Edge>>, junctions: Set<Point>) {
    listOf(
        junction.copy(x = junction.x + 1),
        junction.copy(x = junction.x - 1),
        junction.copy(y = junction.y + 1),
        junction.copy(y = junction.y - 1)
    ).filter {
        it.x in points.indices && it.y in points[0].indices && lines[it.x][it.y] != '#'
    }.forEach {
        addPathToNextJunction(junction, it, currentGraph, setOf(junction), junctions, 1)
    }
}

fun addPathToNextJunction(
    junction: Point,
    currentPoint: Point,
    currentGraph: MutableMap<Point, MutableSet<Edge>>,
    visited: Set<Point>,
    junctions: Set<Point>,
    weight: Int
) {
    val possiblePoints = listOf(
        currentPoint.copy(x = currentPoint.x + 1),
        currentPoint.copy(x = currentPoint.x - 1),
        currentPoint.copy(y = currentPoint.y + 1),
        currentPoint.copy(y = currentPoint.y - 1)
    ).filter {
        it.x in points.indices && it.y in points[0].indices && lines[it.x][it.y] != '#' && !visited.contains(it)
    }
    if (possiblePoints.isEmpty()) return
    val nextPoint = possiblePoints.single()
    if (junctions.contains(nextPoint)) {
        val junctionEdges = currentGraph[junction] ?: mutableSetOf()
        junctionEdges.add(Edge(nextPoint, weight + 1))
        currentGraph[junction] = junctionEdges
        return
    }
    addPathToNextJunction(junction, nextPoint, currentGraph, visited + currentPoint, junctions, weight + 1)
}

fun findLongestPath(
    currentNode: Point,
    currentGraph: Map<Point, MutableSet<Edge>>,
    visited: Set<Point>,
    currentSum: Int
): Int {
    val edges = currentGraph[currentNode].orEmpty().filter {
        !visited.contains(it.point)
    }
    if (currentNode.x == points.lastIndex) {
        return currentSum
    }
    if (edges.isEmpty()) return 0
    return edges.maxOf {
        findLongestPath(it.point, currentGraph, visited + currentNode, currentSum + it.weight)
    }
}

data class Edge(val point: Point, val weight: Int)
