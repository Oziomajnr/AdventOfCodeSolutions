package com.ozioma

import java.math.BigDecimal
import java.math.MathContext

fun main() {
    val coordinatesToVelocity = lines.map { line ->
        line.split(" @ ").map {
            it.trim().split(",\\s*".toRegex()).run {
                Triple(this[0].toLong(), this[1].toLong(), this[2].toLong())
            }
        }.run { this[0] to this[1] }
    }
    val range = 200000000000000.toBigDecimal()..400000000000000.toBigDecimal()

    val lines = coordinatesToVelocity.map {
        Line(
            Point3d(it.first.first, it.first.second, it.first.third),
            Point3d(it.second.first, it.second.second, it.second.third)
        )
    }
    val intersections = lines.mapIndexed { index, line ->
        lines.drop(index + 1).mapNotNull {
            if (line.gradient() == it.gradient()) {
                null
            } else {
                val x = (it.intercept().minus(line.intercept())).divide(
                    line.gradient().minus(it.gradient()),
                    MathContext.DECIMAL128
                )
                val y =
                    line.gradient().multiply(it.intercept()).minus(it.gradient().multiply(line.intercept()))
                        .divide(line.gradient().minus(it.gradient()), MathContext.DECIMAL128)
                if (line.isFutureLine(Point2d(x, y)) &&
                    it.isFutureLine(Point2d(x, y)) &&
                    x in range && y in range
                ) {
                    x to y
                } else {
                    null
                }
            }
        }
    }.flatten()
    println('k' in "kotlin")
    println(intersections.size)
}

data class Line(val point: Point3d, val velocity: Point3d) {
    fun gradient(): BigDecimal = velocity.y.toBigDecimal().divide(velocity.x.toBigDecimal(), MathContext.DECIMAL128)

    fun intercept() = point.y.toBigDecimal().minus(gradient().multiply(point.x.toBigDecimal(), MathContext.DECIMAL128))

    fun isFutureLine(point: Point2d): Boolean {
        if (velocity.x > 0 && point.x < this.point.x.toBigDecimal()) return false
        if (velocity.x < 0 && point.x > this.point.x.toBigDecimal()) return false
        if (velocity.y > 0 && point.y < this.point.y.toBigDecimal()) return false
        if (velocity.y < 0 && point.y > this.point.y.toBigDecimal()) return false
        return true
    }
}

data class Point2d(val x: BigDecimal, val y: BigDecimal)

//For part 2
//Pick any 3 lines 

// and substitute in the equation 

// 219051609191782(L), 68260434807407(M), 317809635461867(N) @ 146, 364, -22
// 292151991892724(O), 394725036264709(P), 272229701860796(Q) @ -43, -280, -32
// 455400538938496(R), 167482380286201(S), 389150487664328(T) @ -109, 219, -58

// L + 146i - ai = O - 43j - aj
// M + 364i - bi = P - 280j - bj
// N - 22i - ci = Q - 32j - cj
// R - 109k - ak = L + 146i - ai
// S + 219k - bk = M + 364i - bi
// T - 58k - ck = N - 22i - ci


