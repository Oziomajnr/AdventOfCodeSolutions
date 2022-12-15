package year_2022.day14

import solve
import year_2022.common.Position

fun main() = solve { lines ->
    val input = lines.map {
        it.split(" -> ").map {
            it.split(",").run {
                Position(this[1].toInt(), this[0].toInt())
            }
        }
    }
    val maxYIndex = input.flatten().maxOf { it.y }
    val minYIndex = input.flatten().minOf { it.y }
    val maxXIndex = input.flatten().maxOf { it.x }

    val shrinkedInput = input.map {
        it.map {
            it.copy(y = it.y - minYIndex)
        }
    }
    val grid = Array(maxXIndex + 1) {
        CharArray(maxYIndex - minYIndex + 1) {
            '.'
        }
    }
    grid[0][500 - minYIndex] = '+'
    shrinkedInput.forEach {
        it.windowed(2).forEach { positions ->
            val firstPosition = positions[0]
            val secondPosition = positions[1]
            if (firstPosition.x == secondPosition.x) {
                if (firstPosition.y > secondPosition.y) {
                    for (y in secondPosition.y..firstPosition.y) {
                        grid[firstPosition.x][y] = '#'
                    }
                } else {
                    for (y in firstPosition.y..secondPosition.y) {
                        grid[firstPosition.x][y] = '#'
                    }
                }
            } else if (firstPosition.y == secondPosition.y) {
                if (firstPosition.x > secondPosition.x) {
                    for (x in secondPosition.x..firstPosition.x) {
                        grid[x][firstPosition.y] = '#'
                    }
                } else {
                    for (x in firstPosition.x..secondPosition.x) {
                        grid[x][firstPosition.y] = '#'
                    }
                }
            }
        }
    }

    grid.forEach {
        it.forEach {
            print(it)
        }
        println()
    }
    -1
}

//fun simulateSandFalling(grid: Array<CharArray>, sandPosition: Position){
//    var currentSandPosition = sandPosition
//    while (true) {
//        if(grid[])
//    }
//
//}
