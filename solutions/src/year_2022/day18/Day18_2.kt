package year_2022.day18

import solve
import kotlin.math.abs

fun main() = solve { lines ->
    val points = lines.map {
        it.split(",").run {
            Cube(this[0].toInt(), this[1].toInt(), this[2].toInt())
        }

    }
    val airPockets = mutableMapOf<CubePair, Int>()
    val result =    ((points.count() * 6) - points.sumOf { cube ->
        var x1 = false
        var x2 = false
        var y1 = false
        var y2 = false
        var z1 = false
        var z2 = false

      points.forEach { siblingCube ->
            if (siblingCube != cube) {
                if (siblingCube.x == cube.x && siblingCube.y == cube.y) {
                    if (abs(siblingCube.z - cube.z) == 1) {
                        if (siblingCube.z > cube.z) {
                            z1 = true
                        } else {
                            z2 = true
                        }
                    } else {
                        val listOfCubes = listOf(cube, siblingCube).sorted()
                        airPockets[CubePair(listOfCubes[0], listOfCubes[1])] = abs(siblingCube.z - cube.z)
                    }

                } else if (siblingCube.y == cube.y && siblingCube.z == cube.z) {
                    if (abs(siblingCube.x - cube.x) == 1) {
                        if (siblingCube.x > cube.x) {
                            x1 = true
                        } else {
                            x2 = true
                        }
                    } else {
                        val listOfCubes = listOf(cube, siblingCube).sorted()
                        airPockets[CubePair(listOfCubes[0], listOfCubes[1])] = abs(siblingCube.x - cube.x)
                    }

                } else if (siblingCube.x == cube.x && siblingCube.z == cube.z) {
                    if (abs(siblingCube.y - cube.y) == 1) {
                        if (siblingCube.y > cube.y) {
                            y1 = true
                        } else {
                            y2 = true
                        }
                    } else {
                        val listOfCubes = listOf(cube, siblingCube).sorted()
                        airPockets[CubePair(listOfCubes[0], listOfCubes[1])] = abs(siblingCube.y - cube.y)
                    }
                }
            }
        }
        listOf(x1, x2, y2, y1, z1, z2).count {
            it
        }
    })
    result - (airPockets.values.sum() * 6)

}

data class CubePair(val cube1: Cube, val cube2: Cube)