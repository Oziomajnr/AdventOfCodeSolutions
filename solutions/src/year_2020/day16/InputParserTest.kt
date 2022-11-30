package com.example.pgntogifconverter.util.day16

import org.junit.Test

class InputParserTest {

    @Test
    fun toInput() {
        val parser = InputParser()
        parser.processInput()
        val result = parser.processNearbyTickets()
        println(result)
    }
}