package `2024`

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("input.txt").readText()
    val lines = input.split("\n").map {
      val (a, b) =   it.split(Regex("\\s+")).map { it.toLong() }
        a to b
    }
   println( lines.map { it.second }.sorted().zip(lines.map { it.first }.sorted()).map { abs(it.first - it.second) }.sum())//pt 1
    val map = lines.map { it.second }.groupBy { it }.mapValues { it.value.size }
   println( lines.map { it.first }.map {  (map[it] ?: 0) * it}.sum())//pt 2
}