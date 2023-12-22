package com.ozioma

fun main() {
    val (lines, testCases) = input.trim().split("\n\n")
    val workFlows = lines.split("\n").map {
        it.dropLast(1).split("{")
    }.associateBy { it[0] }.mapValues { it.value[1] }
    testCases.split("\n").map {
        it.drop(1).dropLast(1).split(",").map {
            it.split("=").apply {
                this[0] to this[1].toLong()
            }
        }.associateBy { it[0] }.mapValues { it.value[1].toLong() }
    }.filter {
        val r =
            evaluateWorkFlow(it, workFlows, workFlows["in"]!!)
        r == "A"
    }.sumOf {
        it.values.sum()
    }.also {
        println(it)
    }

}

fun evaluateWorkFlow(testCases: Map<String, Long>, workFlows: Map<String, String>, workFlowValue: String): String {
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
    return if (ruleValue.apply()) {
        parseOutput(leftOutput, testCases, workFlows)
    } else {
        parseOutput(rightOutput, testCases, workFlows)
    }


}

fun parseOutput(output: String, testCases: Map<String, Long>, workFlows: Map<String, String>): String {
    return when {
        output == "R" || output == "A" -> output
        workFlows[output] != null -> evaluateWorkFlow(testCases, workFlows, workFlows[output]!!)
        else -> evaluateWorkFlow(testCases, workFlows, output)
    }
}

data class Rule(val input1: Long, val input2: Long, val condition: Char) {
    fun apply(): Boolean {
        return when (condition) {
            '<' -> input1 < input2
            '>' -> input1 > input2
            else -> error("Unknown condition $condition")
        }
    }
}
