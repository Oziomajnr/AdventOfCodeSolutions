
import org.junit.Test
import java.io.File

class Part2 {
    private val fileInput =
        File("input.txt").readLines()
    var largest = Int.MIN_VALUE
    private val checked = mutableSetOf<String>()
    private fun solvePart2() {
        for (x in 0..fileInput.lastIndex) {
            for (y in 0..fileInput.lastIndex) {
                if (x != y && !checked.contains("$x,$y") ) {
                    val sum1 = getMagnitude(reduceSnailFish(add(fileInput[x], fileInput[y])))
                    if (sum1 > largest) {
                        largest = sum1
                    }
                    val sum2 = getMagnitude(reduceSnailFish(add(fileInput[y], fileInput[x])))
                    if (sum2 > largest) {
                        largest = sum2
                    }
                    checked.add("$x,$y")
                    checked.add("$y,$x")
                }
            }
        }
        println(largest)
    }


    @Test
    fun test() {
        solvePart2()
    }
}


