import java.util.PriorityQueue

val pointDistances = mutableMapOf<NodeState, Long>()

fun main() {
    val lines = input.trim().split("\n")
    val startPoint1 = NodeState(Point(0, 0), lines[0][0].digitToInt().toLong(), Direction.RIGHT, 0)
    pointDistances[startPoint1] = startPoint1.value

    val unvisitedPoints = PriorityQueue<NodeState>() { a, b ->
        val thisD = pointDistances[a] ?: Long.MAX_VALUE
        val otherD = pointDistances[b] ?: Long.MAX_VALUE
        (thisD).compareTo(otherD)

    }
    val visitedPoints = mutableSetOf<NodeState>()
    unvisitedPoints.add(startPoint1)

    while (unvisitedPoints.isNotEmpty()) {
        val nodeState = unvisitedPoints.poll()
        val left = if (nodeState.steps >= 4) nodeState.point.turnLeft(nodeState.direction) else null
        val right = if (nodeState.steps >= 4) nodeState.point.turnRight(nodeState.direction) else null
        val forward = if (nodeState.steps < 10) nodeState.point.moveForward(nodeState.direction) else null
//        val left = nodeState.point.turnLeft(nodeState.direction)
//        val right = nodeState.point.turnRight(nodeState.direction)
//        val forward = if (nodeState.steps < 3) nodeState.point.moveForward(nodeState.direction) else null
        visitedPoints.add(nodeState)
        listOfNotNull(
            left, right, forward
        ).filter { it.second.x in lines.indices && it.second.y in lines[0].indices }.mapIndexed { index, value ->
            NodeState(
                value.second,
                lines[value.second.x][value.second.y].digitToInt().toLong(),
                value.first,
                if (value == forward) nodeState.steps + 1 else 1
            )
        }.filter {
            unvisitedPoints.contains(it) || !visitedPoints.contains(it)
        }.forEach { node ->
            if (node.point == Point(lines.lastIndex, lines[0].lastIndex)) {
                println(pointDistances[nodeState]!! + node.value - startPoint1.value)
                return
            }
            val currentDistance = pointDistances[nodeState] ?: Long.MAX_VALUE
            if (currentDistance + node.value < (pointDistances[node]
                    ?: Long.MAX_VALUE)
            ) {
                pointDistances[node] = currentDistance + node.value
            }
            unvisitedPoints.remove(node)
            unvisitedPoints.offer(node)
        }
    }

}


data class NodeState(val point: Point, val value: Long, val direction: Direction, val steps: Int)
