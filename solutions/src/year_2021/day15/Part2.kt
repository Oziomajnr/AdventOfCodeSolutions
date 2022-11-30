package com.example.pgntogifconverter.util.day15

import org.junit.Test
import java.io.File

class Part2 {
    private val caves = mutableListOf<List<Cave>>()

    private val fileInput =
        File("/Users/oziomaogbe/AndroidStudioProjects/PgnToGifConverter/app/src/test/java/com/example/pgntogifconverter/util/day15/getInput.txt").readLines()

    init {
        val sizeOfInput = fileInput.size
        fileInput.mapIndexed { y, value ->
            val rows = mutableListOf<Cave>()
            for (index in 0..4) {
                value.mapIndexed { x, charValue ->
                    rows.add(
                        Cave(
                            x * sizeOfInput + index,
                            y,
                            if ((charValue.digitToInt() + index) % 9 == 0) 9 else (charValue.digitToInt() + index) % 9
                        )
                    )
                }
            }
            rows
        }.apply {
            for (index in 0..4) {
                caves.addAll(
                    map {
                        it.map {
                            Cave(
                                it.x,
                                it.y + index * 100,
                                if ((it.value + index) % 9 == 0) 9 else (it.value + index) % 9
                            )
                        }.toMutableList()
                    }
                )
            }
        }
    }


    @Test
    fun test() {
        println(CaveSearcher(caves).findBestPathFromTopToBottomEdge())
    }
}


