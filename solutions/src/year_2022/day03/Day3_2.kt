package year_2022.day03

import solve

fun main() = solve { lines ->
    lines.chunked(3).map {
        it.map {
            it.toSet()
        }.reduce { acc, chars ->
            acc.intersect(chars)
        }.first()
    }.sumOf {
        if (it.isUpperCase()) {
            val res = it.code - 'A'.code + 1 + 26
            println(res)
            res
        } else {
            val res = it.code - 'a'.code + 1
            println(res)
            res
        }
    }
}
