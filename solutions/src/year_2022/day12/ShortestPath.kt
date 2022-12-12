package year_2022.day12

import java.util.*

class ShortestPath(
    private val startPoint: Point,
    private val points: List<List<Point>>,
    val bestPathTable: MutableMap<Point, Pair<Int, Point>>
) {

    private val unVisitedPoints = PriorityQueue<Point> { a, b ->
        (bestPathTable[a]?.first ?: Int.MAX_VALUE).compareTo(
            bestPathTable[b]?.first ?: Int.MAX_VALUE
        )
    }.also {
        it.addAll(points.flatten())
    }

    fun solvePart1(point: Point) {
        val currentPointBestPath = bestPathTable[point]?.first ?: 0

        point.siblings.forEach {
            val siblingBestPathToStart = bestPathTable[it]?.first ?: Int.MAX_VALUE
            if (currentPointBestPath + it.value <= siblingBestPathToStart) {
                bestPathTable[it] = Pair(currentPointBestPath + it.value, point)
                //just remove and add again so that queue would be sorted
                unVisitedPoints.remove(it)
                unVisitedPoints.add(it)
            }
        }

    }

    fun findBestPathFromTopToBottomEdge(): MutableSet<Point> {
        val endPoint = points.flatten().find { it.isEndPoint }!!
        bestPathTable[startPoint] = Pair(0, startPoint)
        unVisitedPoints.remove(startPoint)
        unVisitedPoints.add(startPoint)
        while (unVisitedPoints.isNotEmpty() && bestPathTable[endPoint] == null) {
            solvePart1(unVisitedPoints.poll()!!)
        }
        return getCavesInPath()
    }

    fun getCavesInPath(): MutableSet<Point> {
        val endPoint = points.flatten().find { it.isEndPoint }!!
        val cavesInPath = mutableSetOf<Point>()
        cavesInPath.add(endPoint)
        bestPathTable.remove(startPoint)
        var x = bestPathTable[endPoint]
        var sum = 0
        while (x != null) {
            sum += x.second.value
            cavesInPath.add(x.second)
            x = bestPathTable[x.second]
        }
        return cavesInPath
    }
}