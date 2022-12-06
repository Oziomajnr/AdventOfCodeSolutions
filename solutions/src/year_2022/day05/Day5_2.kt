package year_2022.day05

import solve
import java.util.*

fun main() = solve { lines ->
    parseInput(lines).apply {
        second.forEach { instruction ->
            val temp = mutableListOf<String>()
            (1..instruction.amount).forEach {
                temp.add(first[instruction.source - 1].pop())
            }
            temp.reversed().forEach {
                first[instruction.destination - 1].push(it)
            }
        }
        first.forEach {
            if (it.isNotEmpty()) print(it.peek()[1])
        }
    }
}
