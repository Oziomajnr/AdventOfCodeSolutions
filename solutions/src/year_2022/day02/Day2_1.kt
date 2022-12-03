package year_2022.day02

import solve

//Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock
fun main() = solve { lines ->
    val scoreMap = mapOf(
        'A' to RockPaperSci.Rock,
        'B' to RockPaperSci.Paper,
        'C' to RockPaperSci.Sci,
        'X' to RockPaperSci.Rock,
        'Y' to RockPaperSci.Paper,
        'Z' to RockPaperSci.Sci
    )
    lines.map {
        it.split(' ').map {
            scoreMap[it[0]]!!
        }
    }.sumOf { value ->
        value[1] + getMyScore(value[1], value[0])
    }
}

//return true if I won, false if I lost and  null for draw.
private fun getMyScore(a: Int, b: Int): Int {
    val list = listOf(a, b).sorted()
    if (list[0] == RockPaperSci.Rock && list[1] == RockPaperSci.Paper) {
        return getScoreFromResult(RockPaperSci.Paper == a)
    } else if (list[0] == RockPaperSci.Rock && list[1] == RockPaperSci.Sci) {
        return getScoreFromResult(RockPaperSci.Rock == a)
    } else if (list[0] == RockPaperSci.Paper && list[1] == RockPaperSci.Sci) {
        return getScoreFromResult(RockPaperSci.Sci == a)
    } else if (a == b) {
        return 3
    }
    error("invalid case")
}

private fun getScoreFromResult(result: Boolean?): Int {
    return when (result) {
        true -> 6
        false -> 0
        null -> 3
    }
}

object RockPaperSci {
    const val Rock = 1
    const val Paper = 2
    const val Sci = 3
}




