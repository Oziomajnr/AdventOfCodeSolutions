//package year_2022.day11
//
//import solve
//
//fun main() = solve { lines ->
//    val monkeys = parseInput(lines)
//    repeat(10000) {
//        monkeys.forEach { monkey ->
//            monkey.items.forEach { item ->
//                val newValue = (if (monkey.operation.operator == "*") {
//                    item * (monkey.operation.value ?: item)
//                } else {
//                    item + (monkey.operation.value ?: item)
//                }) / 3
//                if (newValue % monkey.testValue == 0L) {
//                    monkeys[monkey.throwToMonkeyOnTrue].items.add(newValue)
//                } else {
//                    monkeys[monkey.throwToMonkeyOnFalse].items.add(newValue)
//                }
//                monkey.numberOfInspections++
//            }
//            monkey.items.clear()
//        }
//
//    }
//
//    monkeys.sortedBy {
//        it.numberOfInspections
//    }.takeLast(2).run {
//        get(0).numberOfInspections * get(1).numberOfInspections
//    }
//}
//
//private fun parseInput(lines: List<String>): List<Monkey> {
//    return lines.joinToString("\n").split("\n\n").map {
//        it.split("\n").map { it.trimIndent() }.run {
//            val operation = this[2].drop(21).split(" ").run {
//                Operation(this[0], this[1].toLongOrNull())
//            }
//            val id = this[0].drop(7).dropLast(1).toInt()
//            val testValue = this[3].drop(19).toLong()
//            val throwToMonkeyOnTrue = this[4].drop(25).toInt()
//            val throwToMonkeyOnFalse = this[5].drop(26).toInt()
//            val items = this[1].drop(16).split(", ").map { it.toLong() }
//            Monkey(id, operation, testValue, throwToMonkeyOnTrue, throwToMonkeyOnFalse).apply {
//                this.items.addAll(items)
//            }
//        }
//    }
//}
//
//private data class Monkey(
//    val id: Int,
//    val operation: Operation,
//    val testValue: Long,
//    val throwToMonkeyOnTrue: Int,
//    val throwToMonkeyOnFalse: Int
//) {
//    var numberOfInspections = 0
//    val items = mutableListOf<Long>()
//}
//
//private data class Operation(val operator: String, val value: Long?)
//
