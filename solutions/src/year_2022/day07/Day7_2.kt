package year_2022.day07

import solve

fun main() = solve { inputLines ->
   val parsedInput = parseInput(inputLines).map {
        countSize(it)
    }
    val availableSpace = 70000000 - parsedInput.max()
    val requiredSpace = 30000000 - availableSpace
    parsedInput.sorted().find {
        it >= requiredSpace
    }

}
