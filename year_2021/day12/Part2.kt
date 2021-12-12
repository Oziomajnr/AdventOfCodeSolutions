
import org.junit.Test
import java.io.File

class Part1 {
    private val dataPoll = mutableSetOf<Cave>()

    private val fileInput =
        File("input.txt").readLines()
            .map {
                val splittedValue = it.split('-')
                val first = Cave(splittedValue.first())
                val second = Cave(splittedValue[1])
                dataPoll.add(first)
                dataPoll.add(second)
                Pair(dataPoll.first { it == first }, dataPoll.first { it == second })
            }


    val result = mutableListOf<List<Cave>>()

    fun solvePart1(currentCave: Cave, currentResult: List<Cave>) {
        val validSiblings = currentCave.siblings.filter {
            it.isBigCave || !currentResult.contains(it)
        }
        if (currentCave.isEndCave) {
            result.add(currentResult)
        }

        for (cave in validSiblings) {
            solvePart1(cave, currentResult + currentCave)
        }
    }

    private fun connectCaves(caves: Set<Cave>) {
        caves.forEach { cave ->
            fileInput.forEach { cavePair ->
                if (cavePair.first == cave) {
                    if (!cavePair.second.isStartCave) {
                        cave.siblings.add(cavePair.second)
                    }
                    val secondCave = caves.find { cavePair.second == it }!!
                    if (!secondCave.isEndCave && !cave.isStartCave) {
                        secondCave.siblings.add(cave)
                    }
                }
            }
        }
    }

    @Test
    fun test() {
        val allCaves = fileInput.map {
            it.toList()
        }.flatten().toSet()

        val startCave = allCaves.find {
            it.isStartCave
        }!!
        connectCaves(allCaves)
        solvePart1(startCave, emptyList())
        println(result.size)
    }
}

data class Cave(
    val value: String
) {
    val siblings: MutableSet<Cave> = mutableSetOf()
    val isStartCave = value == "start"
    val isEndCave = value == "end"
    val isBigCave: Boolean = value.filter {
        it.isUpperCase()
    }.length == value.length

    override fun toString(): String {
        return value
    }
}

