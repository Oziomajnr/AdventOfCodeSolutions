package `2024`

import java.io.File

fun main() {
    val input = File("input.txt").readText()
    println(input.split("\n").map {
        Regex("(mul\\(\\d+,\\d+\\))").findAll(it).map { it.value }.sumOf {
            Regex("\\d+").findAll(it).map { it.value.toInt() }.reduce { acc, i -> acc * i }
        }
    }.sum()) //pt 1
    fun eval(index: Int, input: List<String>, currentSum: Long, shouldAdd: Boolean): Long {
        if (index == input.size) return currentSum
        return when (input[index]) {
            "do()" -> eval(index + 1, input, currentSum, true)
            "don't()" -> eval(index + 1, input, currentSum, false)
            else -> {
                val value = Regex("\\d+").findAll(input[index]).map { it.value.toInt() }.reduce { acc, i -> acc * i }
                eval(index + 1, input, if (shouldAdd) currentSum + value else currentSum, shouldAdd)
            }
        }
    }
    println(
        eval(0, Regex("(mul\\(\\d+,\\d+\\))|(do\\(\\))|(don't\\(\\))").findAll(input).map { it.value }.toList(), 0, true)
    ) //pt2
}

