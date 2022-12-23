package year_2022.day23

import solve
import year_2022.common.Position

fun main() = solve { lines ->
    val elves = parseInput(lines).toMutableSet()

    repeat(1000000) { round ->
        val proposals = mutableMapOf<Position, MutableList<Position>>()

        elves.forEach { position ->
            val proposal = position.getProposal(elves, round)
            if (proposal != null) {
                if (proposals[proposal] == null) {
                    proposals[proposal] = mutableListOf(position)
                } else {
                    proposals[proposal]!!.add(position)
                }
            }
        }
        if (proposals.filter {
                it.value.size == 1
            }.isEmpty()) {
            return@solve round + 1
        }

        proposals.filter {
            it.value.size == 1
        }.forEach {
            elves.add(it.key)
            elves.remove(it.value.single())
        }
    }
}
