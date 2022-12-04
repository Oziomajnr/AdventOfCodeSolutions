package year_2021.day3

import org.junit.Test
import java.io.File

class DayThree {
    private val fileInput =
        File("input.txt").readLines()

    private fun solveDay3() {
        val gamma = fileInput.first().mapIndexed { index, value ->
            when(hasMoreOnesThanZerosForCurrentBit(index, fileInput)) {
                true -> {
                    '1'
                }
                false -> {
                    '0'
                }
                null -> {
                    error("invalidState")
                }
            }
        }

        val epsilon = fileInput.first().mapIndexed { index, value ->
            when(hasMoreOnesThanZerosForCurrentBit(index, fileInput)) {
                true -> {
                    '0'
                }
                false -> {
                    '1'
                }
                null -> {
                    error("invalidState")
                }
            }
        }
        println(gamma.joinToString("").toInt(2) * epsilon.joinToString("").toInt(2))
    }

    private fun hasMoreOnesThanZerosForCurrentBit(currentBit: Int, input: List<String>): Boolean? {
        val numberOfOnes = input.map {
            it[currentBit]
        }.filter {
            it == '1'
        }.size
        val numberOfZeros = input.size - numberOfOnes

        return when {
            numberOfZeros > numberOfOnes -> {
                false
            }
            numberOfZeros < numberOfOnes -> {
                true
            }
            else -> {
                null
            }
        }
    }


    @Test
    fun test() {
        solveDay3()
    }
}

