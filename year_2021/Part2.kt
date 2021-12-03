package com.example.pgntogifconverter.util.twentyone.day_3

import org.junit.Test
import java.io.File

class Part2 {
    private val input =
        File("/Users/oziomaogbe/AndroidStudioProjects/PgnToGifConverter/app/src/test/java/com/example/pgntogifconverter/util/twentyone/input.txt").readLines()

    private fun findGamma() {
        val oxygenNumber = findOxygenNumber(0, input).toInt(2)
        val carbonNumber = carbonNumber(0, input).toInt(2)
        println(oxygenNumber * carbonNumber)
    }

    private fun findOxygenNumber(
        currentBit: Int,
        filetredInput: List<String>
    ): String {
        if (filetredInput.size == 1) {
            return filetredInput.first()
        }
        val numberOfOnes = filetredInput.filter {
            it[currentBit] == '1'
        }.size
        val numberOfZeros = filetredInput.size - numberOfOnes
        return findOxygenNumber(currentBit + 1, filetredInput.filter {
            if (numberOfZeros > numberOfOnes) {
                it[currentBit] == '0'
            } else {
                it[currentBit] == '1'
            }
        })
    }

    private fun carbonNumber(
        currentBit: Int,
        filetredInput: List<String>
    ): String {
        println(filetredInput)
        if (filetredInput.size == 1) {
            return filetredInput.first()
        }
        val numberOfOnes = filetredInput.filter {
            it[currentBit] == '1'
        }.size
        val numberOfZeros = filetredInput.size - numberOfOnes
        return carbonNumber(currentBit + 1, filetredInput.filter {
            if (numberOfZeros > numberOfOnes) {
                it[currentBit] == '1'
            } else {
                it[currentBit] == '0'
            }
        })
    }


    @Test
    fun test() {
        findGamma()
    }
}