 private val fileInput =
        File("input.txt").readLines()
            .first().split(",")
            .map {
                it.toInt()
            }

    private fun solvePart2() {
        val max = fileInput.maxOrNull()!!
        val groupedInput = fileInput.groupBy {
            it
        }.mapValues {
            it.value.count()
        }

        val costForPosition =  (0..max).map {
            it to groupedInput.map { (i, count) ->
                // sum of arithmetic progression with first value of 1 and common difference of 1
                val cost = (abs(i-it).toDouble()/2)*(2 + (abs(i-it) - 1) * 1)
                (cost * count).toInt()
            }.sum()
        }

        println(costForPosition.minByOrNull {
            it.second
        })
    }

    @Test
    fun test() {
        solvePart2()
    }
