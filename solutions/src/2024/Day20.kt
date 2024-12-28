package `2024`

import utils.Point
import java.io.File
import java.util.LinkedList
import java.util.PriorityQueue
import kotlin.system.measureTimeMillis

operator fun List<CharArray>.get(point: Point) = this[point.x][point.y]

fun main() {
    measureTimeMillis {
        val result1 = mutableSetOf<Result>()
        val input1 =
            File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText().split("\n")

//        ###############
//        #...#...#2....#
//        #.#.#.#.#.###.#
//        #S#...#.#.#...#
//        #######.#.#.###
//        #######.#.#...#
//        #######.#.###.#
//        ###..E#...#...#
//        ###.#######.###
//        #...###...#...#
//        #.#####.#.###.#
//        #.#...#.#.#...#
//        #.#.#.#.#.#.###
//        #...#...#...###
//        ###############

//        There are 14 cheats that save 2 picoseconds.
//        There are 14 cheats that save 4 picoseconds.
//        There are 2 cheats that save 6 picoseconds.
//        There are 4 cheats that save 8 picoseconds.
//        There are 2 cheats that save 10 picoseconds.
//        There are 3 cheats that save 12 picoseconds.
//        There is one cheat that saves 20 picoseconds.
//        There is one cheat that saves 36 picoseconds.
//        There is one cheat that saves 38 picoseconds.
//        There is one cheat that saves 40 picoseconds.
//        There is one cheat that saves 64 picoseconds.
        cal(input1, -1, result1, Int.MAX_VALUE)
        val max = result1.maxBy { it.steps }.steps

//        val x = cal(input1, listOf(18, 19), result1, max)
        println(max)
        (1..max).forEach {
            if (it % 1 == 0) {
                result1.distinctBy {
                    if (it.point1 == null && it.point2 == null && it.steps != max) error("Invalid")
                    it.point1 to it.point2
                }.map {
                    max - it.steps
                }.sorted().groupBy { it }.mapValues { it.value.size }.also {
                    println(it)
                    println(it.values.size)
                }
            }
            cal(input1, it, result1, max)
        }


        result1.distinctBy {
            if (it.point1 == null && it.point2 == null && it.steps != max) error("Invalid")
            it.point1 to it.point2
        }.map {
            max - it.steps
        }.sorted().groupBy { it }.mapValues { it.value.size }.also {
            println(it)
            println(it.values.size)
        }.also {
            println(it.filter { it.key >= 50 }.values.sum())
        }
    }.also { println("Run took $it") }
}

//There are 32 cheats that save 50 picoseconds.
//There are 31 cheats that save 52 picoseconds.
//There are 29 cheats that save 54 picoseconds.
//There are 39 cheats that save 56 picoseconds.
//There are 25 cheats that save 58 picoseconds.
//There are 23 cheats that save 60 picoseconds.
//There are 20 cheats that save 62 picoseconds.
//There are 19 cheats that save 64 picoseconds.
//There are 12 cheats that save 66 picoseconds.
//There are 14 cheats that save 68 picoseconds.
//There are 12 cheats that save 70 picoseconds.
//There are 22 cheats that save 72 picoseconds.
//There are 4 cheats that save 74 picoseconds.
//There are 3 cheats that save 76 picoseconds.


private fun cal(
    input1: List<String>,
    cheat: Int,
    result: MutableSet<Result>,
    max: Int,
    maxCheats: Int = 3
) {
    val input = input1.map { it.toCharArray() }
    val points = input.mapIndexed { x, s ->
        s.mapIndexed { y, c ->
            Point(x, y)
        }
    }
    val startPoint = points.flatten().single { input[it] == 'S' }
    val endPoint = points.flatten().single { input[it] == 'E' }
//    val visited = mutableSetOf<VisitedState>()
    val queue = LinkedList<State>()
//    visited.add(VisitedState(startPoint, null))
    queue.offer(State(startPoint, 0, null, null, 0, setOf(startPoint)))

    while (queue.isNotEmpty()) {
        val top = queue.poll()

        if (top.point == endPoint) {
            result.add(
                Result(
                    top.cheatStartPoint,
                    top.cheatEndPoint,
                    top.numberOfVisitedPoints
                )
            )
//            println(
//                Result(
//                    top.cheatStartPoint,
//                    top.cheatEndPoint,
//                    top.numberOfVisitedPoints
//                )
//            )
        }

        listOf(1 to 0, 0 to 1, -1 to 0,  0 to -1).map {
            top.point.copy(x = top.point.x + it.first, y = top.point.y + it.second)
        }.filter {
            it.x in points.indices && it.y in points.first().indices && top.numberOfVisitedPoints < max && !top.visited.contains(
                it
            )
        }.forEach {
            val nextSecond = top.numberOfVisitedPoints + 1
            if (nextSecond == cheat) {
                queue.offer(State(it, nextSecond, top.point, it, 1, top.visited + it))
//                visited.add(VisitedState(it, top.point))
            } else if (input[it] == '#' && top.cheatStartPoint != null && top.cheatSeconds < maxCheats) {
                queue.offer(State(it, nextSecond, top.cheatStartPoint, it, top.cheatSeconds + 1, top.visited + it))
            } else if (input[it] != '#' && top.cheatStartPoint != null && top.cheatSeconds < maxCheats) {
                queue.offer(State(it, nextSecond, top.cheatStartPoint, it, top.cheatSeconds + 1, top.visited + it))
//                queue.offer(State(it, nextSecond, top.cheatStartPoint, top.cheatEndPoint, top.cheatSeconds, top.visited + it))
            } else if (input[it] != '#') {
                queue.offer(State(it, nextSecond, top.cheatStartPoint, top.cheatEndPoint, top.cheatSeconds, top.visited + it))
            }
//            visited.add(VisitedState(it, top.cheatStartPoint))

        }
    }
}

private data class Result(val point1: Point?, val point2: Point?, val steps: Int)
private data class VisitedState(val point: Point, val chat1: Point?)

private data class State(
    val point: Point,
    val numberOfVisitedPoints: Int,
    val cheatStartPoint: Point? = null,
    val cheatEndPoint: Point? = null,
    val cheatSeconds: Int,
    val visited: Set<Point>
) : Comparable<State> {
    override fun compareTo(other: State) = this.numberOfVisitedPoints.compareTo(other.numberOfVisitedPoints)
//    fun score() = (cheat1?.let { 1 } ?: 0) + (cheat2?.let { 1 } ?: 0)
}