package com.ozioma

fun main() {
    val lines = input.trim().split("\n")
    var result = lines
    val set = mutableMapOf<String, Int>()
    var count = 0
    var originalIndex = 0
    println("Part1: ${getLines(result.configureForNorth().simulateCycle().configureForNorth())}")
    for (it in 1..1000000) {
        result = result.configureForNorth().simulateCycle().configureForNorth()
            .configureForWest().simulateCycle().configureForWest()
            .configureForSouth(false).simulateCycle().configureForSouth(true)
            .configureForEast().simulateCycle().configureForEast()
        if (set.contains(result.joinToString("\n"))) {
            count = it
            originalIndex = set[result.joinToString("\n")]!!
            break
        } else {
            set[result.joinToString("\n")] = it
        }
    }

    result = lines

    repeat(originalIndex) {
        result = result.configureForNorth().simulateCycle().configureForNorth()
            .configureForWest().simulateCycle().configureForWest()
            .configureForSouth(false).simulateCycle().configureForSouth(true)
            .configureForEast().simulateCycle().configureForEast()
    }

    repeat((1000000000 - originalIndex) % (count - originalIndex)) {
        result = result.configureForNorth().simulateCycle().configureForNorth()
            .configureForWest().simulateCycle().configureForWest()
            .configureForSouth(false).simulateCycle().configureForSouth(true)
            .configureForEast().simulateCycle().configureForEast()
    }
    println("Part2: ${getLines(result)}")
}

fun getLines(inputLines: List<String>) = inputLines.mapIndexed { index, s ->
    s.count { it == 'O' } * (inputLines.size - index)
}.sum()


private fun List<String>.configureForNorth(): List<String> = rotate()

private fun List<String>.configureForWest() = this

private fun List<String>.configureForSouth(reverse: Boolean): List<String> =
    if (reverse) this.map { it.reversed() }.rotate() else this.rotate().map { it.reversed() }

private fun List<String>.configureForEast(): List<String> = this.map { it.reversed() }

private fun List<String>.simulateCycle(): List<String> {
    val lines = this
    return lines.mapIndexed { x, line ->
        var lastEmptyIndex = 0
        val stringBuilder = StringBuilder()
        line.forEachIndexed { y, c ->
            when (c) {
                '#' -> {
                    lastEmptyIndex = y + 1
                    stringBuilder.append(c)
                }

                'O' -> {
                    stringBuilder.insert(lastEmptyIndex, "O", 0, 1)
                    if (lastEmptyIndex != y) {
                        stringBuilder[y] = '.'
                    }
                    lastEmptyIndex++
                }

                else -> {
                    stringBuilder.append(c)
                }
            }
        }.toString()
        stringBuilder.toString()
    }
}
