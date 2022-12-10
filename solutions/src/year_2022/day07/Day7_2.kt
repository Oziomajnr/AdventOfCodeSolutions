package year_2022.day07

import solve

fun main() = solve { inputLines ->
    val cache = mutableMapOf<Folder, Long>()
    countSize(parseInput(inputLines), cache)
    val availableSpace = 70000000 - cache.maxBy { it.value }.value
    val requiredSpace = 30000000 - availableSpace
    cache.map {
        it.value
    }.sorted().find {
        it >= requiredSpace
    }

}
