val input = File("src/main/kotlin/com/ozioma/input1.txt").readText()
fun List<String>.rotate(): List<String> {
    val input = this
    val result = mutableListOf<String>()
    for (i in input[0].indices) {
        val line = input.map {
            it[i]
        }.joinToString("")
        result.add(line)
    }
    return result

}

fun main() {
    val inputLines = input.trim().split("\n")
    val lines = inputLines.mapIndexed { x, line ->
        line.mapIndexed { y, c ->
            Cell(x, y).also {
                it.value = c
            }
        }
    }

    inputLines.forEachIndexed { x, s ->
        if (!s.contains('#')) {
            s.forEachIndexed { y, c ->
                lines[x][y].weight = 1000000
            }
        }
    }

    inputLines.rotate().forEachIndexed { x, s ->
        if (!s.contains('#')) {
            s.forEachIndexed { y, c ->
                lines[y][x].weight = 1000000
            }
        }
    }

    val startingPoints = lines.flatten().filter { inputLines[it.x][it.y] == '#' }
    println(startingPoints.sumOf {
        findShortestDistanceToAllCells(it, lines)
    })
}

val oldCells = mutableSetOf<Cell>()

fun findShortestDistanceToAllCells(startingCell: Cell, grid: List<List<Cell>>): Long {
    val queue = LinkedList<Pair<Cell, Int>>()
    val visited = mutableSetOf<Cell>()
    queue.offer(startingCell to 0)
    var result = 0L
    oldCells.add(startingCell)
    while (queue.isNotEmpty()) {
        val entry = queue.poll()
        val cell = entry.first
        if (grid[cell.x][cell.y].value == '#'
            && !oldCells.contains(cell)
        ) {
            result += entry.second
        }
        listOf(
            Cell(cell.x - 1, cell.y),
            Cell(cell.x + 1, cell.y),
            Cell(cell.x, cell.y - 1),
            Cell(cell.x, cell.y + 1)
        ).filter { it.x >= 0 && it.x <= grid.lastIndex && it.y >= 0 && it.y <= grid[0].lastIndex && !visited.contains(it)
        }.forEach {
            visited.add(it)
            queue.offer(it to (entry.second + grid[it.x][it.y].weight))
        }
    }
    return result;
}

data class Cell(val x: Int, val y: Int) {
    var weight = 1
    var value = '.'
}
