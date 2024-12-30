package `2024`

import java.io.File

fun main() {
    File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readLines().map {
        it.split("-").run {
            listOf(this[0] to this[1], this[1] to this[0])
        }
    }.flatten().groupBy { it.first }.mapValues { it.value.map { it.second } }.run {
        println("Part2: ${getLongest(this).joinToString(",")}")
        this.map { entry ->
            this[entry.key]!!.mapNotNull {
                this[it]?.intersect((this[entry.key]?.toSet() ?: emptySet()).toSet())?.map { xxx ->
                    listOf(entry.key, it, xxx).sorted().let {
                        Triple(it[0], it[1], it[2])
                    }
                }
            }
        }.flatten()
    }.flatten().sortedBy { listOf(it.first, it.second, it.third).sorted().joinToString("") }.toSet().run {
        filter { it.first.startsWith("t") || it.second.startsWith("t") || it.third.startsWith("t") }
            .apply {
                println("Part1: ${this.size}")
            }
    }
}

fun getLongest(input: Map<String, List<String>>): Set<String> {
    val keys = input.keys.toMutableSet()
    val mutableInput = input.toMutableMap().mapValues { it.value.toSet() }
    dfs(emptySet(), keys, emptySet(), mutableInput)
    return result1.maxBy { it.size }.sorted().toSet()
}
val result1 = mutableSetOf<Set<String>>()
fun dfs(
    currentClique: Set<String>,
    keys: Set<String>,
    visited: Set<String>,
    inputMap: Map<String, Set<String>>
) {
    if (keys.isEmpty() && visited.isEmpty()) {
        result1.add(currentClique)
    }
    else {
        val pivot  = (keys union  visited).first()
        (keys - inputMap[pivot]!!).forEach {
            dfs(
                currentClique.union(setOf(it)),
                keys.intersect(inputMap[it]!!),
                visited.intersect(inputMap[it]!!),
                inputMap
            )
        }
    }

}
//Algorithm: Bron-Kerbosch Algorithm (simple)
//
//BK(R, P, X):
//if P and X are empty:
//output R
//for each v ∊ P:
//BK(R∪{v}, P∩N(v), X∩N(v))
//P = P \ {v}
//X = X∪{v}
//BK(∅, V, ∅)
//
//Backtracking
//
//Recursive
//
//R - current clique
//P - candidate set
//X - exclusion set
