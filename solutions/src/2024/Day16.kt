import common.Direction
import utils.Point
import java.io.File
import java.util.PriorityQueue

fun main() {
    val input =
        File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText().split("\n")
    val points = input.mapIndexed { x, s ->
        s.mapIndexed { y, c ->
            Point(x, y)
        }
    }
    val start = points.flatten().single { input[it.x][it.y] == 'S' }
    val queue = PriorityQueue<State>()
    val directionMap = mutableMapOf(
        Direction.Right to listOf(
            Point(0, 1),
            Point(-1, 0),
            Point(1, 0)
        ), Direction.Up to listOf(
            Point(-1, 0),
            Point(0, 1),
            Point(0, -1)
        ), Direction.Left to listOf(
            Point(0, -1),
            Point(-1, 0),
            Point(1, 0)
        ), Direction.Down to listOf(
            Point(1, 0),
            Point(0, 1),
            Point(0, -1)
        )
    )

    val visited = mutableSetOf<Pair<Point, Direction>>()
    val result = mutableSetOf<Pair<Point, Direction>>()
    queue.offer(State(start, Direction.Right, 0, 0).apply {
        this.points.add(start to Direction.Right)
    })
    visited.add(start to Direction.Right)
    while (queue.isNotEmpty()) {
        val top = queue.poll()
        visited.add(top.point to top.direction)

        if (input[top.point.x][top.point.y] == 'E') {
                println(top.score())
                result.addAll(top.points)
        } else {
            directionMap[top.direction]!!.map {
                top.point.copy(x = top.point.x + it.x, y = top.point.y + it.y) to getNewDirection(it)
            }.filter {
                it.first.x in points.indices && it.first.y in points.first().indices && input[it.first.x][it.first.y] != '#'
            }.forEach {
                val state = State(
                    it.first,
                    it.second,
                    top.steps + 1,
                    if (it.second == top.direction) top.turns else top.turns + 1,
                ).apply {
                    this.points.addAll(top.points + (it.first to it.second))
                }
                if (!visited.contains(it.first to it.second)) {
                    queue.offer(
                        state
                    )
                }
            }
        }
    }
    println(result.map { it.first }.toSet().size)
}


fun getNewDirection(point: Point): Direction {
    return when {
        point.x == 1 -> Direction.Down
        point.x == -1 -> Direction.Up
        point.y == 1 -> Direction.Right
        point.y == -1 -> Direction.Left
        else -> error("")
    }
}

private data class State(val point: Point, val direction: Direction, val steps: Int, val turns: Int) :
    Comparable<State> {
    fun score() = this.turns * 1000 + this.steps
    override fun compareTo(other: State) =
        (this.score()).compareTo(other.score())

    val points = mutableSetOf<Pair<Point, Direction>>()
}