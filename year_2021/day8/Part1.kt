 private val fileInput =
        File("input.txt").readLines()


    fun solvePart1(): Int {
        return  fileInput.map {
            it.split(" | ")[1]
        }.map {
            it.split(" ")
        }.map { value ->
            value.map {
                it.length
            }.filter {
                it in listOf(2, 4, 3, 7)
            }
        }.flatten().count()

    }

    @Test
    fun test() {
        println(solvePart1())
    }
