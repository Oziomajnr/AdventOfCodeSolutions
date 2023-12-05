val input = File("input1.txt").readText()

fun main() {
    val lines = input.trim().split("\n\n")
    val seeds = "\\d+".toRegex().findAll(lines[0]).map { it.value.toLong() }.toList()
    val keyMaps = lines.drop(1).map {
        it.split("\n")[0].dropLast(5).split("-to-").let {
            it[0] to it[1]
        }
    }.toMap()
    val destinationMap = lines.drop(1).map {
        it.split("\n")[0].dropLast(5).split("-to-")[0] to it.split("\n").drop(1).map {
            it.split("\\s+".toRegex()).let {
                RangeValue(it[1].toLong(), it[0].toLong(), it[2].toLong())
            }
        }.sortedBy { it.source }
    }.toMap()

    val newSeeds = seeds.chunked(2).map {
        (it[0] until it[0] + it[1])
    }
    println(
        newSeeds.map {
            val location = findLowestLocation("seed", it, keyMaps, destinationMap)
            location
        }.min()
    )
}

fun findLowestLocation(
    key: String,
    value: LongRange,
    keyMaps: Map<String, String>,
    destinationMap: Map<String, List<RangeValue>>
): Long {
    val newValue = getValue(key, value, destinationMap)
    if (key == "humidity") {
        return newValue.minByOrNull { it.first }!!.first
    }
    return newValue.minOf {
        findLowestLocation(keyMaps[key]!!, it, keyMaps, destinationMap)
    }
}

data class RangeValue(val source: Long, val destination: Long, val step: Long)

fun getValue(key: String, inputRange: LongRange, destinationMap: Map<String, List<RangeValue>>): List<LongRange> {
    val destinationRanges = destinationMap[key]!!
    val resultRange = mutableListOf<LongRange>()
    var value = inputRange

    destinationRanges.forEach {
        val sourceLastValue = it.source + it.step - 1
        when {
            value.first < it.source && value.last < it.source -> {
                resultRange.add(value)
                return resultRange
            }

            value.first < it.source && value.last <= sourceLastValue -> {
                resultRange.addAll(
                    listOf(
                        value.first until it.source,
                        it.destination..it.destination + (value.last - it.source)
                    )
                )
                return resultRange
            }

            value.first >= it.source && value.last <= sourceLastValue -> {
                resultRange.add(it.destination + value.first - it.source..it.destination + value.first - it.source + (value.last - value.first))
                return resultRange
            }

            value.first >= it.source && value.first < sourceLastValue -> {
                resultRange.add(it.destination + value.first - it.source..it.destination + value.last - sourceLastValue)
                value = sourceLastValue + 1..value.last
            }

            value.first > sourceLastValue -> {
                listOf(value)
            }

            else -> {
                resultRange.addAll(
                    listOf(
                        value.first until it.source,
                        it.destination..it.destination + (value.last - sourceLastValue)
                    )
                )
                value = sourceLastValue + 1..value.last
            }

        }
    }
    resultRange.add(value)
    return resultRange
}
