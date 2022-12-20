package year_2022.day16

import solve

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
    -1

}

data class Valve(val name: String, val pressureRate: Int) {
    val connectedTo = mutableSetOf<Valve>()
}
