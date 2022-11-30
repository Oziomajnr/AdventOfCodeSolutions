package com.example.pgntogifconverter.util.day16

import java.io.File
import java.nio.charset.Charset

class InputParser {
    val inputRanges = mutableMapOf<String, MutableList<Input>>()
    fun addInputToRanges(input: String) {
        val ticketFields = input.split(": ")
        ticketFields[1].split(" or ").forEach {
            val startAndEndRangeArray = it.split('-').map {
                it.toInt()
            }
            if (inputRanges[ticketFields[0]] == null) {
                inputRanges[ticketFields[0]] =
                    mutableListOf(Input(startAndEndRangeArray[0], startAndEndRangeArray[1]))
            } else {
                inputRanges[ticketFields[0]]!!.add(
                    Input(
                        startAndEndRangeArray[0],
                        startAndEndRangeArray[1]
                    )
                )
            }
        }
    }


    fun processInput() {
            File("input.txt").readText(
                Charset.defaultCharset()
            ).split('\n').forEach {
                addInputToRanges(it)
            }
    }

    val myTicket = listOf(
        163,
        151,
        149,
        67,
        71,
        79,
        109,
        61,
        83,
        137,
        89,
        59,
        53,
        179,
        73,
        157,
        139,
        173,
        131,
        167
    )

    fun processNearbyTickets(): Long {
        var result = 1L
        val excludedRowIndex = mutableSetOf<Int>()
        val nearbyTickets =
            File("nearby_tickets.txt").readLines()
        nearbyTickets.forEachIndexed { index, value ->
            value.split(',').map {
                it.toInt()
            }.forEach {
                var foundMatch = false
                for (range in inputRanges) {
                    for (inputList in inputRanges.values) {
                        for (rangeValue in inputList) {
                            if (it >= rangeValue.start && it <= rangeValue.end) {
                                foundMatch = true
                            }
                        }
                    }
                }
                if (!foundMatch) {
                    excludedRowIndex.add(index)
                }
            }
        }
        val validTickets = nearbyTickets.filterIndexed { index, value ->
            !excludedRowIndex.contains(index)
        }

        val numberOFFields = validTickets.first().split(',').size
        val possibleFields = inputRanges.keys.toMutableSet()
        val foundIndex = mutableSetOf<Int>()
        while (possibleFields.size > 0) {
            for (x in 0 until numberOFFields) {
                if (!foundIndex.contains(x)) {
                    val possibleFieldsCopy = mutableSetOf<String>()
                    possibleFieldsCopy.addAll(possibleFields)
                    for (ticket in validTickets) {
                        val fieldValue = ticket.split(',')[x].toInt()
                        for (field in possibleFields) {
                            var numberOfMisMatch = 0
                            for (input in inputRanges[field]!!) {
                                if (fieldValue < input.start || fieldValue > input.end) {
                                    numberOfMisMatch++
                                }
                            }
                            if (numberOfMisMatch > 1) {
                                possibleFieldsCopy.remove(field)
                            }
                        }
                    }

                    if (possibleFieldsCopy.size == 1) {
                        foundIndex.add(x)
                        if (possibleFieldsCopy.first().startsWith("departure")) {
                            result *= myTicket[x]
                        }
                        possibleFields.removeAll(possibleFieldsCopy)
                    }
                }
            }
        }
        return result
    }

}

data class Input(val start: Int, val end: Int)