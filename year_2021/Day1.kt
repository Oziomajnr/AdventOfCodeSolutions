class Day1 {
    val input =
        File("input.txt").readLines().map {
            it.toInt()
        }

    fun getNumberOfIncrements1() {
        println(input.filterIndexed { index, value ->
            index > 1 && input[index - 1] < input[index]
        }.size)
    }

    fun getNumberOfIncrements2() {
        println(input.filterIndexed { index, value ->
            index < input.lastIndex - 2 &&
                    (input[index] + input[index + 1] + input[index + 2]) <
                    (input[index + 1] + input[index + 2] + input[index + 3])
        }.size)
    }


    @Test
    fun test() {
        getNumberOfIncrements1()
        getNumberOfIncrements2()
    }
}
