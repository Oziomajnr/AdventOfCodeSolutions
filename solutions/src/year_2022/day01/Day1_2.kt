package year_2022.day01

import solve

fun part1() = solve { lines ->
    lines.joinToString("\n").split("\n\n").map {
        it.split('\n').map { it.toLong() }
    }.map {
        it.sum()
    }.sorted().takeLast(3).sum()
}

fun part2() = solve { lines ->
    lines.joinToString("\n").split("\n\n").map {
        it.split('\n').map { it.toLong() }
    }.maxOfOrNull {
        it.sum()
    }!!
}

