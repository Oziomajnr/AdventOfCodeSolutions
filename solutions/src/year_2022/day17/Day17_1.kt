package year_2022.day17

import solve

fun main() = solve { lines ->
    val input = lines[0].toCharArray()
    val blocks = Array(5) { index ->
        when (index) {
            0 -> {
                Array(1) {
                    charArrayOf('#', '#', '#', '#')
                }
            }

            1 -> {
                Array(3) {
                    when (it) {
                        1, 3 -> {
                            charArrayOf('.', '#', '.')
                        }

                        2 -> {
                            charArrayOf('#', '#', '#')
                        }

                        else -> {
                            error(" Invalid index")
                        }
                    }
                }
            }

            2 -> {
                Array(3) {
                    when (it) {
                        1, 2 -> {
                            charArrayOf('.', '.', '#')
                        }

                        3 -> {
                            charArrayOf('#', '#', '#')
                        }

                        else -> {
                            error(" Invalid index")
                        }
                    }
                }
            }

            3 -> {
                Array(4) {
                    charArrayOf('#')
                }
            }

            4 -> {
                Array(2) {
                    charArrayOf('#', '#')
                }
            }

            else -> {
                error(" Invalid index")
            }
        }
    }

    val chamber = Array<CharArray>(8088) {
        charArrayOf('.', '.', '.', '.', '.', '.', '.')
    }
    for (i in 0 until 2022) {

    }
    -1
}

private fun simulateBlockMovement(
    pushIndex: Int, block: Array<CharArray>, chamber: Array<CharArray>, pushArray: CharArray
): Int {
    var currentPushIndex = pushIndex
    while (!motionIsBlocked(block, chamber, pushArray[currentPushIndex])) {

        currentPushIndex++
    }
    return currentPushIndex
}

private fun motionIsBlocked(block: Array<CharArray>, chamber: Array<CharArray>, direction: Char): Boolean {
    return false
}
