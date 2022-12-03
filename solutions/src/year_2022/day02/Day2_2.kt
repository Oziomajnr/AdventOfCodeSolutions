package year_2022.day02

import solve

fun main() = solve { lines ->
    val myTotalScore = lines.map {
        val res = it.split(' ')
        Pair(scoreMap1[res[0][0]]!!, expectedResultMap[res[1][0]]!!)
    }.sumOf { value ->
        challenge(value.first, value.second)
    }
    myTotalScore
}

//Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock
private fun challenge(a: Int, expectedResult: Result): Int {
    return when (a) {
        RockPaperSci.Rock -> {
            when(expectedResult) {
                Result.Win -> RockPaperSci.Paper + 6
                Result.Lose -> RockPaperSci.Sci
                Result.Draw -> RockPaperSci.Rock + 3
            }
        }
        RockPaperSci.Paper -> {
            when(expectedResult) {
                Result.Win -> RockPaperSci.Sci + 6
                Result.Lose -> RockPaperSci.Rock
                Result.Draw -> RockPaperSci.Paper + 3
            }
        }
        RockPaperSci.Sci -> {
            when(expectedResult) {
                Result.Win -> RockPaperSci.Rock + 6
                Result.Lose -> RockPaperSci.Paper
                Result.Draw -> RockPaperSci.Sci + 3
            }
        }
        else -> error("")
    }
}

val scoreMap1 = mapOf(
    'A' to RockPaperSci.Rock,
    'B' to RockPaperSci.Paper,
    'C' to RockPaperSci.Sci,

)
val expectedResultMap = mapOf(
    'X' to Result.Lose,
    'Y' to Result.Draw,
    'Z' to Result.Win
)

enum class Result {
    Win, Lose, Draw
}




