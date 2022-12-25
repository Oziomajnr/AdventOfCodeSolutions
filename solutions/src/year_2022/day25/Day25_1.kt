package year_2022.day25

import solve
import kotlin.math.pow

fun main() = solve { lines ->
    lines.sumOf { snafuToDecimal(it) }.toSnafu()
}

fun snafuToDecimal(snafuNumber: String): Long {
    return snafuNumber.reversed().mapIndexed { index, value ->
        val realValue = when (value) {
            '-' -> {
                -1
            }

            '=' -> {
                -2
            }

            else -> {
                value.digitToInt()
            }
        }
        realValue * 5.toDouble().pow(index.toDouble())
    }.sumOf {
        it.toLong()
    }
}

fun Long.toSnafu(): String {
    if (this < 10) return getSingleDigitSnafu(this.toInt())
    var temp = this
    var result = ""
    while (temp > 0) {
        val reminder = temp % 5
        val digit = getSingleDigitSnafu(reminder.toInt())
        temp /= 5
        if (digit.length == 1) {
            result = "$digit$result"
        } else {
            result = "${digit[1]}$result"
            temp += digit[0].digitToInt()
        }
    }
    return result
}

fun getSingleDigitSnafu(snafuNumber: Int) = listOf("0", "1", "2", "1=", "1-", "10", "11", "12", "2=", "2-")[snafuNumber]
