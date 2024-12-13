package `2024`

import java.io.File

fun main() {
    val input = File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText()
    val lines = input.split("\n")
    lines.mapNotNull {
        val (expectedResult, testValues) = it.split(": ")
        val values = testValues.split(" ").map { it.toInt() }
        if(test(expectedResult.toLong(), values, 0, 0L)) expectedResult.toLong() else null
    }.sum().also { println(it) }
}

fun test(expectedResult: Long, testValues: List<Int>, currentIndex: Int, currentResult: Long): Boolean {
    val ans1 = currentResult * testValues[currentIndex]
    val ans2 = currentResult + testValues[currentIndex]
    val ans3 = (currentResult.toString() + testValues[currentIndex]).toLong()
    if (currentIndex == testValues.lastIndex)
        return ans1 == expectedResult || ans2 == expectedResult || ans3 == expectedResult
    return test(expectedResult, testValues, currentIndex + 1, ans1) || test(
        expectedResult,
        testValues,
        currentIndex + 1,
        ans2
    ) || test(
        expectedResult,
        testValues,
        currentIndex + 1,
        ans3
    )
}