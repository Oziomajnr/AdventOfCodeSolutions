package year_2022.day01

import solve

fun main() = solve { lines ->
    lines.joinToString("\n").split("\n\n").map {
        it.split('\n').map { it.toLong() }
    }.maxOfOrNull {
        it.sum()
    }!!
}
