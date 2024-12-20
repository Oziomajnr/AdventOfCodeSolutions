package `2024`

import utils.Point
import java.io.File

fun main() {
    val (input, directions) =
        File("input.txt").readText().split("\n\n")
            .let {
                it[0].let {
                    it.replace("#", "##").replace(".", "..").replace("@", "@.").replace("O", "[]")
                }.split("\n").toMutableList().map { it.toMutableList() }.toMutableList() to it[1].split("\n")
                    .joinToString("")
            }
    val directionMap = mapOf(
        '^' to Point(-1, 0),
        'v' to Point(1, 0),
        '<' to Point(0, -1),
        '>' to Point(0, 1),
    )
    var robotPosition = input.mapIndexed { x, chars ->
        chars.mapIndexed { y, c ->
            if (c == '@') x to y
            else null
        }
    }.flatten().filterNotNull().map { Point(it.first, it.second) }.single()

    directions.forEach {
        val direction = directionMap[it]!!
        val nextPosition = robotPosition.copy(x = robotPosition.x + direction.x, y = robotPosition.y + direction.y)
        val inputCopy = input.map { it.map { it }.toMutableList() }.toMutableList()

        val result = if (direction.x != 0 && input.getOrNull(nextPosition.x)?.getOrNull(nextPosition.y)
                .let { it == ']' || it == '[' }
        ) {
            input.getOrNull(nextPosition.x)!!.getOrNull(nextPosition.y)!!.let {
                (if (it == '[') proceed(nextPosition.copy(y = nextPosition.y + 1), input, direction)
                else proceed(nextPosition.copy(y = nextPosition.y - 1), input, direction)) && proceed(
                    nextPosition, input, direction
                )
            }
        } else {
            proceed(nextPosition, input, direction)
        }
        if (result) {
            input[robotPosition.x][robotPosition.y] = '.'
            robotPosition = nextPosition
            input[robotPosition.x][robotPosition.y] = '@'
        } else {
            inputCopy.forEachIndexed { x, chars -> chars.forEachIndexed { y, c -> input[x][y] = c } }
        }
    }

    input.mapIndexed { x, chars ->
        chars.mapIndexed { y, c ->
            if (c == '[') (100 * x + y).toLong() else 0L
        }.sum()
    }.sum().also(::println)

}

fun proceed(
    currentPosition: Point,
    input: MutableList<MutableList<Char>>,
    direction: Point
): Boolean {
    val nextPosition = currentPosition.copy(
        x = currentPosition.x + direction.x,
        y = currentPosition.y + direction.y
    )
    return when (input[currentPosition.x][currentPosition.y]) {
        '#' -> false

        '[', ']' -> {
            (if (direction.x != 0 && input.getOrNull(nextPosition.x)?.getOrNull(nextPosition.y)
                    .let { it == ']' || it == '[' }
            ) {
                input.getOrNull(nextPosition.x)!!.getOrNull(nextPosition.y)!!.let {
                    (if (it == '[') proceed(nextPosition.copy(y = nextPosition.y + 1), input, direction)
                    else proceed(nextPosition.copy(y = nextPosition.y - 1), input, direction)) && proceed(
                        nextPosition, input, direction
                    )
                }
            } else {
                proceed(nextPosition, input, direction)
            }).apply {
                if (this) {
                    input[nextPosition.x][nextPosition.y] = input[currentPosition.x][currentPosition.y]
                    input[currentPosition.x][currentPosition.y] = '.'
                }
            }
        }

        'O' -> {
            proceed(
                nextPosition, input, direction
            ).apply {
                if (this) input[nextPosition.x][nextPosition.y] = input[currentPosition.x][currentPosition.y]
            }
        }

        '.' -> true

        else -> error("invalid state")
    }
}