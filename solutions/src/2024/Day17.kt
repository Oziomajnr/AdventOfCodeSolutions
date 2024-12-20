package `2024`

import java.io.File
import java.lang.StringBuilder
import kotlin.math.pow

fun main() {
    (1000000000000000L..10000000000000000L step 8).forEach { aMin ->
        val input =
            Regex("\\d+").findAll(File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText())
                .toList().map { it.value }.map { it.toLong() }
        var registerA = aMin
        var registerB = input[1]
        var registerC = input[2]
        val program = input.drop(3)
        val programString = input.drop(3).joinToString("")
        var pointer = 0
        val result = StringBuilder()
        var rr = mutableListOf<String>()

        fun getComboOperandValue(value: Long): Long {
            return when (value) {
                in 0..3 -> value
                4L -> registerA
                5L -> registerB
                6L -> registerC
                else -> error("")
            }
        }
//        2,4,1,5,7,5,1,6,4,1,5,5,0,3,3,0
        while (pointer in program.indices) {
            when (program[pointer]) {
                0L -> {
                    registerA /= 2.0.pow(getComboOperandValue(program[pointer + 1]).toDouble()).toLong()
                    pointer += 2
                }

                1L -> {
                    registerB = registerB xor program[pointer + 1]
                    pointer += 2
                }

                2L -> {
                    registerB = getComboOperandValue(program[pointer + 1]) % 8
                    pointer += 2
                }

                3L -> {
                    if (registerA != 0L) pointer = program[pointer + 1].toInt() else pointer += 2
                }

                4L -> {
                    registerB = registerB xor registerC
                    pointer += 2
                }

                5L -> {
                    result.append(getComboOperandValue(program[pointer + 1]) % 8).toString()
                    rr += (getComboOperandValue(program[pointer + 1]) % 8).toString()
                    pointer += if (result.length > programString.length
                        || (result.length == programString.length && result.toString() != programString)
                        || (0..result.lastIndex).any { result[it] != programString[it] })
                        break
                    else 2
                }

                6L -> {
                    registerB = registerA / 2.0.pow(getComboOperandValue(program[pointer + 1]).toDouble()).toLong()
                    pointer += 2
                }

                7L -> {
                    registerC = registerA / 2.0.pow(getComboOperandValue(program[pointer + 1]).toDouble()).toLong()
                    pointer += 2
                }

                else -> error("")
            }
        }
        if(result.length > 7)
        { println("$aMin $result $rr") }
        if (programString == result.toString()) {
            println(aMin)
            return
        }
//        println(result.map { it.toString().toCharArray().toList() }.flatten().joinToString(","))
    }

}




