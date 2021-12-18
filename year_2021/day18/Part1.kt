
import org.junit.Assert
import org.junit.Test
import java.io.File

class Part1 {
    private val fileInput =
        File("input.txt").readLines()

    private fun solvePart1(): String {
        return fileInput.reduce { acc, input ->
            val result = reduceSnailFish(add(acc, input))
            result
        }
    }

    private tailrec fun reduceSnailFish(input: String): String {
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

    @Test
    fun test() {
       println(explode("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"))
        Assert.assertEquals(
            "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]",
            explode("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")
        )
        Assert.assertEquals(
            "[[[[0,7],4],[15,[0,13]]],[1,1]]",
            explode("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]")
        )

        Assert.assertTrue(requiresSplit("[[[[0,7],4],[15,[0,13]]],[1,1]]"))
        Assert.assertFalse(requiresSplit("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"))
        Assert.assertFalse(requiresExplode("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]"))

        Assert.assertEquals(
            "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]",
            split("[[[[0,7],4],[15,[0,13]]],[1,1]]")
        )
        Assert.assertEquals(
            "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]",
            split("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]")
        )
        Assert.assertEquals(29, getMagnitude("[9,1]"))
        Assert.assertEquals(143, getMagnitude("[[1,2],[[3,4],5]]"))
        Assert.assertEquals(3488, getMagnitude("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"))
        val resultString = solvePart1()
        val magnitude = getMagnitude(resultString)
        println(magnitude)
    }
}

