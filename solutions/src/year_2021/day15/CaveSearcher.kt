package year_2021.day15

import com.example.pgntogifconverter.util.day15.Cave
import java.util.*

class CaveSearcher(private val caves: List<List<Cave>>) {
    private val bestPathTable = mutableMapOf<Cave, Pair<Int, Cave>>()

    private val unVisitedCaves = PriorityQueue<Cave> { a, b ->
        (bestPathTable[a]?.first ?: Int.MAX_VALUE).compareTo(
            bestPathTable[b]?.first ?: Int.MAX_VALUE
        )
    }.also {
        it.addAll(caves.flatten())
    }


    private fun connectCaves() {
        caves.forEachIndexed { y, list ->
            list.forEachIndexed { x, xValue ->
                if (x - 1 >= 0) {
                    xValue.siblings.add(caves[y][x - 1])
                }
                if (x + 1 <= caves.first().lastIndex) {
                    xValue.siblings.add(caves[y][x + 1])
                }
                if (y + 1 <= caves.lastIndex) {
                    xValue.siblings.add(caves[y + 1][x])
                }
                if (y - 1 >= 0) {
                    xValue.siblings.add(caves[y - 1][x])
                }
            }
        }
    }

    fun solvePart1(cave: Cave) {
        val currentCaveBestPath = bestPathTable[cave]?.first ?: 0

        cave.siblings.forEach {
            val sibilingBestPathToStart = bestPathTable[it]?.first ?: Int.MAX_VALUE
            if (currentCaveBestPath + it.value <= sibilingBestPathToStart) {
                bestPathTable[it] = Pair(currentCaveBestPath + it.value, cave)
                //just remove and add again so that queue would be sorted
                unVisitedCaves.remove(it)
                unVisitedCaves.add(it)
            }
        }

    }

    fun findBestPathFromTopToBottomEdge(): Int {
        val lastCave = caves[caves.lastIndex][caves.first().lastIndex]
        val firstCave = caves[0][0]
        connectCaves()
        bestPathTable[firstCave] = Pair(0, firstCave)
        unVisitedCaves.remove(firstCave)
        unVisitedCaves.add(firstCave)
        while (unVisitedCaves.isNotEmpty() && bestPathTable[lastCave] == null) {
            solvePart1(unVisitedCaves.poll()!!)
        }
        return bestPathTable[lastCave]!!.first
    }
}