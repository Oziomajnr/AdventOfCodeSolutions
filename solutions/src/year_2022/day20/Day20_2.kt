package year_2022.day20

import solve
import kotlin.math.abs

fun main() = solve { lines ->
    val inputArray = lines.map {
        NumberValue(it.toLong() * (811589153L))
    }.toTypedArray()
    val finalPositionArray = Array(inputArray.size) {
        inputArray[it]
    }
    decryptNumber(finalPositionArray, inputArray, 10)

    getNthValueAfterZero(1000, finalPositionArray) + getNthValueAfterZero(
        2000, finalPositionArray
    ) + getNthValueAfterZero(3000, finalPositionArray)
}

fun decryptNumber(finalPositionArray: Array<NumberValue>, inputArray: Array<NumberValue>, numberOfRounds: Int) {
    repeat(numberOfRounds) {
        inputArray.forEach { input ->
            val numberOfTimesToMove = abs(input.value % inputArray.lastIndex)
            val index = finalPositionArray.indexOf(input)
            val destinationIndex = if (input.value > 0) {
                if (inputArray.lastIndex - (numberOfTimesToMove + index) > 0) {
                    index + numberOfTimesToMove
                } else {
                    abs(inputArray.lastIndex - (numberOfTimesToMove + index))
                }
            } else {
                if (index - numberOfTimesToMove >= 0) {
                    index - numberOfTimesToMove
                } else {
                    abs(inputArray.lastIndex - abs(index - numberOfTimesToMove))
                }
            }
            moveArrayValue(finalPositionArray, input, destinationIndex.toInt())
        }

    }
}

fun moveArrayValue(array: Array<NumberValue>, value: NumberValue, destinationIndex: Int) {

    val originalIndex = array.indexOf(value)
    if (destinationIndex > originalIndex) {
        for (index in originalIndex until destinationIndex) {
            array[index] = array[index + 1]
        }
    } else if (destinationIndex < originalIndex) {
        for (index in originalIndex downTo destinationIndex + 1) {
            array[index] = array[index - 1]
        }
    }
    array[destinationIndex] = value
}

fun getNthValueAfterZero(n: Int, array: Array<NumberValue>): Long {
    val zeroIndex = array.indexOf(array.find { it.value == 0L })

    val numberOfTimesToCount = n % array.size
    val result = if ((numberOfTimesToCount + zeroIndex) <= array.lastIndex) {
        array[numberOfTimesToCount + zeroIndex].value
    } else {
        array[abs(numberOfTimesToCount + zeroIndex) - 1 - array.lastIndex].value
    }
    return result
}

class NumberValue(val value: Long) {
    override fun toString(): String {
        return value.toString()
    }
}




