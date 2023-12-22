
fun main() {
    val (lines, testCases) = input.trim().split("\n\n")
    val workFlows = lines.split("\n").map {
        it.dropLast(1).split("{")
    }.associateBy { it[0] }.mapValues { it.value[1] }
    val range = 1..4000L
    println(evaluateWorkFlow(
        listOf("x", "m", "a", "s").map { it to range }.associate { it.first to it.second },
        workFlows,
        workFlows["in"]!!
    )
    )
}

fun evaluateWorkFlow(testCases: Map<String, LongRange>, workFlows: Map<String, String>, workFlowValue: String): Long {
    val rule = workFlowValue.substringBefore(":")
    val output = workFlowValue.substringAfter(":")
    val (input1, input2) = rule.split("<", ">", "=")
    val condition = when {
        rule.contains("<") -> '<'
        rule.contains(">") -> '>'
        else -> error("Unknown condition")
    }
    val ruleValue = Rule(testCases[input1]!!, input2.toLong(), condition)
    val leftOutput = output.substringBefore(",")
    val rightOutput = output.substringAfter(",")
    val (leftRange, rightRange) = ruleValue.splitRange()
    return parseOutput(leftOutput, testCases.mapValues {
        if (it.key == input1) {
            leftRange
        } else {
            it.value
        }
    }, workFlows) + parseOutput(
        rightOutput,
        testCases.mapValues {
            if (it.key == input1) {
                rightRange
            } else {
                it.value
            }
        },
        workFlows
    )
}

fun parseOutput(
    output: String,
    testCases: Map<String, LongRange>,
    workFlows: Map<String, String>
): Long {
    return when {
        output == "A" -> testCases.values.map { it.count().toLong() }.filter { it != 0L }.reduce(Long::times)
        output == "R" -> 0L
        workFlows[output] != null -> evaluateWorkFlow(testCases, workFlows, workFlows[output]!!)
        else -> evaluateWorkFlow(testCases, workFlows, output)
    }
}

data class Rule(val input1: LongRange, val input2: Long, val condition: Char) {
    fun splitRange(): Pair<LongRange, LongRange> {
        return when (condition) {
            '<' -> (input1.first until input2) to (input2..input1.last)
            '>' -> (input2 + 1..input1.last) to (input1.first..input2)
            else -> error("Unknown condition $condition")
        }
    }
}
