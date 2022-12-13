package year_2022.day13

import solve
import java.util.*
import kotlin.math.max


fun main() = solve { lines ->
    val pairs = lines.joinToString("\n").split("\n\n").filter { it.isNotBlank() }.map {
        it.parseToPair().run {
            listOf(this.first, this.second)
        }
    }.flatten().sortedWith { a, b ->
        when (compareValue(a to b)) {
            true -> {
                -1
            }

            false -> {
                1
            }

            else -> {
                0
            }
        }
    }

    pairs.mapIndexed { index, value ->
        if(value.toString() == "[[6]]" || value.toString() == "[[2]]") {
            index + 1
        } else {
            1
        }
    }.reduce{
        acc, value -> acc * value
    }

}

fun compareValue(pairs: Pair<Item, Item>): Boolean? {
    val firstItem = pairs.first
    val secondItem = pairs.second

    when {
        firstItem is Item.DigitItem && secondItem is Item.DigitItem -> {
            return if (firstItem.value < secondItem.value) {
                true
            } else if (firstItem.value > secondItem.value) {
                false
            } else {
                null
            }
        }

        firstItem is Item.ListItem && secondItem is Item.ListItem -> {
            repeat((0..max(firstItem.values.lastIndex, secondItem.values.lastIndex)).count()) {
                if (it > firstItem.values.lastIndex) {
                    return true
                } else if (it > secondItem.values.lastIndex) {
                    return false
                } else {
                    val result = (compareValue(firstItem.values[it] to secondItem.values[it]))
                    if (result == true) {
                        return true
                    } else if (result == false) {
                        return false
                    }
                }
            }
        }

        firstItem is Item.DigitItem && secondItem is Item.ListItem -> {
            return compareValue(Item.ListItem(LinkedList(listOf(firstItem))) to secondItem)
        }

        firstItem is Item.ListItem && secondItem is Item.DigitItem -> {
            return compareValue(firstItem to Item.ListItem(LinkedList(listOf(secondItem))))
        }
    }
    return null
}

fun String.parseToPair(): Pair<Item, Item> {
    return split("\n").run {
        (this[0].parseSingleItem() ?: Item.ListItem(LinkedList())) to (this[1].parseSingleItem() ?: Item.ListItem(
            LinkedList()
        ))
    }
}

fun String.parseSingleItem(): Item? {
    val stack = Stack<Char>()
    val itemValueStack = Stack<Item.ListItem>()
    var currentItemValue: Item.ListItem? = null

    this.forEachIndexed { index: Int, character: Char ->
        when (character) {
            ']' -> {
                val result = mutableListOf<Item>()
                var previousCharacter: String? = null
                while (stack.isNotEmpty() && stack.peek() != '[') {
                    if (stack.peek().digitToIntOrNull() != null) {
                        previousCharacter = stack.pop().toString() + (previousCharacter ?: "")
                    } else {
                        if (previousCharacter != null) {
                            result.add(Item.DigitItem(previousCharacter.toInt()))
                        }
                        previousCharacter = null
                        stack.pop()
                    }
                }
                if (previousCharacter != null) {
                    result.add(Item.DigitItem(previousCharacter.toInt()))
                }
                currentItemValue = itemValueStack.pop()
                currentItemValue!!.values.addAll(result.reversed())

                if (itemValueStack.isNotEmpty()) {
                    itemValueStack.peek().values.add(currentItemValue!!)
                }
                stack.pop()
            }

            '[' -> {
                stack.push(character)
                itemValueStack.push(Item.ListItem(LinkedList()))
            }

            ',' -> {
                var previousCharacter: String? = null
                while (stack.isNotEmpty() && stack.peek().digitToIntOrNull() != null) {
                    previousCharacter = stack.pop().toString() + (previousCharacter ?: "")
                }
                if (previousCharacter != null) {
                    itemValueStack.peek().values.add(Item.DigitItem(previousCharacter.toInt()))
                }
                stack.push(character)
            }

            else -> {
                stack.push(character)
            }
        }
    }
    return currentItemValue
}

sealed interface Item {
    data class ListItem(val values: LinkedList<Item>) : Item {
        override fun toString(): String {
            return "[" + values.joinToString(",") + "]"
        }
    }

    data class DigitItem(val value: Int) : Item {
        override fun toString(): String {
            return value.toString()
        }
    }
}