package year_2022.day05

import solve
import java.util.LinkedList
import java.util.Queue
import java.util.Stack

data class Instruction(val source: Int, val destination: Int, val amount: Int)

fun main() = solve { lines ->
    parseInput(lines).apply {
        second.forEach { instruction ->
            (1..instruction.amount).forEach {
                first[instruction.destination - 1].push(first[instruction.source - 1].pop())
            }
        }
        first.forEach {
            if (it.isNotEmpty()) print(it.peek()[1])
        }
    }
}

fun parseInput(lines: List<String>): Pair<List<Stack<String>>, List<Instruction>> {
    val (stacks, instructionString) = lines.joinToString("\n").split("\n\n")
    return List(10) { LinkedList<String>() }.apply {
        stacks.split("\n").take(stacks.split("\n").size - 1).forEach { value ->
            value.chunked(4).forEachIndexed { index, s ->
                if (s.isNotBlank()) {
                    this[index].add(s)
                }
            }
        }
    }.mapIndexed { index, strings ->
        val stack = Stack<String>()
        strings.reversed().forEach {
            stack.push(it)
        }
        stack
    } to instructionString.split("\n").map {
        it.split(" ").mapNotNull {
            it.toIntOrNull()
        }
    }.map {
        Instruction(it[1], it[2], it[0])
    }
}


