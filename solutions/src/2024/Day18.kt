import utils.Point
import java.io.File
import java.util.LinkedList

fun main() {
    val input =
        File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText().split("\n")
            .map {
                it.split(",").let { Point(it[1].toInt(), it[0].toInt()) }
            }
    (1..input.size).forEach {
        val finalInput = input.dropLast(it).toSet()
        val max = 70
        val range = 0..max
        val start = Point(0, 0)
        val end = Point(max, max)
        val visited = mutableSetOf<Point>()
        val queue = LinkedList<Pair<Point, Int>>()
        val diff = listOf(Point(1, 0), Point(-1, 0), Point(0, 1), Point(0, -1))
        queue.offer(start to 1)
        visited.add(start)
        var result: Int? = null
        while (queue.isNotEmpty()) {
            val top = queue.poll()
            if (top.first == end) {
                result = top.second
                break
            }
            diff.map {
                top.first.copy(x = top.first.x + it.x, y = top.first.y + it.y) to top.second
            }.filter {
                it.first.x in range && it.first.y in range && !visited.contains(it.first) && !finalInput.contains(it.first)
            }.forEach {
                visited.add(it.first)
                queue.offer(it.copy(second = it.second + 1))
            }
        }
        if (result == null) {
            println(finalInput.last())
        } else  return

    }
}