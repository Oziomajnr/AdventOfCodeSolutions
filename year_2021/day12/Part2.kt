 Part2 {
    private val dataPoll = mutableSetOf<Cave>()

    private val fileInput =
        File("input.txt").readLines()
            .map {
                val splittedValue = it.split('-')
                val first = Cave(splittedValue.first())
                val second = Cave(splittedValue[1])
                dataPoll.add(first)
                dataPoll.add(second)
                Pair(dataPoll.first { it == first }, dataPoll.first { it == second })
            }


    private val allCaves = fileInput.map {
        it.toList()
    }.flatten().toSet()

    private val startCave = allCaves.find {
        it.isStartCave
    }!!

    val result = mutableListOf<List<Cave>>()

    fun solvePart1(currentCave: Cave, currentResult: List<Cave>) {
        if (currentResult.filter {
            !it.isBigCave
        }.groupBy { it.value }.mapValues {
            it.value.size
        }.count { it.value > 1 } > 1) {
            return
        }
        val validSiblings = currentCave.siblings.filter {
            val smallSiblingCaves = currentResult.filter {
                !it.isBigCave
            }.groupBy { it.value }.mapValues {
                it.value.size
            }
            val shouldAdd =  (smallSiblingCaves[it.value] == null) || (smallSiblingCaves.count { it.value > 1 } == 0)
            it.isBigCave || shouldAdd
        }
        if (currentCave.isEndCave) {
            result.add(currentResult)
        }

        for (cave in validSiblings) {

            solvePart1(cave, currentResult + currentCave)
        }
    }

    private fun connectCaves() {
        allCaves.forEach { cave ->
            fileInput.forEach { cavePair ->
                if (cavePair.first == cave) {
                    if (!cavePair.second.isStartCave) {
                        cave.siblings.add(cavePair.second)
                    }
                    val secondCave = allCaves.find { cavePair.second == it }!!
                    if (!secondCave.isEndCave && !cave.isStartCave) {
                        secondCave.siblings.add(cave)
                    }
                }
            }
        }
    }

    @Test
    fun test() {
        connectCaves()
        solvePart1(startCave, emptyList())
        println(result.count())
    }
