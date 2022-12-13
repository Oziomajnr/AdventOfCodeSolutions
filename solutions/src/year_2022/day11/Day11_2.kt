package year_2022.day11


import solve
import java.math.BigInteger

fun main() = solve { lines ->
    getMonkeyBusiness(lines, 10000, false)
}

data class Monkey(
    val id: Int,
    val operation: Operation,
    val testValue: Int,
    val throwToMonkeyOnTrue: Int,
    val throwToMonkeyOnFalse: Int,
    var numberOfInspections: Int = 0
) {
    val items = mutableListOf<BigInteger>()
}

fun getMonkeyBusiness(lines: List<String>, numberOfRounds: Int, divideBy3: Boolean): Long {
    val monkeys = parseInput(lines)
    repeat(numberOfRounds) {
        monkeys.forEach { monkey ->
            monkey.items.forEach { item ->
                val newStressLevel = (if (monkey.operation.operator == "*") {
                    val product = monkeys.map {
                        it.testValue
                    }.fold(1) { acc, i ->
                        acc * i
                    }
                    item * ((monkey.operation.value ?: item) % product.toBigInteger())

                } else {
                    item + (monkey.operation.value ?: item)
                }) / (if (divideBy3) 3.toBigInteger() else 1.toBigInteger())

                if (newStressLevel % monkey.testValue.toBigInteger() == BigInteger.ZERO) {
                    monkeys[monkey.throwToMonkeyOnTrue].items.add(newStressLevel)
                } else {
                    monkeys[monkey.throwToMonkeyOnFalse].items.add(newStressLevel)
                }
                monkey.numberOfInspections++
            }
            monkey.items.clear()
        }
    }
    println(monkeys)

    return monkeys.sortedBy {
        it.numberOfInspections
    }.takeLast(2).run {
        get(0).numberOfInspections.toLong() * get(1).numberOfInspections.toLong()
    }
}

fun parseInput(lines: List<String>): List<Monkey> {
    return lines.joinToString("\n").split("\n\n").map {
        it.split("\n").map { it.trimIndent() }.run {
            val operation = this[2].drop(21).split(" ").run {
                Operation(this[0], this[1].toBigIntegerOrNull())
            }
            val id = this[0].drop(7).dropLast(1).toInt()
            val testValue = this[3].drop(19).toInt()
            val throwToMonkeyOnTrue = this[4].drop(25).toInt()
            val throwToMonkeyOnFalse = this[5].drop(26).toInt()
            val items = this[1].drop(16).split(", ").map { it.toBigInteger() }
            Monkey(id, operation, testValue, throwToMonkeyOnTrue, throwToMonkeyOnFalse).apply {
                this.items.addAll(items)
            }
        }
    }
}

data class Operation(val operator: String, val value: BigInteger?)


