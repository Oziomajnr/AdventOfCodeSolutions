package `2024`

import java.io.File
import java.util.LinkedList
import kotlin.io.path.Path
import kotlin.io.path.readText


fun main() {
    val input =Path("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText()
    val (firstSection, secondSection) = input.split("\n\n")
    val inputPairs = firstSection.split("\n").map { it.split('|').map { it.toInt() } }
    val inputMap = inputPairs.flatten().toSet().groupBy { it }.mapValues { Input(it.value.single()) }
    if (secondSection.split("\n").any {
            it.split(",").size % 2 == 0
        }) error("Invalid")

    inputPairs.forEach {
        val first = inputMap[it[0]]!!
        val second = inputMap[it[1]]!!
        first.children.add(second)
    }
    inputMap.values.forEach { parent ->
        val visited = mutableSetOf<Input>()
        val queue = LinkedList<Input>()
        queue.addAll(parent.children)
        while (queue.isNotEmpty()) {
            val item = queue.poll()
            visited.add(item)
            item.children.forEach {
                if (!visited.contains(it)) queue.add(it)
            }
        }
    }

    val result = secondSection.split("\n").map { it.split(",").map { it.toInt() } }.map {
        it.sortedWith { a, b ->
            if (inputMap[a]!!.children.contains(inputMap[b])) -1 else 1
        }
    }.zip(secondSection.split("\n").map { it.split(",").map { it.toInt() } })
    println(result.filter { it.first != it.second

    }.map { it.first }.map {  it[it.size / 2] }.sum())
}

data class Input(val value: Int) {
    var weight = Int.MIN_VALUE
    val children = mutableSetOf<Input>()
}