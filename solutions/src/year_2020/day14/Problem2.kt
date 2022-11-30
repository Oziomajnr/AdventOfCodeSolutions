package year_2020.day14

import java.io.File
import kotlin.math.pow

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
                memoryAddress += applyMaskToNumber(currentMask, it.memoryAddress, it.value)
            }
        }
        println(memoryAddress.map {
            it.value
        }.sum())
    }

    private fun applyMaskToNumber(mask: String, input: Long, memValue: Long): Map<Long, Long> {
        var number = input
        val floatingBitMap = mutableListOf<Int>()
        mask.forEachIndexed { index, value ->
            when {
                value == 'X' -> {
                    floatingBitMap.add(index)
                }
                value == '1' -> {
                    number = bitManipulator.setBitAtPosition(number, mask.length - index)
                }
                value != '0' -> {
                    error("Invalid data")
                }
            }
        }
        val addressAsBinaryString = number.toString(2)
        val builder = StringBuilder()
        for (x in 0 until mask.length - addressAsBinaryString.length) {
            builder.append('0')
        }
        builder.append(addressAsBinaryString)
        return appendNumberToAddress(builder.toString(), memValue, floatingBitMap)
    }

    private fun appendNumberToAddress(mask: String, input: Long, floatingIndices: List<Int>): Map<Long, Long> {
        val memoryAddress = mutableMapOf<Long, Long>()
        val maskArray = mask.toCharArray()
        val maxValueOfFloatingIndex = 2.0.pow(floatingIndices.size.toDouble()).toLong()
        for (x in 0 until maxValueOfFloatingIndex) {
            floatingIndices.forEachIndexed { index, value ->
                if (bitManipulator.bitAtPositionIsSet(index + 1, x, floatingIndices.size)) {
                    maskArray[value] = '1'
                } else {
                    maskArray[value] = '0'
                }
            }
            val builder = StringBuilder()
            maskArray.forEach {
                builder.append(it)
            }
            memoryAddress[builder.toString().toLong(2)] = input
        }
        return memoryAddress
    }

    private fun readFileAsLinesUsingUseLines(): List<String> =
        File("day14/input.txtut.txt").useLines { data ->
            data.toList()
        }
}

class BitManipulator {
    fun setBitAtPosition(number: Long, position: Int): Long {
        return (1L shl position - 1) or number
    }

    fun clearBitAtPosition(number: Long, position: Int): Long {
        return (1L shl position).inv() and number
    }

    fun bitAtPositionIsSet(position: Int, number: Long, numberOfBits: Int): Boolean {
        return ((1 shl numberOfBits - position).toLong() and number) > 0
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
