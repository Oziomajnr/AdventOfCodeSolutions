package year_2022.day20

import solve

fun main() = solve { lines ->
    val inputArray = lines.map {
        NumberValue(it.toLong())
    }.toTypedArray()
    val finalPositionArray = Array(inputArray.size) {
        inputArray[it]
    }
    decryptNumber(finalPositionArray, inputArray, 10)
    getNthValueAfterZero(1000, finalPositionArray) + getNthValueAfterZero(
        2000, finalPositionArray
    ) + getNthValueAfterZero(3000, finalPositionArray)
}
