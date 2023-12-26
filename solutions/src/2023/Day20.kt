package com.ozioma

import java.util.LinkedList

fun main() {
//    val range = 1..1000L //part1
    val range = 1..100000000L //part2
    val lines = input.split("\n")
    val modules = lines.map {
        val name = it.split(" -> ")[0]
        when {
            name == "broadcaster" -> Module.BroadcasterModule(name)
            name.startsWith("%") -> Module.FlipFlopModule(name.drop(1))
            name.startsWith("&") -> Module.ConjunctionModule(name.drop(1))
            else -> Module.OutputModule(name)
        }
    }.associateBy { it.name }.toMutableMap()

    lines.forEach {
        val (parent, children) = it.split(" -> ")
        val parentModule = modules[parent.removePrefix("%").removePrefix("&")]!!

        children.split(", ").forEach {
            if (modules[it] is Module.ConjunctionModule) {
                (modules[it] as Module.ConjunctionModule).addSourceModule(parentModule.name)
            }
            modules[it]?.let { it1 -> parentModule.children.add(it1) } ?: run {
                parentModule.children.add(Module.OutputModule(""))
            }
        }
    }

    modules["button"] = Module.BroadcasterModule("button").also {
        it.children.add(modules["broadcaster"]!!)
    }
    modules.forEach {
        print("${it.key} ")
        it.value.children.map { it.name }.forEach { print(" $it") }
        println()

    }

    data class QueueEntry(val sourceModule: String, val isHighPulse: Boolean, val destinationModule: Module)

    val queue = LinkedList<QueueEntry>()
    var lowPulseCount = 0L
    var highPulseCount = 0L
    val resultSet = mutableMapOf<String, Long>()
    (1L..10000000000L).forEach { numberOfButtonPress ->
        queue.offer(QueueEntry("_", false, modules["button"]!!))
        while (queue.isNotEmpty()) {
            val (sourceModule, isHighPulse, destinationModule) = queue.poll()
            val nextPulseSignals = destinationModule.processPulse(sourceModule, isHighPulse)
            if (destinationModule.name == "sq" && (destinationModule as Module.ConjunctionModule).sourceModules.values.any { it }) {
                destinationModule.sourceModules.filter {
                    it.value
                }.forEach {
                    if (resultSet[it.key] == null) {
                        resultSet[it.key] = numberOfButtonPress
                        //part2
                        println("$numberOfButtonPress")
                    }
                }
            }
            if (resultSet.size == (modules["sq"]!! as Module.ConjunctionModule).sourceModules.size) {
                println(resultSet.values.reduce(Long::times))
                return
            }
            if (nextPulseSignals.first) {
                highPulseCount += nextPulseSignals.second.size
            } else {
                lowPulseCount += nextPulseSignals.second.size
            }
            nextPulseSignals.second.mapTo(queue) {
                QueueEntry(destinationModule.name, nextPulseSignals.first, it)
            }
        }
    }
    //part1
    println("Pulse count: ${(lowPulseCount) * highPulseCount}")
}

sealed interface Module {
    val name: String
    val children: MutableSet<Module>

    fun processPulse(sourceModule: String, isHighPulse: Boolean): Pair<Boolean, Set<Module>>

    data class BroadcasterModule(
        override val name: String,
    ) : Module {
        override val children: MutableSet<Module> = mutableSetOf()
        override fun processPulse(sourceModule: String, isHighPulse: Boolean): Pair<Boolean, Set<Module>> {
            return isHighPulse to children
        }
    }

    data class FlipFlopModule(
        override val name: String
    ) : Module {
        private var isOn: Boolean = false
        override val children: MutableSet<Module> = mutableSetOf()
        override fun processPulse(sourceModule: String, isHighPulse: Boolean): Pair<Boolean, Set<Module>> {
            if (!isHighPulse) {
                isOn = !isOn
                return isOn to children
            }
            return isOn to emptySet()
        }
    }

    data class ConjunctionModule(
        override val name: String
    ) : Module {
        override val children: MutableSet<Module> = mutableSetOf()
        val sourceModules = mutableMapOf<String, Boolean>()

        fun addSourceModule(sourceModule: String) {
            sourceModules[sourceModule] = false
        }

        override fun processPulse(sourceModule: String, isHighPulse: Boolean): Pair<Boolean, Set<Module>> {
            sourceModules[sourceModule] = isHighPulse
            return if (sourceModules.values.all { it }) {
                false to children
            } else {
                true to children
            }
        }
    }

    data class OutputModule(override val name: String) : Module {
        override val children: MutableSet<Module> = mutableSetOf()

        override fun processPulse(sourceModule: String, isHighPulse: Boolean): Pair<Boolean, Set<Module>> {
            return false to emptySet()
        }

    }
}
