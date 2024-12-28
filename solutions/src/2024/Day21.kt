package `2024`

import utils.Point
import java.util.LinkedList
import java.util.Queue

private operator fun List<String>.get(point: Point) = this[point.x][point.y]
fun main() {
    ("029A\n" +
            "980A\n" +
            "179A\n" +
            "456A\n" +
            "379A").split("\n").map {
//<A^A>^^AvvvA,
//<A^A^>^AvvvA,
//<A^A^^>AvvvA

//    +---+---+---+
//    | 7 | 8 | 9 |
//    +---+---+---+
//    | 4 | 5 | 6 |
//    +---+---+---+
//    | 1 | 2 | 3 |
//    +---+---+---+
//        | 0 | A |
//    +---+---+---+

//        +---+---+
//        | ^ | A |
//    +---+---+---+
//    | < | v | > |
//    +---+---+---+
//
//    029A
//    980A
//    179A
//    456A
//    379A

        val memo = mutableMapOf<Pair<Point, Char>, List<Pair<String, Point>>>()

        val keyPad = "789\n456\n123\n#0A".split("\n")
        val directionPad = "#^A\n<v>".split("\n")
        val firstResult = getOutput(keyPad, Point(3, 2), it[0], mutableMapOf())
        var result = firstResult

        it.drop(1).forEach { inputChar ->
            result.map { input ->
                getOutput(keyPad, input.second, inputChar, mutableMapOf()).map {
                    it.copy(first = input.first + it.first)
                }

            }.flatten().also {
                result = it
            }
        }
        lateinit var r2: List<Pair<String, Point>>
        (1..25).forEach {
            println(it)
            r2 = result.map { input ->
                var result2 = getOutput(directionPad, Point(0, 2), input.first.first(), memo)

                input.first.drop(1).forEach { inputChar ->
                    result2.map { input1 ->
                        getOutput(directionPad, input1.second, inputChar, memo).map {
                            it.copy(first = input1.first + it.first)
                        }
                    }.flatten().also {
                        result2 = it
                    }
                }
                result2
            }.flatten().let {
                val min = it.minBy { it.first.length }.first.length
                it.filter { it.first.length == min }
            }
            result = r2
        }

        val aa = r2.minBy { it.first.length }.first.length
        val bb = it.filter { it.isDigit() }.toInt()
        aa * bb
    }.sum().also { println(it) }
}


private data class KeyState(val currentList: String, val currentPosition: Point)

fun getOutput(
    keyPad: List<String>,
    initialPosition: Point,
    expectedOutPut: Char,
    memo: MutableMap<Pair<Point, Char>, List<Pair<String, Point>>>
): List<Pair<String, Point>> {
//    memo[initialPosition to expectedOutPut]?.let {
//        return it
//    }
    val directionMap = mapOf(-1 to 0 to '^', 1 to 0 to 'v', 0 to -1 to '<', 0 to 1 to '>')
    val result = mutableListOf<Pair<String, Point>>()
    val queue: Queue<KeyState> = LinkedList()
    val visited = mutableSetOf<Point>()
    queue.offer(KeyState("", initialPosition))
    while (queue.isNotEmpty()) {
        (1..queue.size).forEach {
            val top = queue.poll()
            visited.add(top.currentPosition)
            if (keyPad[top.currentPosition] == expectedOutPut) {
                result.add(top.currentList + 'A' to top.currentPosition)
            }
            directionMap.keys.map {
                top.currentPosition.copy(
                    x = top.currentPosition.x + it.first,
                    y = top.currentPosition.y + it.second
                ) to it
            }.filter {
                it.first.x in keyPad.indices
                        && it.first.y in keyPad.first().indices
                        && !visited.contains(it.first)
                        && keyPad[it.first] != '#'
            }.forEach {
                queue.offer(KeyState(top.currentList + directionMap[it.second], it.first))
            }
        }
        if (result.isNotEmpty()) {
            memo[initialPosition to expectedOutPut] = result
            return memo[initialPosition to expectedOutPut]!!
        }

    }
    error("invalid")
}
