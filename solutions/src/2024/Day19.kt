package `2024`

import java.io.File

fun main() {
    val (patterns, input) =
        File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readLines()
            .filter { it.isNotEmpty() }.let { it[0].split(", ").toSet() to it.drop(1) }
    input.map {
        valid(it, it.take(1), it.drop(1), patterns, mutableMapOf())
    }.also {
        println("Part 1: ${it.count { it > 0 }}\nPart 2: ${it.sum()}")
    }
}

fun valid(
    originalInput: String,
    tail: String,
    head: String,
    patterns: Set<String>,
    cache: MutableMap<String, Long>
): Long {
    cache[tail + head]?.let { return it }
    cache[tail + head] = if (head.isEmpty() && !patterns.contains(tail)) 0
    else if (head.isEmpty() && patterns.contains(tail)) 1
    else if (patterns.contains(tail)) {
        valid(originalInput, tail + head.take(1), head.drop(1), patterns, cache) +
                valid(originalInput, head.take(1), head.drop(1), patterns, cache)
    } else {
        valid(originalInput, tail + head.take(1), head.drop(1), patterns, cache)
    }
    return cache[tail + head]!!
}