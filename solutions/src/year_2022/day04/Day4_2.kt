package year_2022.day04

import solve

fun main() = solve { lines ->
    lines.map {
        val range = it.split(",")
        val firstPair = range[0]
        val secondPair = range[1]
        val firstRange = (firstPair.split("-")[0].toInt()..firstPair.split("-")[1].toInt())
        val secondRange = (secondPair.split("-")[0].toInt()..secondPair.split("-")[1].toInt())
        firstRange.intersect(secondRange).isNotEmpty()
    }.count {
        it
    }
}
