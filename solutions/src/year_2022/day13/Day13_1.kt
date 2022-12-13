package year_2022.day13

import solve


fun main() = solve { lines ->
    val pairs = lines.joinToString("\n").split("\n\n").map {
        it.parseToPair()
    }


    pairs.forEach {
        println()
        println(it.first)
        println(it.second)
        println()
    }


    pairs.mapIndexed { index, value ->
        if (compareValue(value) == true) {
            index + 1
        } else {
          0
        }
    }.sum()
}