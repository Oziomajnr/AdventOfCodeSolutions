package com.example.pgntogifconverter.util.day_18

import java.io.File
import java.lang.StringBuilder
import java.util.*

class Part1 {
    val result =
        File("/Users/oziomaogbe/AndroidStudioProjects/PgnToGifConverter/app/src/test/java/com/example/pgntogifconverter/util/day_18/input.txt").readLines()
            .map {
                solveExpression(it)
            }.sum()

    fun solveExpression(expression: String): Long {
        val stack = Stack<String>()
        expression.filter { !it.isWhitespace() }.forEach {
            if (it == ')') {
                var builder = ""
                var x = stack.pop()
                while (x != "(") {
                    builder = x + builder
                    x = stack.pop()
                }
                stack.push(evaluateIntermediateExpression(builder).toString())
            } else {
                stack.push(it.toString())
            }
        }
        val builder = StringBuilder()
        stack.forEach {
            builder.append(it)
        }
        val result = evaluateIntermediateExpression(builder.toString())
        println("Result of $expression is $result")
        return result
    }

    private fun evaluateIntermediateExpression(inputExpression: String): Long {
        var result = 0L
        var x = 0
        var isAdd = false
        while (x <= inputExpression.lastIndex) {
            when {
                inputExpression[x].isDigit() -> {
                    //get all numbers in  digit
                    var number = 0
                    while (x <= inputExpression.lastIndex && inputExpression[x].isDigit()) {
                        number = (number * 10) + inputExpression[x].digitToInt()
                        x++
                    }
                    if (isAdd) {
                        result += number
                    } else {
                        if (result == 0L) {
                            result += number
                        } else {
                            result *= number
                        }
                    }
                }

                inputExpression[x] == '+' -> {
                    isAdd = true
                    x++
                }

                inputExpression[x] == '*' -> {
                    isAdd = false
                    x++
                }

            }
        }
        return result
    }

}