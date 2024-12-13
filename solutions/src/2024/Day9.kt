package `2024`

import java.io.File
import java.util.LinkedList
import java.util.Stack

fun main() {
    val input = File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText()
    println(input.map { it.digitToInt().toLong() }.sum())
    var abs = 0
    val diskInputs = input.mapIndexed { index, c ->
        val x = if (index % 2 == 0) FileBlock(index / 2, c.digitToInt(), abs) else FreeSpace(c.digitToInt(), abs)
        abs += c.digitToInt()
        x
    }
    val deque = LinkedList<DiskInput>().also { it.addAll(diskInputs) }
    var result = 0L
    while (deque.isNotEmpty()) {
        val back = deque.pollLast()
        if (back is FreeSpace) continue
        val diskBlock = back as FileBlock
        val temp = Stack<DiskInput>()
        var found = false
        while (deque.isNotEmpty()) {
            val front = deque.pollFirst()
            if (front is FreeSpace && front.frequency >= diskBlock.frequency) {
                if (front.frequency > diskBlock.frequency) {
                    deque.addFirst(
                        FreeSpace(
                            front.frequency - diskBlock.frequency,
                            front.absIndex + diskBlock.frequency
                        )
                    )
                }
                deque.addFirst(FileBlock(back.id, back.frequency, front.absIndex))
                found = true
                break
            } else {
                temp.push(front)
            }
        }
        while (temp.isNotEmpty()) {
            deque.addFirst(temp.pop())
        }
        if(!found) {
            (diskBlock.absIndex until (diskBlock.absIndex + diskBlock.frequency)).forEach {
                result += it * diskBlock.id
            }
        }
    }
    println(result)

}

sealed interface DiskInput {
    val frequency: Int
    val absIndex: Int
}

data class FileBlock(val id: Int, override val frequency: Int, override val absIndex: Int) : DiskInput
data class FreeSpace(override val frequency: Int, override val absIndex: Int) : DiskInput