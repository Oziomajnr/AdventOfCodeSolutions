package year_2022.day11


import solve
import java.math.BigInteger

fun main() = solve { lines ->
    val monkeys = parseInput(lines)
    repeat(10000) {
        println("Round: $it")
        monkeys.forEach { monkey ->
            monkey.items.forEach { item ->
                val newValue = if (monkey.operation.operator == "*") {
                    item * (monkey.operation.value ?: item)
                } else {
                    item + (monkey.operation.value ?: item)
                }
                if (newValue.divisibleBy(monkey.testValue)) {
                    monkeys[monkey.throwToMonkeyOnTrue].items.add(newValue)
                } else {
                    monkeys[monkey.throwToMonkeyOnFalse].items.add(newValue)
                }
                monkey.numberOfInspections++
            }
            monkey.items.clear()
        }
    }
    println(monkeys)

    monkeys.sortedBy {
        it.numberOfInspections
    }.takeLast(2).run {
        get(0).numberOfInspections * get(1).numberOfInspections
    }
}

fun BigInteger.divisibleBy(divisor: Int): Boolean {
    if (this.toString().length <= 2) return this % divisor.toBigInteger() == BigInteger.ZERO
    val result = when (divisor) {
        5 -> {
            this.toString().last().digitToInt() == 5 || this.toString().last().digitToInt() == 0
        }
//
//        7 -> {
//
//        }

        13 -> {
            val stringValue = this.toString()
            ((stringValue.last().digitToInt() * 4) + stringValue.dropLast(1).map { it.digitToInt() }.sum()) % 13 == 0
        }

//        11 -> {
//
//        }
//
//        3 -> {
//
//        }

        2 -> {
            this.toString().last().digitToInt() % 2 == 0
        }

        17 -> {
            val stringValue = this.toString()
            (stringValue.dropLast(1).map { it.digitToInt() }.sum() - (stringValue.last().digitToInt() * 4)) % 17 == 0
        }

        19 -> {
            var stringValue = this.toString()
            while (stringValue.length > 2) {
                stringValue = ((stringValue.last().digitToInt() * 2) + stringValue.dropLast(1).map { it.digitToInt() }
                    .sum()).toString()
            }
            stringValue.toInt() % 19 == 0

        }

        23 -> {
            val stringValue = this.toString()
            ((stringValue.last().digitToInt() * 4) + stringValue.dropLast(1).map { it.digitToInt() }.sum()) % 23 == 0
        }

        else -> {
            error(" Invalid input ")
        }
    }
    return result
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

fun parseInput(lines: List<String>): List<Monkey> {
    return lines.joinToString("\n").split("\n\n").map {
        it.split("\n").map { it.trimIndent() }.run {
            val operation = this[2].drop(21).split(" ").run {
                year_2022.day11.Operation(this[0], this[1].toBigIntegerOrNull())
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


