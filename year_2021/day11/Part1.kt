 private val fileInput =
        File("input.txt").readLines()
            .map {
                it.map {
                    it.digitToInt()
                }
            }


    fun solvePart1(currentInput: List<List<Octupus>>, currentIteration: Int, result: Int): Int {
        println(currentInput)
        val input = currentInput.map {
            it.map {
                it.value++
                it
            }
        }

        val yMax = input[0].lastIndex

        if (currentIteration <= 100) {
            var currentResult = result
            val seen = mutableSetOf<Octupus>()

            while (input.filter {
                    it.any {
                        it.value > 9
                    }
                }.isNotEmpty()) {

                for (x in 0..input.lastIndex) {
                    for (y in 0..yMax) {
                        val currentValue = input[x][y].value
                        if (currentValue > 9) {
                            if (y - 1 >= 0 && !seen.contains(input[x][y - 1])) (input[x][y - 1]).value++
                            if (y + 1 <= yMax && !seen.contains(input[x][y + 1])) (input[x][y + 1]).value++
                            if (x - 1 >= 0 && !seen.contains(input[x - 1][y])) (input[x - 1][y]).value++
                            if (x + 1 <= input.lastIndex && !seen.contains(input[x + 1][y])) (input[x + 1][y]).value++
                            if (y - 1 >= 0 && x - 1 >= 0 && !seen.contains(input[x - 1][y - 1])) (input[x - 1][y - 1]).value++
                            if (y + 1 <= yMax && x + 1 <= input.lastIndex && !seen.contains(input[x + 1][y + 1])) (input[x + 1][y + 1]).value++
                            if (y + 1 <= yMax && x - 1 >= 0 && !seen.contains(input[x - 1][y + 1])) (input[x - 1][y + 1]).value++
                            if (y - 1 >= 0 && x + 1 <= input.lastIndex && !seen.contains(input[x + 1][y - 1])) (input[x + 1][y - 1]).value++
                            input[x][y].value = 0
                            seen.add(input[x][y])
                            currentResult++
                        }
                    }
                }
            }
            return solvePart1(input, currentIteration + 1, currentResult)
        } else {
            return result
        }
    }

    @Test
    fun test() {
        println(solvePart1(fileInput.map {
            it.map {
                Octupus(it)
            }
        }, 1, 0))
    }
}

 data class Octupus(var value: Int) {
    override fun toString(): String {
        return value.toString()
    }
