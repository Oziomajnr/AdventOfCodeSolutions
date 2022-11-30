fun main() {
    val input = listOf(16,1,0,18,12,14,19)

    val numberTurnMap = mutableMapOf<Int, MutableList<Int>>()
    input.forEachIndexed { index, value ->
        numberTurnMap[value] = mutableListOf(index + 1)
    }
    var numberOfNumbersSpoken = input.size + 1

    var lastSpokenNumber = input.last()

    while (numberOfNumbersSpoken <= 30000000) {
        when {
            numberTurnMap[lastSpokenNumber] == null -> {
                error("Invalid state")
            }
            numberTurnMap[lastSpokenNumber]!!.size < 2 -> {
                lastSpokenNumber = 0
                addTurnForNumber(numberTurnMap, lastSpokenNumber, numberOfNumbersSpoken)
            }
            numberTurnMap[lastSpokenNumber]!!.size == 2 -> {
                val currentSpokenNumber = numberTurnMap[lastSpokenNumber]!!.run {
                    this.last() - this.first()
                }
                addTurnForNumber(numberTurnMap, currentSpokenNumber, numberOfNumbersSpoken)
                lastSpokenNumber = currentSpokenNumber
            }
            else -> {
                error("Invalid state")
            }
        }
        numberOfNumbersSpoken++
    }
    println(lastSpokenNumber)
}

private fun addTurnForNumber(
    numberTurnMap: MutableMap<Int, MutableList<Int>>,
    number: Int,
    turn: Int
) {
    if (numberTurnMap[number] == null) {
        numberTurnMap[number] = mutableListOf(turn)
        return
    }
    val turnList = numberTurnMap[number]!!
    if (turnList.size > 1) {
        turnList.removeFirst()
    }
    turnList.add(turn)
    numberTurnMap[number] = turnList
}
