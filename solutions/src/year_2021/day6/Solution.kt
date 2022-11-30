class Day6 {
    private val fileInput =
        File("input.txt").readLines()
            .first().split(",")
            .map {
                it.toInt()
            }

    private val cachedResult = mutableMapOf<String, Long>()
    private fun solvePart1(input: List<Int>, day: Int, totalDays: Int): Long {
        if (day == totalDays) {
            return input.size.toLong()
        }
        val mutableInput = mutableListOf<Int>()
        mutableInput.addAll(input)
        for (x in 0..input.lastIndex) {
            if (mutableInput[x] == 0) {
                mutableInput[x] = 6
                mutableInput.add(8)
            } else {
                mutableInput[x] = mutableInput[x] - 1
            }
        }
        val nextInput = mutableInput.groupBy {
            it
        }
        return nextInput.map {
            it.key to it.value.size
        }.map {
            it.first
        }.map {
            val tempResult = cachedResult.get(getHash(it, day + 1))

            val result = if (tempResult == null) {
                val x = solvePart1(listOf(it), day + 1, totalDays)
                cachedResult.put(getHash(it, day + 1), x)
                x
            } else {
                tempResult
            }
            result * nextInput[it]!!.size
        }.sum()
    }

    private fun getHash(day: Int, value: Int): String {
        return "$day,$value"
    }

    @Test
    fun test() {
        println(solvePart1(fileInput, 0, 256))
    }
}
