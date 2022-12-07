package year_2022.day06

import solve

fun main() = solve { lines ->
    findMarker(lines.joinToString(""), 14)
}

fun findMarker(input: String, numberOfDistinctMarker: Int): Int {
    return input.indexOf(
        input.windowed(numberOfDistinctMarker).find {
            it.toSet().size == it.length
        }!!
    ) + numberOfDistinctMarker
}
