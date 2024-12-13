package `2024`

import common.Direction
import java.io.File

fun main() {
    val input = File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText()
    val lines = input.split("\n")
    val newObstacles = lines.mapIndexed { x, s -> s.mapIndexed{y, c ->  if(lines[x][y] == '.') x to y else null}.filterNotNull() }.flatten()
    val result = newObstacles.count { emptySlot->
        val newLines = lines.mapIndexed { x, line ->
            line.mapIndexed { y, c ->
                if(x to y == emptySlot.first to emptySlot.second){
                    '#'
                } else c
            }
        }
        var initialPosition =
            newLines.mapIndexed { index, s -> if (s.contains('^')) index to s.indexOf('^') else null }.filterNotNull().single()
        var initialDirection = Direction.Up
        val nextDirectionMap = mapOf(
            Direction.Up to Direction.Right,
            Direction.Down to Direction.Left,
            Direction.Left to Direction.Up,
            Direction.Right to Direction.Down
        )
        val visited = mutableSetOf(initialPosition)
        fun getNextPosition (position: Pair<Int, Int>, direction: Direction) = when (direction) {
            Direction.Up -> position.first - 1 to position.second
            Direction.Down -> position.first + 1 to position.second
            Direction.Left -> position.first to position.second - 1
            Direction.Right -> position.first to position.second + 1
        }
        val visitedLoop = mutableSetOf<Pair<Direction , Pair<Int, Int>>>()

        fun Pair<Int, Int>.withinBounds() =
            first >= 0 && second >= 0 && first <= newLines.lastIndex && second <= newLines[0].lastIndex


        while(getNextPosition(initialPosition, initialDirection).withinBounds()){
            val xxx = getNextPosition(initialPosition, initialDirection)
            if(newLines[xxx.first][xxx.second] == '#') {
                initialDirection = nextDirectionMap[initialDirection]!!
            } else {
                visited.add(getNextPosition(initialPosition, initialDirection))
                initialPosition = getNextPosition(initialPosition, initialDirection)
            }
            if(visitedLoop.contains(initialDirection to  initialPosition)){
                return@count true
            }
            visitedLoop.add(initialDirection to  initialPosition)
        }
        return@count false
    }
   println(result)
}