package `2024`

import java.io.File

fun main() {
    val input = File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText()
    val lines = input.split("\n").map {
        it.split(Regex("\\s+")).map { it.toLong() }
    }
    println(lines.count { isSafe(it) }) //pt1
    println(lines.count { (0..it.lastIndex).any { index -> isSafe(it.filterIndexed { i, _ -> i != index }) } }) //pt2
}

fun isSafe(items: List<Long>) =
    items.windowed(2).all { it[1] > it[0] && it[1] - it[0] <= 3 } || items.windowed(2)
        .all { it[0] > it[1] && it[0] - it[1] <= 3 }
