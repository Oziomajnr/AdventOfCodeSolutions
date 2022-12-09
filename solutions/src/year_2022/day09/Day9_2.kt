package year_2022.day09

import solve

fun main() = solve { lines ->
    getVisitedPoints(parseInput(lines), buildList {
        repeat((1..10).count()) {
            add(Position(500, 500))
        }
    })
}