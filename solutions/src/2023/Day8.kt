input = File("src/main/kotlin/com/ozioma/input1.txt").readText()

fun main() {
    val (instructions, lines) = input.trim().split("\n\n")
    val tree =
        lines.split("\n").map { "(\\d+|[A-Z])+".toRegex().findAll(it).map { it.value }.toList() }
            .associateBy {
                it[0]
            }.mapValues { (key, value) ->
                Pair(value[1], value[2])
            }

    println(lcm(tree.filter {
        it.key.endsWith('A')
    }.map {
        findEnd2(tree, listOf(it.key), instructions, 0)
    }))
}

fun lcm(input: List<Long>): Long {
    val min = input.min()
    for (x in 1..Long.MAX_VALUE) {
        if (input.all { ((x * min) % it) == 0L }) {
            return x * min
        }
    }
    return input.reduce { acc, l -> acc * l }
}

tailrec fun findEnd1(
    tree: Map<String, Pair<String, String>>,
    start: String,
    instructions: String,
    currentIndex: Int
): Int {
    val (left, right) = tree[start]!!
    val nextStart = if (instructions[(currentIndex % instructions.length)] == 'L') left else right
    if (nextStart == "ZZZ") {
        return currentIndex + 1
    }
    return findEnd1(tree, nextStart, instructions, currentIndex + 1)
}

tailrec fun findEnd2(
    tree: Map<String, Pair<String, String>>,
    starts: List<String>,
    instructions: String,
    currentIndex: Long
): Long {
    starts.map { tree[it]!! }.map {
        if (instructions[(currentIndex % instructions.length).toInt()] == 'L') it.first else it.second
    }.run {
        return if (this.all { it.last() == 'Z' }) (currentIndex + 1)
        else findEnd2(tree, this, instructions, currentIndex + 1)
    }
}
