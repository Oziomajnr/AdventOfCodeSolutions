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
            val crtIndex = (numberOfCycles - 1) % 40
            screen[(numberOfCycles - 1) / 40][crtIndex] = if (setOf(
                    spritePosition, spritePosition - 1, spritePosition + 1
                ).contains(crtIndex)
            ) {
                '#'
            } else {
                '.'
            }
        }
            spritePosition += it.value
    }
    screen.forEach {
        println(it.joinToString(" "))
    }
}
