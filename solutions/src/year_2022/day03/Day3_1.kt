package year_2022.day03

import solve

fun main() = solve { lines ->
    lines.map {
        it.chunked(it.length / 2).map {
            it.toCharArray().toSet()
        }.reduce { acc, s ->
            acc.intersect(s)
        }.map {
            it
        }
    }.sumOf {
        it.sumOf {
            if (it.isUpperCase()) {
                val res = it.code - 'A'.code + 1 + 26
                res
            } else {
                val res = it.code - 'a'.code + 1
                res
            }

        }
    }
}
