import java.io.File

fun main() {
    val x = File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readLines()
        .map { it.toLong() }
        .map {
            val list = buildList {
                add(it)
                (1..2000).fold(it) { acc, i ->
                    (((acc * 64L) xor acc) % 16777216L).let {
                        ((it / 32L) xor it) % 16777216L
                    }.let { ((it * 2048L) xor it) % 16777216L }.also { add(it) }
                }
            }.map { it % 10 }

            list.mapIndexed { index, l ->
                if (index == 0) l to 0L
                else l to list[index] - list[index - 1]
            }
        }

    val pc = x.map {
        it.windowed(4).filter {
            it[0].second != 0L && it[1].second != 0L && it[2].second != 0L && it[3].second != 0L
        }.map {
            PriceChangeState(it[0].second, it[1].second, it[2].second, it[3].second)
        }
    }.flatten().distinct()

    val xMap = x.map {
        it.windowed(4).map {
            PriceChangeState(
                it[0].second,
                it[1].second,
                it[2].second,
                it[3].second
            ) to it[3].first
        }.distinctBy { it.first }.toMap()
    }
    pc.maxOfOrNull { priceChangeState ->
        val ccc = xMap.sumOf {
            it[priceChangeState] ?: 0L
        }
        ccc
    }.also { println(it) }


}

private data class PriceChangeState(val a: Long, val b: Long, val c: Long, val d: Long)