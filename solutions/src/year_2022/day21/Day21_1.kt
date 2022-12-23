package year_2022.day21

import solve

fun main() = solve { lines ->
    val monkeys = parseInput(lines)
    findRootMonkeyNumber(emptyMap(), monkeys.single {
        it.name == "root"
    })
}

fun findRootMonkeyNumber(cache: Map<Monkey, Long> = emptyMap(), rootMonkey: Monkey, print: Boolean = false): Long {
    return if (rootMonkey.monkeyOutput is MonkeyOutput.Number) {
        (rootMonkey.monkeyOutput as MonkeyOutput.Number).value
    } else {
        val output = (rootMonkey.monkeyOutput as MonkeyOutput.Operation)
        val monkey1 = output.monkey1
        val monkey2 = output.monkey2
        val monkey1Value = cache[monkey1]
        val monkey2Value = cache[monkey2]
        return when (output.operator) {
            Operator.Add -> {
                if (print)
                    println("$monkey1 + $monkey2")
                (monkey1Value ?: findRootMonkeyNumber(cache, monkey1)) + (monkey2Value ?: findRootMonkeyNumber(
                    cache,
                    monkey2
                ))
            }

            Operator.Subtract -> {
                if (print)
                    println("$monkey1 - $monkey2")
                (monkey1Value ?: findRootMonkeyNumber(cache, monkey1)) - (monkey2Value ?: findRootMonkeyNumber(
                    cache,
                    monkey2
                ))
            }

            Operator.Divide -> {
                if (print)
                    println("$monkey1 / $monkey2")
                (monkey1Value ?: findRootMonkeyNumber(cache, monkey1)) / (monkey2Value ?: findRootMonkeyNumber(
                    cache,
                    monkey2
                ))
            }

            Operator.Multiply -> {
                if (print)
                    println("$monkey1 * $monkey2")
                (monkey1Value ?: findRootMonkeyNumber(cache, monkey1)) * (monkey2Value ?: findRootMonkeyNumber(
                    cache,
                    monkey2
                ))
            }

            Operator.Equal -> error("Not expecting equals in input")
        }
    }
}

fun getOperator(sign: String): Operator {
    return when (sign) {
        "+" -> {
            Operator.Add
        }

        "-" -> {
            Operator.Subtract
        }

        "/" -> {
            Operator.Divide
        }

        "*" -> {
            Operator.Multiply
        }

        else -> {
            error("Invalid sign")
        }
    }
}

data class Monkey(val name: String) {
    var monkeyOutput: MonkeyOutput = MonkeyOutput.Number(1)

    override fun toString(): String {
        return name
    }
}

sealed interface MonkeyOutput {
    data class Number(val value: Long) : MonkeyOutput {
        override fun toString(): String {
            if (value == -1L) return "J"
            return value.toString()
        }
    }

    data class Operation(val operator: Operator, val monkey1: Monkey, val monkey2: Monkey) : MonkeyOutput {
        override fun toString(): String {
            return "(${monkey1.monkeyOutput} ${operator.operatorTitle} ${monkey2.monkeyOutput}) "
        }
    }
}

enum class Operator(val operatorTitle: String) {
    Add("+"), Subtract("-"), Divide("/"), Multiply("*"), Equal("=")
}
