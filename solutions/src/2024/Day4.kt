package `2024`

import year_2022.day08.transpose
import java.io.File

//fun main() {
//    val input = File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText()
//    val lines = input.split("\n").map { it.toCharArray().toList() }
//    val init = { ' ' }
//    val transposed = lines.transpose(init)
//    val result = lines.sumOf {
//        it.matches()
//    } + transposed.sumOf {
//        it.matches()
//    } + lines.first().indices.sumOf { index ->
//        var x = 0
//        var y = index
//        buildList {
//            while (x <= lines.lastIndex && y <= lines[0].lastIndex) {
//                add(lines[x][y])
//                x++
//                y++
//            }
//        }.matches() + buildList {
//            x = 0
//            y = index
//            while (y != 0 && x <= lines.lastIndex && y <= lines[0].lastIndex) {
//                add(lines[y][x])
//                x++
//                y++
//            }
//        }.matches()
//    } + lines.first().indices.sumOf { index ->
//        var x = lines.first().lastIndex - index
//        var y = 0
//
//        var x1 = lines.first().lastIndex
//        var y1 = index
//        buildList {
//            while (x >= 0 && y <= lines[0].lastIndex) {
//                add(lines[x][y])
//                x--
//                y++
//            }
//        }.matches() + buildList {
//            if (x1 == lines.first().lastIndex && y1 == 0) return@buildList
//            while (x1 >= 0 && y1 <= lines[0].lastIndex) {
//                add(lines[x1][y1])
//                x1--
//                y1++
//            }
//        }.matches()
//    }
//    println(result)
//
//}
fun main() {
    val input =
        File("/Users/oogbe/IdeaProjects/AdventOfCodeSolutions/solutions/src/2024/input.txt").readText().split("\n")
    input.windowed(3).map {
        val x = it[0].windowed(3)
        val y = it[1].windowed(3)
        val z = it[2].windowed(3)
       val k = x.filterIndexed{ index, s ->
            x[index][0] == 'M'&&
            x[index][2] == 'S'&&
            y[index][1] == 'A'&&
            z[index][0] == 'M'&&
            z[index][2] == 'S' ||
                    x[index][0] == 'S'&&
                    x[index][2] == 'M'&&
                    y[index][1] == 'A'&&
                    z[index][0] == 'S'&&
                    z[index][2] == 'M' ||
                    x[index][0] == 'S'&&
                    x[index][2] == 'S'&&
                    y[index][1] == 'A'&&
                    z[index][0] == 'M'&&
                    z[index][2] == 'M' ||

                    x[index][0] == 'M'&&
                    x[index][2] == 'M'&&
                    y[index][1] == 'A'&&
                    z[index][0] == 'S'&&
                    z[index][2] == 'S'

        }.size
        k
    }.sum().also { println(it) }

}

fun List<Char>.matches() = windowed(4).count {
    it.joinToString("").run { this == "XMAS" || this == "XMAS".reversed() }
}
