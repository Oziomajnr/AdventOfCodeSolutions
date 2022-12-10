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
        if (it is Command.Add) {
            xValue += it.value
        }
    }
    totalSignalStrength
}

fun parseInput(lines: List<String>): List<Command> {
    return lines.map { line ->
        line.split(" ").run {
            when (this[0]) {
                "addx" -> {
                    Command.Add(this[1].toInt())
                }

                "noop" -> {
                    Command.NoOp()
                }

                else -> {
                    error("Invalid command")
                }
            }
        }
    }
}

sealed interface Command {
    val cycles: Int

    data class Add(val value: Int, override val cycles: Int = 2) : Command
    data class NoOp(override val cycles: Int = 1) : Command
}

