package `2024`

import common.transpose
import java.io.File

fun main() {
    File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText().split("\n\n").map {
        it.split("\n")
    }.groupBy {
        if (it.first().all { it == '#' }) "lock" else "key"
    }.mapValues {
        it.value.map {
            it.map { it.toCharArray().toList() }.transpose { ' ' }.map {
                it.count { it == '#' }
            }
        }
    }.apply {
        val locks = this["lock"]!!
        val keys = this["key"]!!
        println(locks.sumOf { lock ->
            keys.count { key ->
                lock.zip(key).all {
                    it.first + it.second <= 7
                }
            }
        })

    }
}