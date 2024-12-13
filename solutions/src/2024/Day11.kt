package `2024`

import java.io.File
import java.math.BigInteger
import java.util.UUID


fun main() {
    val input =
        File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText().split(" ")
            .map { it.toLong() }

    println(input.map { (calculate(BigInteger.valueOf(it), 1)) }.sumOf { it })
    println(UUID.randomUUID())

}

val cache = mutableMapOf<Pair<BigInteger, Int>, BigInteger>()

fun calculate(source: BigInteger, count: Int): BigInteger {
    cache[source to count]?.let { return it }

    val res = buildList {
        if (source == BigInteger.ZERO) add(BigInteger.ONE)
        else if (source.toString().length % 2 == 0) {
            add(source.toString().take(source.toString().length / 2).toBigInteger())
            add(source.toString().drop(source.toString().length / 2).toBigInteger())
        } else {
            add(source * BigInteger.valueOf(2024L))
        }
    }
    cache[source to count] = if (count == 75) BigInteger.valueOf(res.size.toLong())
    else res.sumOf {
        calculate(it, count + 1)
    }
    return  cache[source to count]!!

}