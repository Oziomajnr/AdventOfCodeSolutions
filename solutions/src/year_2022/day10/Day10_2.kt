package year_2022.day10

import solve

fun main() = solve { lines ->
    var numberOfCycles = 0
    val screen = List(6) {
        MutableList(40) {
            '.'
        }
    }
    var spritePosition = 1
    parseInput(lines).forEach {
        repeat(it.cycles) {
            numberOfCycles++
            val crtIndex =  (numberOfCycles-1) % 40
            if (setOf(
                    spritePosition, spritePosition - 1, spritePosition + 1
                ).contains(crtIndex)
            ) {
                screen[(numberOfCycles - 1) / 40][crtIndex] = '#'
            } else {
                screen[(numberOfCycles - 1) / 40][crtIndex] = '.'
            }
        }
        if (it is Command.Add) {
            spritePosition += it.value
        }
    }
    screen.forEach {
        println(it.joinToString(" "))
    }
}
