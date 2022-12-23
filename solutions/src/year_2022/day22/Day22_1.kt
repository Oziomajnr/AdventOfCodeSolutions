package year_2022.day22

import solve

fun main() = solve { lines ->
    val (input, instructions) = parseInput(lines)
    connectTiles(input)
    var currentTile = input.first().first {
        it !is Tile.EmptyTile
    }
    var currentDirection = Direction.Right

    instructions.forEach { instruction ->
        if (instruction is Instruction.ForwardInstruction) {
            repeat(instruction.value) {
                currentTile = currentTile.moveForward(currentDirection).tile
            }
        } else if (instruction is Instruction.DirectionInstruction) {
            currentDirection = getNextDirection(currentDirection, instruction.direction)
        }
    }
    1000 * (currentTile.position.x + 1) + 4 * (currentTile.position.y + 1) + currentDirection.value

}