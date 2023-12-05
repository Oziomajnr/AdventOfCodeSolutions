val input = File("input1.txt").readText()
val lines = input.split("\n").dropLast(1).map {
    it.split(": ")[1].trim()
}
fun main() {
    println(lines.mapIndexed { index, s ->
       val sum= processLinePart2(s, index)
        sum
    }.sum())
}
val cache = mutableMapOf<Int, Int>()
fun processLinePart2(line: String, lineNumber: Int): Int {
    cache[lineNumber]?.let { return it }
    cache[lineNumber] =
        getMatchingCardForLine(line).let { numberOfMatchingCards ->
             1 + (lineNumber + 1..lineNumber + numberOfMatchingCards).sumOf {
                processLinePart2(lines[it], it)
            }
        }
    return cache[lineNumber]!!
}
fun processLinePart1(line: String, lineNumber: Int): Int {
     getMatchingCardForLine(line).let {numberOfMatchingCards ->
         return  2.0.pow(numberOfMatchingCards.toDouble()-1).toInt()
    }
}
fun getMatchingCardForLine(line: String): Int {
    return line.split(" | ").let {
        it[0].trim().split(Regex("\\s+"))
            .map { it.trim().toInt() }.toSet()
            .intersect(it[1].trim().split(Regex("\\s+")).map { it.trim().toInt() }.toSet()).size
    }
}
