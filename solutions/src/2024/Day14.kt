package `2024`

import utils.Point
import java.io.File


fun main() {

    (1..1000).forEach { seconds ->
        val input =
            File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText().split("\n")
                .map {
                    Regex("-?\\d*\\.?\\d+").findAll(it).toList().let {
                        Robot(
                            Point(it[0].value.toInt(), it[1].value.toInt()),
                            Point(it[2].value.toInt(), it[3].value.toInt())
                        )
                    }
                }.map {
                    it.copy(position = getNewPosition(it, 2851 + 101 * seconds))
                }
        val x = (0..102).map { y ->
            (0..100).map { x ->
                "."
            }.toMutableList()
        }.toMutableList()
        var firstQudrant = 0
        var secondQudrant = 0
        var thirdQudrant = 0
        var fourthQudrant = 0
        val x1 = 50
        val y1 = 51

        input.forEach {
            if (it.position.x < x1 && it.position.y < y1) firstQudrant++
            if (it.position.x < x1 && it.position.y > y1) secondQudrant++
            if (it.position.x > x1 && it.position.y < y1) thirdQudrant++
            if (it.position.x > x1 && it.position.y > y1) fourthQudrant++
        }

        input.forEach {
            if (x[it.position.y][it.position.x] == ".") x[it.position.y][it.position.x] =
                "1" else x[it.position.y][it.position.x] = (x[it.position.y][it.position.x].toInt() + 1).toString()
        }
        //write each lins to file
        val file =
            File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/output/output.txt").let {
                it.createNewFile()
                it
            }
        //create file if not exist
        file.createNewFile()
        x.forEach {
            println(it.joinToString(""))
            file.appendText(it.joinToString(""))
            file.appendText("\n")
        }
        file.appendText(seconds.toString())

        println(firstQudrant * secondQudrant * thirdQudrant * fourthQudrant)

    }
}

//12
private fun getNewPosition(robot: Robot, seconds: Int): Point {
    val yMax = 103
    val xMax = 101
    val newXPosition = robot.position.x + (seconds * robot.velocity.x)
    val newYPosition = robot.position.y + (seconds * robot.velocity.y)
    val finalX = if (newXPosition < 0) {
        (xMax + ((newXPosition % (xMax))))
    } else if (newXPosition >= xMax) {
        newXPosition % (xMax)
    } else {
        newXPosition
    }

    val finalY = if (newYPosition < 0) {
        yMax + (newYPosition % yMax)
    } else if (newYPosition >= yMax) {
        newYPosition % (yMax)
    } else {
        newYPosition
    }
    return Point(if (finalX == xMax) 0 else finalX, if (finalY == yMax) 0 else finalY)
}

private data class Robot(val position: Point, val velocity: Point)