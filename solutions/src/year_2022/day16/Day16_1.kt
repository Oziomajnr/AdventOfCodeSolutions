package year_2022.day16

import solve
import java.util.LinkedList

fun main() = solve { lines ->
    val nodeConnection = mutableMapOf<String, Set<String>>()
    val valves = lines.map {
        val splitString = it.split(Regex("; tunnels lead to valves |; tunnel leads to valve "))
        val (name: String, rate: String) = splitString[0].drop(6).split(" has flow rate=")
        nodeConnection[name] = splitString[1].split(", ").toSet()
        Valve(name, rate.toInt())
    }.toSet()
    valves.forEach { valve ->
        val connectedValves = nodeConnection[valve.name]!!
        valves.filter {
            connectedValves.contains(it.name)
        }.forEach { connectedValve ->
            valve.connectedTo.add(connectedValve)
        }
    }
    println(valves)
    val startingValve = valves.find { it.name == "AA" }!!

    searchValves(startingValve)

}

val resultCache = mutableMapOf<String, Long>()
val pathCache = mutableSetOf<String>()

private fun searchValves(startingValve: Valve): Long {
    val queue = LinkedList<SearchState>()
    queue.add(SearchState("", startingValve, 1, ""))
    while (queue.isNotEmpty()) {
        val currentState = queue.poll()
//        if (pathCache.contains("${currentState.visitedValves},${currentState.openedValves}")) {
//            continue
//        } else {
//            pathCache.add("${currentState.visitedValves},${currentState.openedValves}")
//        }
        if (currentState.minutes >= 5) {
            val result = currentState.openedValves.split("|").filter { it.isNotBlank() }.sumOf {
                it.split(",").run {
                    this[1].toInt() * this[2].toLong()
                }
            }
            val key = currentState.visitedValves
            resultCache[key] = result
            println("Found result for Key: $key value: $result")

        } else {
            if (currentState.currentValve.pressureRate > 0) {
                //you can decide to open it before moving to the next valve or leave it closed before moving to the next valve
                currentState.currentValve.connectedTo.forEach { neighbourValve ->
                    val openedValves = if (!currentState.openedValves.split("|").map {
                            it.split(",").first()
                        }.toSet().contains(currentState.currentValve.name)) {
                        "${currentState.openedValves}|${currentState.currentValve.name},${currentState.currentValve.pressureRate},${currentState.minutes}"
                    } else {
                        currentState.openedValves
                    }
                    queue.offer(
                        SearchState(
                            "${currentState.visitedValves},${currentState.currentValve.name}",
                            neighbourValve,
                            currentState.minutes + 1,
                            openedValves
                        )
                    )
                }
            }
            currentState.currentValve.connectedTo.forEach { neighbourValve ->
                queue.offer(
                    SearchState(
                        "${currentState.visitedValves},${currentState.currentValve.name}",
                        neighbourValve,
                        currentState.minutes + 1,
                        "${currentState.openedValves}|${currentState.currentValve.name},${currentState.currentValve.pressureRate},${currentState.minutes}"

                    )
                )
            }
        }
    }
    return resultCache.maxOf {
        it.value
    }
}

data class SearchState(
    val visitedValves: String, val currentValve: Valve, val minutes: Int, val openedValves: String
)

data class Valve(val name: String, val pressureRate: Int) {
    val connectedTo = mutableSetOf<Valve>()
}
