package com.example.pgntogifconverter.util.twentyone

import org.junit.Test
import java.io.File

class Day2 {
    val input =
        File("input.txt").readLines()
            .map {
                val line = it.split(' ')
                Input(line.first(), line[1].toInt())
            }

    private fun part1(): Int {
        val totalY = input.map {
            when (it.instruction) {
                "down" -> {
                    it.value
                }
                "up" -> {
                    -it.value
                }
                else -> {
                    0
                }
            }
        }.sum()

        val totalX =
            input.map {
                when (it.instruction) {
                    "forward" -> {
                        it.value
                    }
                    else -> {
                        0
                    }
                }
            }.sum()

        return totalX * totalY
    }

    private fun part2(): Long {

        val totalAim = input.map {
            when (it.instruction) {
                "down" -> {
                    it.value
                }
                "up" -> {
                    -it.value
                }
                else -> {
                    0
                }
            }
        }
        val totalX = input.map {
            when (it.instruction) {
                "forward" -> {
                    it.value.toLong()
                }
                else -> {
                    0L
                }
            }
        }.sum()

        val totalY = input.forEach {
            when (it.instruction) {
                "forward" -> {
                    forward += (it.value * aim)
                }
                else -> {
                    0L
                }

            }
        }
        return totalX * totalY
    }

    @Test
    fun test() {
        print(part1())
        print(part2())
    }
}

data class Input(val instruction: String, val value: Int)
