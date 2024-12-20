package `2024`

import java.io.File
import java.math.BigDecimal
import java.math.BigInteger

fun main() {
        File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText().split("\n\n")
            .map {
                val x = it.split("\n")
                val buttonA = Regex("\\d+").findAll(x[0]).toList().map { it.value.toInt() }.run {
                    Button("A", this[0], this[1])
                }
                val buttonB = Regex("\\d+").findAll(x[1]).toList().map { it.value.toInt() }.run {
                    Button("A", this[0], this[1])
                }
                val prize = Regex("\\d+").findAll(x[2]).toList().map { it.value.toInt() }.run {
                    Prize(
                        this[0].toBigInteger() + 10000000000000.toBigInteger(),
                        this[1].toBigInteger() + 10000000000000.toBigInteger()
                    )
                }
                val k = solveSimultaneousEquations(
                    a1 = buttonA.xDiff.toBigDecimal(),
                    b1 = buttonB.xDiff.toBigDecimal(),
                    c1 = prize.x.toBigDecimal(),
                    a2 = buttonA.yDiff.toBigDecimal(),
                    b2 = buttonB.yDiff.toBigDecimal(),
                    c2 = prize.y.toBigDecimal()
                )
                k
            }.sumOf {
                if (it.x.isWholeNumber() && it.y.isWholeNumber()) (it.x * 3.toBigDecimal() + it.y).toBigInteger() else BigInteger.ZERO
            }.also { println(it) }


}

fun BigDecimal.isWholeNumber() = stripTrailingZeros().scale() <= 0

data class Solution(val x: BigDecimal, val y: BigDecimal)

fun solveSimultaneousEquations(
    a1: BigDecimal, b1: BigDecimal, c1: BigDecimal,
    a2: BigDecimal, b2: BigDecimal, c2: BigDecimal
): Solution {
    val determinant = a1 * b2 - a2 * b1
    val x = (c1 * b2 - c2 * b1).divide(determinant, 3, BigDecimal.ROUND_HALF_UP)
    val y = (a1 * c2 - a2 * c1).divide(determinant, 3, BigDecimal.ROUND_HALF_UP)
    return Solution(x, y)
}

data class Prize(val x: BigInteger, val y: BigInteger)
data class Button(val name: String, val xDiff: Int, val yDiff: Int)