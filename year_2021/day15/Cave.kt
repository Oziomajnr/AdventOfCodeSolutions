package com.example.pgntogifconverter.util.day15

class Cave(val x: Int, val y: Int, val value: Int) {
    val siblings = mutableListOf<Cave>()

    override fun toString(): String {
        return value.toString()
    }
}