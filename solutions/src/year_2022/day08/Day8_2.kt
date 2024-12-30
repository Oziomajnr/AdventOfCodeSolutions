package year_2022.day08

import common.transpose
import solve

fun main() = solve { lines ->
    val grid = lines.mapIndexed { x, trees ->
        trees.mapIndexed { y, tree ->
            Tree(tree.digitToInt(), x, y)
        }
    }

    grid.forEachIndexed { index, trees ->
        trees.forEachIndexed { index1, tree ->
            findDistanceForTree(trees, index1)
        }
        trees.reversed().forEachIndexed { index1, tree ->
            findDistanceForTree(trees.reversed(), index1)
        }
    }
    grid.transpose {
        Tree(-1, -1, -1)
    }.forEachIndexed { index, trees ->
        trees.forEachIndexed { index1, tree ->
            findDistanceForTree(trees, index1)
        }
        trees.reversed().forEachIndexed { index1, tree ->
            findDistanceForTree(trees.reversed(), index1)
        }
    }
    cache.maxBy {
        it.value
    }
}

val cache = mutableMapOf<Tree, Int>()

private fun findDistanceForTree(
    trees: List<Tree>, indexY: Int
) {
    var result = 0
    if (indexY < trees.lastIndex) {
        var copy = indexY
        while (copy < trees.lastIndex && trees[copy + 1].height < trees[indexY].height) {
            result++
            copy++
        }
        if (copy < trees.lastIndex) result++
    }

    cache[trees[indexY]] = (cache[trees[indexY]] ?: 1) * result
}