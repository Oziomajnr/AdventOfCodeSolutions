package year_2022.day08

import common.transpose
import solve
import java.util.PriorityQueue

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
        treeRow.takeLast(treeRow.size - 1)
            .fold(PriorityQueue<Tree>().also { it.add(treeRow.first()) }) { visibleTrees, tree ->
                foldTrees(visibleTrees, tree)
            } + treeRow.take(treeRow.size - 1).reversed()
            .fold(PriorityQueue<Tree>().also { it.add(treeRow.last()) }) { visibleTrees, tree ->
                foldTrees(visibleTrees, tree)
            }
    }.flatten().toSet()
}

private fun foldTrees(visibleTrees: PriorityQueue<Tree>, tree: Tree): PriorityQueue<Tree> {
    return if (tree.height > visibleTrees.last().height) {
        PriorityQueue<Tree>().also {
            it.addAll(visibleTrees + tree)
        }
    } else {
        visibleTrees
    }
}

data class Tree(val height: Int, val positionX: Int, val positionY: Int) : Comparable<Tree> {
    override fun compareTo(other: Tree): Int {
        return this.height.compareTo(other.height)
    }

}

