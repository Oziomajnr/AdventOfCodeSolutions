package year_2022.day07

import solve
import java.util.*

fun main() = solve { inputLines ->
    parseInput(inputLines).map {
        countSize(it)
    }.sumOf {
        if (it <= 100_000) it else 0
    }
}

fun parseInput(inputLines: List<String>): Set<Folder> {
    val lines = inputLines.mapNotNull {
        when {
            it.startsWith("$") -> {
                if (it.split(" ")[1] == "cd") Input.Command("cd", it.split(" ").last())
                else {
                    null
                }
            }

            else -> {
                Input.Output(it.split(" ")[0], it.split(" ")[1])
            }
        }
    }

    val folders = Stack<Folder>()
    val distinctFolders = mutableSetOf<Folder>()
    lines.forEach {
        when (it) {
            is Input.Command -> {
                if (it.commandValue == "..") {
                    folders.pop()
                } else {
                    val newFolder = distinctFolders.find { folder ->
                        folder == Folder(it.commandValue + folders.getCurrentFolderName())
                    } ?: Folder(
                        it.commandValue + folders.getCurrentFolderName()
                    )
                    distinctFolders.add(newFolder)
                    folders.push(newFolder)
                }
            }

            is Input.Output -> {
                if (it.outputType == "dir") {
                    val newFolder = distinctFolders.find { folder ->
                        folder == Folder(it.outputValue + folders.getCurrentFolderName())
                    } ?: Folder(
                        it.outputValue + folders.getCurrentFolderName()
                    )
                    folders.peek().children.add(newFolder)
                    distinctFolders.add(newFolder)
                } else {
                    folders.peek().totalSize += it.outputType.toLong()
                }
            }
        }

    }
    return distinctFolders
}


interface Input {
    data class Command(val commandType: String, val commandValue: String) : Input
    data class Output(val outputType: String, val outputValue: String) : Input
}

val cache = mutableMapOf<Folder, Long>()
fun countSize(folder: Folder): Long {
    if (cache[folder] != null) return cache[folder]!!
    val result = folder.children.sumOf {
        countSize(it)
    } + folder.totalSize
    cache[folder] = result
    return result
}

fun Stack<Folder>.getCurrentFolderName(): String {
    return if (isEmpty()) "" else peek().name
}

data class Folder(val name: String) {
    val children: MutableList<Folder> = mutableListOf()
    var totalSize = 0L
}
