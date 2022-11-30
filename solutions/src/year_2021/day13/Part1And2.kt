
import org.junit.Test
import java.io.File
import kotlin.math.max

class Part2 {
    private val points =
        File("input.txt").readLines()
            .map {
                val points = it.split(',').map {
                    it.toInt()
                }
                Point(points[0], points[1])
            }

    private val instructions =
        File("instructions.txt").readLines()
            .map {
                val instruction = it.split(' ').last().split('=')
                Instruction(instruction[0], instruction[1].toInt())
            }

    private fun solvePart2(input: Array<BooleanArray>, instructionIndex: Int): Array<BooleanArray> {
        if (instructionIndex > instructions.lastIndex) return input
        val instructio = instructions[instructionIndex]
        return solvePart2(
            when (instructio.direction) {
                "x" -> {
                    foldAlongX(input, instructio.value)
                }
                "y" -> {
                    foldAlongY(input, instructio.value)
                }
                else -> {
                    error("Invalid direction")
                }
            },
            instructionIndex + 1
        )
    }

    private fun foldAlongX(inputArray: Array<BooleanArray>, value: Int): Array<BooleanArray> {
        var x1 = value - 1
        var x2 = value + 1
        val resultArray = Array(inputArray.size) {
            BooleanArray(value)
        }
        while (x1 >= 0 && x2 <= inputArray.first().lastIndex) {

            for (y in 0..inputArray.lastIndex) {
                if (inputArray[y][x1] || inputArray[y][x2]) {
                    resultArray[y][x1] = true
                }
            }
            x1--
            x2++
        }
        return resultArray
    }

    private fun foldAlongY(inputArray: Array<BooleanArray>, value: Int): Array<BooleanArray> {
        var y1 = value - 1
        var y2 = value + 1
        val resultArray = Array(value) {
            BooleanArray(inputArray.first().size)
        }
        while (y1 >= 0 && y2 <= inputArray.lastIndex) {

            for (x in 0..inputArray.first().lastIndex) {
                if (inputArray[y1][x] || inputArray[y2][x]) {
                    resultArray[y1][x] = true
                }
            }
            y1--
            y2++
        }
        return resultArray
    }

    @Test
    fun test() {
        val maxPoint = points.maxByOrNull {
            max(it.x, it.y)
        }!!
        val max = max(maxPoint.x, maxPoint.y) + 1

        val inputArray = Array(max) {
            BooleanArray(max)
        }
        points.map {
            inputArray[it.y][it.x] = true
        }
        solvePart2(inputArray, 0).forEach {
            it.forEach {
                if (it) {
                    print("# ")
                } else {
                    print(". ")
                }
            }
            println()
        }
    }
}

data class Instruction(val direction: String, val value: Int)
