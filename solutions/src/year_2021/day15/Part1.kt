package com.example.pgntogifconverter.util.day15

import org.junit.Test
import java.io.File
import java.util.*

class Part1 {
    private val caves =
        File("/Users/oziomaogbe/AndroidStudioProjects/PgnToGifConverter/app/src/test/java/com/example/pgntogifconverter/util/day15/getInput.txt").readLines()
            .mapIndexed { y, value ->

                value.mapIndexed { x, charValue ->
                    Cave(x, y, charValue.digitToInt())
                }
            }



    @Test
    fun test() {
       println(CaveSearcher(caves).findBestPathFromTopToBottomEdge())
    }
}


