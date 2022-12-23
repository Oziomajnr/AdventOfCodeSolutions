package year_2022.day21

import solve

fun main() = solve { lines ->
    val monkeys = parseInput(lines)
    println((monkeys.single {
        it.name == "lntp"
    }.monkeyOutput).toString().filter { !it.isWhitespace() })
//    monkeys.forEach {
//        monkeyValues[it] = findRootMonkeyNumber(monkeyValues, it, false)
//    }
    val x2 = findRootMonkeyNumber(emptyMap(), monkeys.single {
        it.name == "bjft"
    })
//    val jMonkeyNumber = findRootMonkeyNumber(emptyMap(), monkeys.single {
//        it.name == "humn"
//    })
    var lowerBound = Long.MAX_VALUE
    var higherBound = Long.MAX_VALUE
    x2
//   for(x in 1200000000000..1200000000010L) {
//
//        println(x)
//        monkeys.single {
//            it.name == "humn"
//        }.apply {
//            this.monkeyOutput = MonkeyOutput.Number(x)
//            monkeyValues[this] = x
//        }


//       val rootResult = findRootMonkeyNumber(emptyMap(), monkeys.single {
//            it.name == "root"
//        })
//
//        val pppwResult = findRootMonkeyNumber(emptyMap(), monkeys.single {
//            it.name == "lntp"
//        })
//
//        val sjmnResult = findRootMonkeyNumber(emptyMap(), monkeys.single {
//            it.name == "bjft"
//        })
//        println("When x = $x root is $rootResult pppwResult is $pppwResult sjmnResult is $sjmnResult")

//       val rootResult = findRootMonkeyNumber(emptyMap(), monkeys.single {
//            it.name == "root"
//        })
//        val x1 = findRootMonkeyNumber(emptyMap(), monkeys.single {
//            it.name == "lntp"
//        })
//
//
//        println("When x = $x root is $rootResult pppwResult is $x1 sjmnResult is $x2")

//        if (x1 < x2) {
//            lowerBound = x + 1
//        } else if (x1 > x2) {
//            higherBound = x - 1
//        } else {
//            println("Solution is $x ")
//            break
//        }
//    }

}


val monkeyValues = mutableMapOf<Monkey, Long>()

fun parseInput(lines: List<String>): Set<Monkey> {
    val monkeys = lines.map {
        Monkey(it.split(": ")[0])
    }.toSet()
    lines.forEach {
        val input = it.split(Regex(": | "))
        val currentMonkey = monkeys.find { monkey -> monkey == Monkey(input[0]) }!!
        if (input[1].toLongOrNull() != null) {
            currentMonkey.monkeyOutput = MonkeyOutput.Number(input[1].toLong())
        } else {
            val operator = getOperator(input[2])
            val monkey1 = monkeys.find { monkey -> monkey == Monkey(input[1]) }!!
            val monkey2 = monkeys.find { monkey -> monkey == Monkey(input[3]) }!!

            currentMonkey.monkeyOutput = MonkeyOutput.Operation(operator, monkey1, monkey2)
        }
    }
    return monkeys
}


private fun isRootMonkeyTrue(rootMonkey: Monkey): Boolean {
    return (rootMonkey.monkeyOutput as MonkeyOutput.Operation).run {
        findRootMonkeyNumber(monkeyValues, this.monkey1) == findRootMonkeyNumber(monkeyValues, this.monkey2)
    }
}