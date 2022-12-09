package year_2022.day09

import solve

fun main() = solve { lines ->
    getVisitedPoints(parseInput(lines), buildList {
        repeat(10) {
            add(Position(500, 500))
        }
    })
}