package year_2022.day18

import solve
import kotlin.math.abs

fun main() = solve { lines ->
    val points = lines.map {
        it.split(",").run {
            Cube(this[0].toInt(), this[1].toInt(), this[2].toInt())
        }

    }
    (points.count() * 6) - points.sumOf { cube ->
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
                    }

                } else if (siblingCube.y == cube.y && siblingCube.z == cube.z) {
                    if (abs(siblingCube.x - cube.x) == 1) {
                        if (siblingCube.x > cube.x) {
                            x1 = true
                        } else {
                            x2 = true
                        }
                    }

                } else if (siblingCube.x == cube.x && siblingCube.z == cube.z) {
                    if (abs(siblingCube.y - cube.y) == 1) {
                        if (siblingCube.y > cube.y) {
                            y1 = true
                        } else {
                            y2 = true
                        }
                    }
                }
            }
        }
        listOf(x1, x2, y2, y1, z1, z2).count {
            it
        }
    }

}

data class Cube(val x: Int, val y: Int, val z: Int) : Comparable<Cube> {
    override fun compareTo(other: Cube): Int {
        val xComparison = this.x.compareTo(other.x)
        val yComparison = this.y.compareTo(other.y)
        val zComparison = this.z.compareTo(other.z)
        return if (xComparison == 0) {
            if (yComparison == 0) {
                zComparison
            } else {
                yComparison
            }
        } else {
            xComparison
        }
    }

}
