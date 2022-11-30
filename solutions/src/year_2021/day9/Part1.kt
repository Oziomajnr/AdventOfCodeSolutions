 private val fileInput =
        File("input.txt").readLines()
            .map {
                it.map {
                    it.digitToInt()
                }
            }


    fun solvePart1() {
        var result = 0
        val yMax = fileInput[0].lastIndex

        for (x in 0..fileInput.lastIndex) {
            for (y in 0..yMax) {
                val currentValue = fileInput[x][y]
                var isLower = true
                if (y - 1 >= 0 && fileInput[x][y - 1] <= currentValue) isLower = false
                if (y + 1 <= yMax && fileInput[x][y + 1] <= currentValue) isLower = false
                if (x - 1 >= 0 && fileInput[x - 1][y] <= currentValue) isLower = false
                if (x + 1 <= fileInput.lastIndex && fileInput[x + 1][y] <= currentValue) isLower =
                    false
                if (isLower) result += currentValue + 1

            }
        }
        println(result)
    }

    @Test
    fun test() {
        solvePart1()
    }
