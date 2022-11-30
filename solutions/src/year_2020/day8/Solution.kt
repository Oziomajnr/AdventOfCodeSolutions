import java.io.File

fun main(args: Array<String>) {
   println(fixInfiniteLoopCodeAndReturnAcc())
}

fun fixInfiniteLoopCodeAndReturnAcc(): Long {
    val instructions = parseInstructions()
    var currentIndex = 0
    var hasInfiniteLoop: Boolean
    var acc: Long
    var changedCurrentIndex = false

    do {

        val result = detectInfiniteLoopInProgram(instructions)
        if (changedCurrentIndex && currentIndex - 1 >= 0) {
            if (instructions[currentIndex - 1].instruction == "jmp") {
                instructions[currentIndex - 1].instruction = "nop"
            } else if (instructions[currentIndex - 1].instruction == "nop") {
                instructions[currentIndex - 1].instruction = "jmp"
            }
        }
        hasInfiniteLoop = result.first
        acc = result.second
        if (hasInfiniteLoop) {
            changedCurrentIndex = true
            if (instructions[currentIndex].instruction == "jmp") {
                instructions[currentIndex].instruction = "nop"
            } else if (instructions[currentIndex].instruction == "nop") {
                instructions[currentIndex].instruction = "jmp"
            }
            currentIndex++
        } else {
            changedCurrentIndex = false
        }
    } while (hasInfiniteLoop)

    return acc

}

fun detectInfiniteLoopInProgram(instructions: List<Instruction>): Pair<Boolean, Long> {
    var acc = 0L
    val setOfIndex = mutableSetOf<Int>()
    var currentIndex = 0
    var hasInfiniteLoop = false
    while (!hasInfiniteLoop && currentIndex <= instructions.lastIndex) {
        setOfIndex.add(currentIndex)
        val instruction = instructions[currentIndex]
        when (instruction.instruction) {
            "nop" -> {
                currentIndex++
            }

            "acc" -> {
                acc += instruction.arg
                currentIndex++
            }

            "jmp" -> {
                currentIndex += instruction.arg
            }
        }
        hasInfiniteLoop = setOfIndex.contains(currentIndex)
    }
    return Pair(hasInfiniteLoop, acc)
}

fun parseInstructions(): List<Instruction> {
    val instructions = mutableListOf<Instruction>()
    readFileAsLinesUsingUseLines().forEach {
        val singleInstruction = it.split(" ")
        val instruction = Instruction(singleInstruction[0], singleInstruction[1].toInt())
        instructions.add(instruction)
    }
    return instructions
}

fun readFileAsLinesUsingUseLines(): List<String> = File("input.txt").useLines { data ->
    data.toList()
}

data class Instruction(var instruction: String, val arg: Int)
