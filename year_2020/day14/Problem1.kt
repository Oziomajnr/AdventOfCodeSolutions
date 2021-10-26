package year_2020.day14

import java.io.File

class Main {
    /**
     * You can edit, run, and share this code.
     * play.kotlinlang.org
     */
    private val bitManipulator = BitManipulator()
    fun main() {
        val inputParser = InputParser()
        val inputList = readFileAsLinesUsingUseLines().map {
            inputParser.toInput(it)
        }
        val memoryAddress = mutableMapOf<Long, Long>()
        lateinit var currentMask: String
        inputList.forEach {
            if (it is Input.MaskedInput) {
                currentMask = it.mask
            } else if (it is Input.MemoryInput) {
                memoryAddress[it.memoryAddress] = maskNumber(currentMask, it.value)
            }
        }
        println(memoryAddress.map {
            it.value
        }.sum())
    }

    fun maskNumber(mask: String, input: Long): Long {
        var number = input
        mask.forEachIndexed { index, value ->
            when {
                value == '0' -> {
                    number = bitManipulator.clearBitAtPosition(number, mask.lastIndex - index)
                }
                value == '1' -> {
                    number = bitManipulator.setBitAtPosition(number, mask.lastIndex - index)
                }
                value != 'X' -> {
                    error("Invalid data")
                }
            }
        }
        return number
    }

    private fun readFileAsLinesUsingUseLines(): List<String> =
        File("/Users/oogbe/IdeaProjects/AOC14/src/main/kotlin/input.txt").useLines { data ->
            data.toList()
        }
}

class InputParser {
    private val maskRegex = "^mask.*".toRegex()
    private val memRegex = "^mem.*".toRegex()

    fun toInput(input: String): Input {
        return if (input.matches(maskRegex)) {
            parseAsMask(input)
        } else if (input.matches(memRegex)) {
            parseAsMem(input)
        } else {
            error("Invalid input $input")
        }
    }

    private fun parseAsMask(input: String): Input.MaskedInput {
        return Input.MaskedInput(input.split(" = ")[1])
    }

    private fun parseAsMem(input: String): Input.MemoryInput {
        val separatedInput = input.split(" = ")
        val value = separatedInput[1].toLong()
        val memValueRegex = "mem\\[(\\d+)]".toRegex()
        val memoryAddress =
            memValueRegex.find(separatedInput.first())!!.destructured.component1().toLong()
        return Input.MemoryInput(memoryAddress, value)
    }

}

sealed class Input {
    class MaskedInput(val mask: String) : Input()
    class MemoryInput(val memoryAddress: Long, val value: Long) : Input()
}
