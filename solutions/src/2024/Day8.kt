package `2024`

import utils.Point
import java.io.File

fun main() {
    val input = File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText()
    val antennas = input.split("\n").mapIndexed { x, s -> s.mapIndexed { y, c ->  Antenna(c, Point(x, y)) } }
    val validPoint = mutableSetOf<Point>()
    val antennaGroups = antennas.flatten().filter { it.value != '.' }.groupBy { it.value }
    antennaGroups.forEach {
        it.value.forEachIndexed { index, antenna ->
            it.value.forEachIndexed { index1, antenna1 ->
                if (index != index1) {
                    (1..1000).map { factor ->
                        val sortedByX = listOf(antenna, antenna1).sortedBy { it.point.x }
                        val absX = (sortedByX[1].point.x - sortedByX[0].point.x) * factor
                        val absY = (sortedByX[0].point.y - sortedByX[1].point.y) * factor

                        listOf(
                            Point(
                                sortedByX[0].point.x - absX,
                                sortedByX[0].point.y + absY
                            ),
                            Point(
                                sortedByX[1].point.x + absX,
                                sortedByX[1].point.y - absY
                            )
                        ).filter { it.x in antennas.indices && it.y in antennas.first().indices }.also {
                            if (it.isEmpty()) return@map
                            validPoint.addAll(it)
                        }
                    }
                }
            }
        }
    }
    antennaGroups.filter { it.value.size > 1 }.map { validPoint.addAll(it.value.map { it.point }) }
    println(validPoint.size )
}

data class Antenna(val value: Char, val point: Point)