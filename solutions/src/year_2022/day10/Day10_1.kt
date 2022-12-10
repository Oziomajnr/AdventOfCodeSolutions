package year_2022.day10

import solve

fun main() = solve { lines ->
    var numberOfCycles = 0
    var xValue = 1
    var totalSignalStrength = 0
    val expectedCycles = setOf(20, 60, 100, 140, 180, 220)
    parseInput(lines).forEach {
        repeat(it.cycles) {
            numberOfCycles++
            if (expectedCycles.contains(numberOfCycles)) {
                totalSignalStrength += (xValue * numberOfCycles)
                println(totalSignalStrength)
            }
        }
        xValue += it.value
    }
    totalSignalStrength
}

fun parseInput(lines: List<String>): List<Command> {
    return lines.map { line ->
        line.split(" ").run {
            when (this[0]) {
                "addx" -> {
                    Command(this[1].toInt(), 2)
                }

                else -> {
                    Command(0, 2)
                }
            }
        }
    }
}

data class Command(val value: Int, val cycles: Int)


