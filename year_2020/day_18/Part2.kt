package com.example.pgntogifconverter.util.day_18

import org.junit.Assert
import org.junit.Test
import java.io.File
import java.lang.StringBuilder
import java.util.*

class Solution {
    val result =
        File("/Users/oziomaogbe/AndroidStudioProjects/PgnToGifConverter/app/src/test/java/com/example/pgntogifconverter/util/day_18/input.txt").readLines()
            .map {
                solveExpression(it)
            }.sum()

//    2 + ((8 + 3 + 6 * 6 + 5 + 9) * 4 * 7 + 6 * 4 + (6 + 8 * 5 * 9 + 3)) + 9 + 7 * 7
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
                stack.push(evaluateAddition(builder).toString())
            } else {
                stack.push(it.toString())
            }
        }
        val builder = StringBuilder()
        stack.forEach {
            builder.append(it)
        }
        val result = evaluateAddition(builder.toString())
        println("Result of $expression is $result")
        return result
    }

    //    2*3*4+2*3*4
    fun evaluateAddition(inputExpression: String): Long {
        val stack = Stack<String>()
        var x = 0
        var activeMultiplication = false
        while (x <= inputExpression.lastIndex) {
            when {
                inputExpression[x] == '*' -> {
                    stack.push(inputExpression[x].toString())
                    activeMultiplication = false
                    x++
                }
                inputExpression[x].isDigit() -> {
                    //get all digits in  number
                    var number = 0L
                    while (x <= inputExpression.lastIndex && inputExpression[x].isDigit()) {
                        number = (number * 10) + inputExpression[x].digitToInt().toLong()
                        x++
                    }
                    if (activeMultiplication) {
                        val arg1 = stack.pop().toLong()
                        val arg2 = number
                        stack.push((arg1 + arg2).toString())
                    } else {
                        stack.push(number.toString())
                    }
                    activeMultiplication = false
                }
                inputExpression[x] == '+' -> {
                    activeMultiplication = true
                    x++
                }
                else -> {
                    error("Invalid state")
                }
            }
        }
        val builder = StringBuilder()
        stack.forEach {
            builder.append(it)
        }
        return simplifyMultiplication(stack)
    }

    fun simplifyMultiplication(expression: Stack<String>): Long {
        var result = 1L
        expression.forEach {
            if (it != "*") {
                result *= it.toLong()
            }
        }
        return result
    }
}


class SolutionTest {
    @Test
    fun testMultiplicationSimplification() {
        val solution = Solution()
        Assert.assertEquals(231, solution.evaluateAddition("1+2*3+4*5+6"))
        println("Result is ${solution.result}")
    }
}

//2 * 3 + (4 * 5) becomes 26.
//5 + (8 * 3 + 9 + 3 * 4 * 3) becomes 437.
//5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)) becomes 12240.
//((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2 becomes 13632