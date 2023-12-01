fun main() {
    val filePath = Paths.get("input1.txt")
    val input = Files.readAllLines(filePath)
    val digitMap = (1..9).groupBy { it }.mapKeys { it.key.toString() }.mapValues { it.value.first() } +
            ("one,two,three,four,five,six,seven,eight,nine").split(",")
                .mapIndexed { index, value -> value to index + 1 }.toMap()

    val result = input.sumOf { line ->
        val matched = Regex("""(?=(\d|eight|one|two|three|four|five|six|seven|nine))""").findAll(line).toList()
        (digitMap[matched.first().groups[1]!!.value]!! * 10 + digitMap[matched.last().groups[1]!!.value]!!).toInt()
    }
    println(result)
}
