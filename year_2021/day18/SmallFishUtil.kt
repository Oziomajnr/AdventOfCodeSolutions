package com.example.pgntogifconverter.util.twentyone.day18

import java.lang.NumberFormatException
import java.lang.StringBuilder
import java.util.*

//Hiding all the dirty code here so that the solution would not look like its dirty when its actually one very dirty piece of shit.
tailrec fun reduceSnailFish(input: String): String {
    return when {
        requiresExplode(input) -> {
            reduceSnailFish(explode(input))
        }
        requiresSplit(input) -> {
            reduceSnailFish(split(input))
        }
        else -> {
            input
        }
    }
}

fun explode(input: String): String {
    val inputArray = input.toStringArray().toMutableList()
    var numberOFOpenBrackets = 0
    var closedBracketIndex = -1
    for (index in 0..inputArray.lastIndex) {
        val value = inputArray[index]
        if (value == "[") {
            numberOFOpenBrackets++
        } else if (value == "]") {
            if (numberOFOpenBrackets >= 5) {
                closedBracketIndex = index
                break
            } else {
                numberOFOpenBrackets--
            }
        }
    }

    for (index in closedBracketIndex - 4 downTo 0) {

        val number = try {
            (inputArray[index].toInt())
        } catch (ex: NumberFormatException) {
            -1
        }

        if (number != -1) {
            inputArray[index] = (number + inputArray[closedBracketIndex - 3].toInt()).toString()
            break
        }
    }

    for (index in closedBracketIndex + 1..inputArray.lastIndex) {

        val number = try {
            (inputArray[index].toInt())
        } catch (ex: NumberFormatException) {
            -1
        }
        if (number != -1) {
            inputArray[index] = (number + inputArray[closedBracketIndex - 1].toInt()).toString()
            break
        }
    }
    val finalIndex = closedBracketIndex - 4
    inputArray.removeAt(finalIndex)
    inputArray.removeAt(finalIndex)
    inputArray.removeAt(finalIndex)
    inputArray.removeAt(finalIndex)
    inputArray.removeAt(finalIndex)
    inputArray.add(finalIndex, "0")


    val result = StringBuilder()
    inputArray.forEach {
        result.append(it)
    }
    return result.toString()
}

fun split(input: String): String {
    val inputArray = input.toStringArray().toMutableList()
    for (x in 0..inputArray.lastIndex) {
        val number = try {
            (inputArray[x].toInt())
        } catch (ex: NumberFormatException) {
            -1
        }

        if (number != -1 && number >= 10) {
            inputArray[x] = "[${number / 2},${(number / 2) + number % 2}]"
            break
        }
    }
    val result = StringBuilder()
    inputArray.forEach {
        result.append(it)
    }

    return result.toString()
}

fun requiresSplit(input: String): Boolean {
    return input.toStringArray().find {
        try {
            it.toInt() >= 10
        } catch (ex: Exception) {
            false
        }
    } != null
}

//
fun requiresExplode(input: String): Boolean {
    val inputArray = input.toStringArray()
    var numberOFOpenBrackets = 0
    var closedBracketIndex = -1
    for (index in 0..inputArray.lastIndex) {
        val value = inputArray[index]
        if (value == "[") {
            numberOFOpenBrackets++
        } else if (value == "]") {
            if (numberOFOpenBrackets >= 5) {
                closedBracketIndex = index
                break
            } else {
                numberOFOpenBrackets--
            }
        }
    }
    return closedBracketIndex != -1
}


fun getMagnitude(input: String): Int {
    val inputArray = input.toStringArray()
    val stack = Stack<String>()
    for (x in 0..inputArray.lastIndex) {
        val value = inputArray[x]
        if (value != "]") {
            stack.push(value)
        } else {
            var temp = stack.pop()
            val currentList = mutableListOf<String>()
            while (temp != "[") {
                currentList.add(temp)
                temp = stack.pop()
            }
            stack.push(evaluateExpression(currentList.reversed()).toString())
        }
    }
    return stack.pop().toInt()
}

fun evaluateExpression(input: List<String>): Int {
    val values = input.filter {
        it != ","
    }.map { it.toInt() }
    return values[0] * 3 + values[1] * 2
}

fun add(input1: String, input2: String): String {
    return "[$input1,$input2]"
}

fun String.toStringArray(): List<String> {
    val input = this
    val inputArray = mutableListOf<String>()
    var x = 0
    while (x <= input.lastIndex) {
        if (input[x].isDigit()) {
            var number = 0
            while (input[x].isDigit()) {
                number = number * 10 + input[x].digitToInt()
                x++
            }
            inputArray.add(number.toString())
        } else {
            inputArray.add(input[x].toString())
            x++
        }
    }
    return inputArray
}