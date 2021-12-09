
import org.junit.Test
import java.io.File
import java.util.*

class Part2 {
    private val fileInput =
        File("input.txt").readLines()
            .map {
                it.map {
                    it.digitToInt()
                }
            }

    val locations = fileInput.mapIndexed { x, list ->
        list.mapIndexed { y, value ->
            Location(value, x, y)
        }
    }

    private fun getLowPoints(): List<Location> {
        val yMax = fileInput[0].lastIndex
        val lowPoints = mutableListOf<Location>()
        for (x in 0..fileInput.lastIndex) {
            for (y in 0..yMax) {
                val currentValue = fileInput[x][y]
                var isLower = true
                if (y - 1 >= 0 && fileInput[x][y - 1] <= currentValue) isLower = false
                if (y + 1 <= yMax && fileInput[x][y + 1] <= currentValue) isLower = false
                if (x - 1 >= 0 && fileInput[x - 1][y] <= currentValue) isLower = false
                if (x + 1 <= fileInput.lastIndex && fileInput[x + 1][y] <= currentValue) isLower =
                    false
                if (isLower) lowPoints.add(locations[x][y])
            }
        }
        return lowPoints
    }

    private fun solvePart2() {

        val lowPoints = getLowPoints()
        val yMax = fileInput[0].lastIndex
        println(lowPoints.map {
            it.value
        })
        val result = mutableListOf<Set<Location>>()
        lowPoints.forEach {
            val seen = mutableSetOf<Location>()
            val currentStack = Stack<Location>()
            currentStack.push(it)
            while (currentStack.isNotEmpty()) {
                val currentValue = currentStack.pop()
                seen.add(currentValue)
                if (currentValue.y - 1 >= 0 && fileInput[currentValue.x][currentValue.y - 1] >
                    currentValue.value && fileInput[currentValue.x][currentValue.y - 1] != 9 && !seen.contains(
                        locations[currentValue.x][currentValue.y - 1]
                    )
                ) {
                    currentStack.push(locations[currentValue.x][currentValue.y - 1])
                }

                if (currentValue.y + 1 <= yMax && fileInput[currentValue.x][currentValue.y + 1] >
                    currentValue.value && fileInput[currentValue.x][currentValue.y + 1] != 9 && !seen.contains(
                        locations[currentValue.x][currentValue.y + 1]
                    )
                ) {
                    currentStack.push(locations[currentValue.x][currentValue.y + 1])
                }
                if (currentValue.x - 1 >= 0 && fileInput[currentValue.x - 1][currentValue.y] >
                    currentValue.value && fileInput[currentValue.x - 1][currentValue.y] != 9 && !seen.contains(
                        locations[currentValue.x - 1][currentValue.y]
                    )
                ) {
                    currentStack.push(locations[currentValue.x - 1][currentValue.y])
                }
                if (currentValue.x + 1 <= locations.lastIndex && fileInput[currentValue.x + 1][currentValue.y] >
                    currentValue.value && fileInput[currentValue.x + 1][currentValue.y] != 9 && !seen.contains(
                        locations[currentValue.x + 1][currentValue.y]
                    )
                ) {
                    currentStack.push(locations[currentValue.x + 1][currentValue.y])
                }

            }
            result.add(seen)
        }
        val finalResult = result.map {
            it.size
        }.sortedDescending()
        println(finalResult[0] * finalResult[1] * finalResult[2])
    }

    @Test
    fun test() {
        solvePart2()
    }
}

data class Location(val value: Int, val x: Int, val y: Int)
