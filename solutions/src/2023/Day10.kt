package com.ozioma

import java.util.*

//Theorem https://en.wikipedia.org/wiki/Point_in_polygon

fun main() {
    val lines = input.trim().split("\n")
    val startCell = lines.mapIndexed { index, s ->
        if (s.contains('S')) {
            index
        } else {
            -1
        }
    }.filter { it != -1 }.map { Pair(it, lines[it].indexOf('S')) }.first()
    val newLines = lines.map {
        if (it.contains('S')) it.replace('S', '|') else it
    }

    val pipe = newLines.mapIndexed { x, line ->
        line.mapIndexed { y, it ->
            when (it) {
                '|' -> Pipe('N' to 'S', x, y)
                '-' -> Pipe('W' to 'E', x, y)
                'L' -> Pipe('N' to 'E', x, y)
                'J' -> Pipe('N' to 'W', x, y)
                '7' -> Pipe('W' to 'S', x, y)
                'F' -> Pipe('S' to 'E', x, y)
                '.' -> Pipe(' ' to ' ', x, y)
                else -> error("Unknown pipe $it")
            }
        }
    }

    val startPipe = pipe[startCell.first][startCell.second]
    val stack = Stack<State>()
    stack.push(State(pipe, startPipe, startCell))
    val visited = mutableSetOf<CacheValue>()
    while (stack.isNotEmpty()) {
        val state = stack.pop()
        if (state.currentPipe == state.grid[startCell.first][startCell.second] && visited.size > 0) {
            println("Part 1 solution ${visited.size / 2}")
            break
        }
        listOf(
            (state.currentPipe.x - 1 to state.currentPipe.y) to 'N',
            (state.currentPipe.x + 1 to state.currentPipe.y) to 'S',
            (state.currentPipe.x to state.currentPipe.y - 1) to 'W',
            (state.currentPipe.x to state.currentPipe.y + 1) to 'E'
        ).first {
            canMoveToCell(
                state.currentPipe.x to state.currentPipe.y,
                it.first,
                state.grid,
                it.second,
                visited
            )
        }.also {
            visited.add(CacheValue(state.currentPipe.x to state.currentPipe.y, !it.second))
            stack.push(State(state.grid, state.grid[it.first.first][it.first.second], startCell))
        }
    }

    val visitedCells = visited.map {
        it.cell
    }

    val validPattern = "|LF7J".toSet()
    val allTiles = newLines.mapIndexed { x, value ->
        value.mapIndexed { y, c ->
            Pair(x, y)
        }.filter {
            !visitedCells.contains(it.first to it.second)
        }
    }.flatten().filter {
        Regex("(L7|FJ|\\|)").findAll((it.second..newLines[0].lastIndex).filter { k ->
            visitedCells.contains(it.first to k) && (validPattern.contains(newLines[it.first][k]))
        }.map { k ->
            newLines[it.first][k]
        }.joinToString("")).count() % 2 != 0

    }.toSet()

    println("Part 2 Solution ${allTiles.size}")
}

data class State(val grid: List<List<Pipe>>, val currentPipe: Pipe, val startCell: Pair<Int, Int>)
data class CacheValue(val cell: Pair<Int, Int>, val direction: Char)

operator fun Char.not() = when (this) {
    'N' -> 'S'
    'S' -> 'N'
    'E' -> 'W'
    'W' -> 'E'
    else -> error("Unknown direction $this")
}

fun canMoveToCell(
    originCell: Pair<Int, Int>,
    destinationCell: Pair<Int, Int>,
    grid: List<List<Pipe>>,
    direction: Char,
    visited: Set<CacheValue>
) = destinationCell.first >= 0 && destinationCell.first <= grid.lastIndex &&
        destinationCell.second >= 0 && destinationCell.second < grid[0].size &&
        !visited.contains(CacheValue(destinationCell, direction)) &&
        grid[originCell.first][originCell.second].poles.toList().contains(direction) &&
        grid[destinationCell.first][destinationCell.second].poles.toList().contains(!direction)

data class Pipe(val poles: Pair<Char, Char>, val x: Int, val y: Int)
