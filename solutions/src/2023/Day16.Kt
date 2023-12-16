fun main() {
    val lines = input.trim().split("\n")
    measureTimeMillis {
        (lines[0].mapIndexed { index, c ->
            Point(0, index) to Direction.DOWN
        } + lines.last().mapIndexed { index, c ->
            Point(lines.lastIndex, index) to Direction.UP
        } + lines.mapIndexed { index, s ->
            Point(index, 0) to Direction.RIGHT
        } + List(lines.size) { index ->
            Point(index, lines[0].lastIndex) to Direction.LEFT
        }).maxOf {
            val r = calculateBestBeam(it.first, it.second, lines)
            visited.clear()
            r
        }.let(::println)
    }.let(::println)
}

fun calculateBestBeam(startPoint: Point, direction: Direction, lines: List<String>): Int {
    visited.add(startPoint to direction)
    calculateEnergizedBeams(
        lines,
        startPoint,
        direction
    )
    return visited.map { it.first }.toSet().size
}

val visited: MutableSet<Pair<Point, Direction>> = mutableSetOf()

fun calculateEnergizedBeams(
    lines: List<String>,
    currentPosition: Point,
    direction: Direction
) {
    visited.add(currentPosition to direction)
    val nextResults = getNextPosition(lines, currentPosition, direction).filter { !visited.contains(it) }
    if (nextResults.isEmpty()) {
        return
    }
    nextResults.forEach {
        calculateEnergizedBeams(
            lines,
            it.first,
            it.second
        )
    }
}

fun getNextPosition(
    lines: List<String>,
    currentPosition: Point,
    direction: Direction
): Set<Pair<Point, Direction>> {
    return when (direction) {
        Direction.UP -> {
            when (lines[currentPosition.x][currentPosition.y]) {
                '|', '.' -> setOf(Point(currentPosition.x - 1, currentPosition.y) to Direction.UP)
                '-' -> setOf(
                    Point(currentPosition.x, currentPosition.y - 1) to Direction.LEFT,
                    Point(currentPosition.x, currentPosition.y + 1) to Direction.RIGHT
                )

                '\\' -> setOf(Point(currentPosition.x, currentPosition.y - 1) to Direction.LEFT)

                '/' -> setOf(Point(currentPosition.x, currentPosition.y + 1) to Direction.RIGHT)

                else -> error("Invalid character")
            }
        }

        Direction.DOWN -> {
            when (lines[currentPosition.x][currentPosition.y]) {
                '|', '.' -> setOf(Point(currentPosition.x + 1, currentPosition.y) to Direction.DOWN)
                '-' -> setOf(
                    Point(currentPosition.x, currentPosition.y - 1) to Direction.LEFT,
                    Point(currentPosition.x, currentPosition.y + 1) to Direction.RIGHT
                )

                '\\' -> setOf(Point(currentPosition.x, currentPosition.y + 1) to Direction.RIGHT)
                '/' -> setOf(Point(currentPosition.x, currentPosition.y - 1) to Direction.LEFT)

                else -> error("Invalid character")
            }
        }

        Direction.LEFT -> {
            when (lines[currentPosition.x][currentPosition.y]) {
                '|' -> setOf(
                    Point(currentPosition.x - 1, currentPosition.y) to Direction.UP,
                    Point(currentPosition.x + 1, currentPosition.y) to Direction.DOWN
                )

                '-', '.' -> setOf(Point(currentPosition.x, currentPosition.y - 1) to Direction.LEFT)
                '\\' -> setOf(Point(currentPosition.x - 1, currentPosition.y) to Direction.UP)
                '/' -> setOf(Point(currentPosition.x + 1, currentPosition.y) to Direction.DOWN)
                else -> error("Invalid character")
            }
        }

        Direction.RIGHT -> {
            when (lines[currentPosition.x][currentPosition.y]) {
                '|' -> setOf(
                    Point(currentPosition.x - 1, currentPosition.y) to Direction.UP,
                    Point(currentPosition.x + 1, currentPosition.y) to Direction.DOWN
                )

                '-', '.' -> setOf(Point(currentPosition.x, currentPosition.y + 1) to Direction.RIGHT)
                '\\' -> setOf(Point(currentPosition.x + 1, currentPosition.y) to Direction.DOWN)
                '/' -> setOf(Point(currentPosition.x - 1, currentPosition.y) to Direction.UP)
                else -> error("Invalid character")
            }
        }
    }.filter {
        it.first.x in lines.indices && it.first.y in lines[0].indices
    }.toSet()
}

data class Point(val x: Int, val y: Int)
enum class Direction {
    UP, DOWN, LEFT, RIGHT
}
