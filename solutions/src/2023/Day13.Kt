fun main() {
    measureTimeMillis {
        input.split("\n\n").map {
            val lines = it.split("\n")
            val lir = lines.rotate()

            val a = (0..lines.lastIndex).windowed(2).map {
                findMirror(it.first(), it.last(), lines, it.first(), 0, null, true)
            }.max()
            val b = (0..lir.lastIndex).windowed(2).map {
                findMirror(it.first(), it.last(), lir, it.first(), 0, null, false)
            }.max()
            val oldIndex = if (a != 0) true to a else false to b
            val a1 = (0..lines.lastIndex).windowed(2).map {
                findMirror(it.first(), it.last(), lines, it.first(), 1, oldIndex, true)
            }.max()
            val b1 = (0..lir.lastIndex).windowed(2).map {
                findMirror(it.first(), it.last(), lir, it.first(), 1, oldIndex, false)
            }.max()

            a1 to b1
        }.fold(0 to 0) { a, b -> a.first + b.first to a.second + b.second }
            .let { it.first * 100 + it.second }
            .let(::println)
    }.let(::println)
}

fun findMirror(
    index1: Int, index2: Int, lines: List<String>, startIndex: Int, diffsAllowed: Int,
    oldIndex: Pair<Boolean, Int>?,
    vertical: Boolean
): Int {
    fun getDiff() = lines[index1].mapIndexed { index, c ->
        if (c != lines[index2][index]) {
            1
        } else 0
    }.sum()
    if (index1 < 0 || index2 > lines.lastIndex) {
        if (vertical to startIndex + 1 == oldIndex) return 0
        return startIndex + 1
    }
    if (lines[index1] == lines[index2]) return findMirror(
        index1 - 1,
        index2 + 1,
        lines,
        startIndex,
        diffsAllowed,
        oldIndex,
        vertical
    ) else if (getDiff() <= diffsAllowed) {
        return findMirror(
            index1 - 1,
            index2 + 1,
            lines,
            startIndex,
            0,
            oldIndex,
            vertical
        )
    }
    return 0
}
