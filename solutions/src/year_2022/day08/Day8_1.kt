package year_2022.day08

import solve

fun main() = solve { lines ->
    val grid = lines.mapIndexed { x, trees ->
        trees.mapIndexed { y, tree ->
            Tree(tree.digitToInt(), x, y)
        }
    }
    (getVisibleTrees(grid) + getVisibleTrees(grid.transpose {
        Tree(-1, -1, -1)
    })).size
}

private fun getVisibleTrees(trees: List<List<Tree>>): Set<Tree> {
    return trees.map { treeRow ->
        treeRow.takeLast(treeRow.size - 1).fold(listOf(treeRow.first())) { visibleTrees, tree ->
            foldTrees(visibleTrees, tree)
        } + treeRow.take(treeRow.size - 1).reversed().fold(listOf(treeRow.last())) { visibleTrees, tree ->
            foldTrees(visibleTrees, tree)
        }
    }.flatten().toSet()
}

private fun foldTrees(visibleTrees: List<Tree>, tree: Tree): List<Tree> {
    return if (tree.height > visibleTrees.maxBy { it.height }.height) {
        buildList {
            addAll(visibleTrees + tree)
        }
    } else {
        visibleTrees
    }
}

data class Tree(val height: Int, val positionX: Int, val positionY: Int)

fun <T> List<List<T>>.transpose(initializer: () -> T): List<List<T>> {
    return List(this.first().size) {
        List(this.size) {
            initializer()
        }
    }.mapIndexed { x, ts ->
        List(ts.size) { y ->
            this[y][x]
        }
    }
}

