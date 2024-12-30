package year_2022.day22

import common.transpose
import solve
import year_2022.common.Position

fun main() = solve { lines ->
    val (input, instructions) = parseInput(lines)
    connectTiles(input)
    connectEdges(input)
    var currentTile = input.first().first {
        it !is Tile.EmptyTile
    }
    var currentDirection = Direction.Right

    instructions.forEach { instruction ->
        if (instruction is Instruction.ForwardInstruction) {
            repeat(instruction.value) {
                val newTile = currentTile.moveForward(currentDirection)
                currentTile = newTile.tile
                currentDirection = newTile.direction ?: currentDirection
            }
        } else if (instruction is Instruction.DirectionInstruction) {
            currentDirection = getNextDirection(currentDirection, instruction.direction)
        }
    }
    1000 * (currentTile.position.x + 1) + 4 * (currentTile.position.y + 1) + currentDirection.value
}

fun parseInput(lines: List<String>): Pair<Array<Array<Tile>>, List<Instruction>> {
    val forwardInstruction = instructions.split(Regex("[LR]"))
    val directionInstruction = instructions.filter {
        it == 'L' || it == 'R'
    }
    val instructions = mutableListOf<Instruction>()
    forwardInstruction.forEachIndexed { index, instruction ->
        instructions.add(Instruction.ForwardInstruction(instruction.toInt()))
        if (index != forwardInstruction.lastIndex) {
            val direction = if (directionInstruction[index] == 'L') {
                Direction.Left
            } else {
                Direction.Right
            }
            instructions.add(Instruction.DirectionInstruction(direction))
        }
    }

    val rawInput = lines.mapIndexed { x, line ->
        line.toCharArray().mapIndexed { y, tile ->
            when (tile) {
                '.' -> Tile.OpenTile(Position(x, y))
                '#' -> Tile.ClosedTile(Position(x, y))
                ' ' -> Tile.EmptyTile(Position(x, y))
                else -> {
                    error("Invalid tile type")
                }
            }
        }.toTypedArray()
    }.toTypedArray()
    return Array(rawInput.size) { x ->
        Array(rawInput.maxOf {
            it.size
        }) { y ->
            rawInput.getOrNull(x)!!.getOrNull(y) ?: Tile.EmptyTile(Position(x, y))
        }
    } to instructions
}

fun connectTiles(tileArray: Array<Array<Tile>>) {
    tileArray.forEach { tiles ->
        val firstTile = tiles.first {
            it !is Tile.EmptyTile
        }
        val lastTile = tiles.last {
            it !is Tile.EmptyTile
        }
        tiles.filter { it !is Tile.EmptyTile }.forEach { tile ->
            if (tile == lastTile) {
                tile.rightTile = TileWithDirection(firstTile)
            } else {
                tile.rightTile = TileWithDirection(tiles[tile.position.y + 1])
            }

            if (tile == firstTile) {
                tile.leftTile = TileWithDirection(lastTile)
            } else {
                tile.leftTile = TileWithDirection(tiles[tile.position.y - 1])
            }
        }
    }

    tileArray.map { it.toList() }.transpose {
        Tile.EmptyTile(Position(-1, -1))
    }.forEach { tiles ->
        val firstTile = tiles.first {
            it !is Tile.EmptyTile
        }
        val lastTile = tiles.last {
            it !is Tile.EmptyTile
        }
        tiles.filter { it !is Tile.EmptyTile }.forEach { tile ->
            if (tile == lastTile) {
                tile.bottomTile = TileWithDirection(firstTile)
            } else {
                tile.bottomTile = TileWithDirection(tiles[tile.position.x + 1])
            }

            if (tile == firstTile) {
                tile.topTile = TileWithDirection(lastTile)
            } else {
                tile.topTile = TileWithDirection(tiles[tile.position.x - 1])
            }
        }
    }
}


private fun connectEdges(tileArray: Array<Array<Tile>>) {
    tileArray[0].copyOfRange(50, 99 + 1).zip(tileArray.sliceArray(150..199).map { it[0] }).onEach {
        if (it.first is Tile.EmptyTile || it.second is Tile.EmptyTile) {
            error("Invalid tile")
        }
        it.first.topTile = TileWithDirection(it.second, Direction.Right)
        it.second.leftTile = TileWithDirection(it.first, Direction.Down)
    }

    tileArray.sliceArray(0..49).map { it[50] }.zip(tileArray.sliceArray((100..149)).map { it[0] }.reversed()).onEach {
        if (it.first is Tile.EmptyTile || it.second is Tile.EmptyTile) {
            error("Invalid tile")
        }
        it.first.leftTile = TileWithDirection(it.second, Direction.Right)
        it.second.leftTile = TileWithDirection(it.first, Direction.Right)
    }

    tileArray.sliceArray(0..49).map { it[149] }.zip(tileArray.sliceArray(100..149).map { it[99] }.reversed()).onEach {
        if (it.first is Tile.EmptyTile || it.second is Tile.EmptyTile) {
            error("Invalid tile")
        }
        it.first.rightTile = TileWithDirection(it.second, Direction.Left)
        it.second.rightTile = TileWithDirection(it.first, Direction.Left)
    }

    tileArray[0].copyOfRange(100, 149 + 1).zip(
        tileArray[199].copyOfRange(0, 49 + 1)
    ).onEach {
        if (it.first is Tile.EmptyTile || it.second is Tile.EmptyTile) {
            error("Invalid tile")
        }
        it.first.topTile = TileWithDirection(it.second, Direction.Up)
        it.second.bottomTile = TileWithDirection(it.first, Direction.Down)
    }

    tileArray[49].copyOfRange(100, 149).zip(tileArray.sliceArray(50..99).map { it[99] }).onEach {
        if (it.first is Tile.EmptyTile || it.second is Tile.EmptyTile) {
            error("Invalid tile")
        }
        it.first.bottomTile = TileWithDirection(it.second, Direction.Left)
        it.second.rightTile = TileWithDirection(it.first, Direction.Up)
    }

    tileArray.sliceArray(50..99).map { it[50] }.zip(
        tileArray[100].copyOfRange(0, 49 + 1)
    ).onEach {
        if (it.first is Tile.EmptyTile || it.second is Tile.EmptyTile) {
            error("Invalid tile")
        }
        it.first.leftTile = TileWithDirection(it.second, Direction.Down)
        it.second.topTile = TileWithDirection(it.first, Direction.Right)
    }

    tileArray[149].copyOfRange(50, 99 + 1).zip(tileArray.sliceArray(150..199).map { it[49] }).onEach {
        if (it.first is Tile.EmptyTile || it.second is Tile.EmptyTile) {
            error("Invalid tile")
        }
        it.first.bottomTile = TileWithDirection(it.second, Direction.Left)
        it.second.rightTile = TileWithDirection(it.first, Direction.Up)
    }
}

data class TileWithDirection(val tile: Tile, val direction: Direction? = null)
sealed class Tile {
    abstract val position: Position


    lateinit var topTile: TileWithDirection
    lateinit var bottomTile: TileWithDirection
    lateinit var leftTile: TileWithDirection
    lateinit var rightTile: TileWithDirection

    fun moveForward(currentDirection: Direction): TileWithDirection {
        val nextTile = when (currentDirection) {
            Direction.Left -> {
                leftTile
            }

            Direction.Right -> {
                rightTile
            }

            Direction.Up -> {
                topTile
            }

            Direction.Down -> {
                bottomTile
            }
        }
        return if (nextTile.tile is ClosedTile) {
            TileWithDirection(this, currentDirection)
        } else {
            nextTile
        }
    }

    data class OpenTile(
        override val position: Position
    ) : Tile()

    data class EmptyTile(override val position: Position) : Tile()

    data class ClosedTile(override val position: Position) : Tile()
}

sealed interface Instruction {
    data class DirectionInstruction(val direction: Direction) : Instruction
    data class ForwardInstruction(val value: Int) : Instruction
}

fun getNextDirection(currentDirection: Direction, newDirection: Direction): Direction {
    return when (currentDirection) {
        Direction.Left -> {
            when (newDirection) {
                Direction.Left -> Direction.Down
                Direction.Right -> Direction.Up
                else -> error("invalid direction")
            }
        }

        Direction.Right -> {
            when (newDirection) {
                Direction.Left -> Direction.Up
                Direction.Right -> Direction.Down
                else -> error("invalid direction")
            }
        }

        Direction.Up -> {
            when (newDirection) {
                Direction.Left -> Direction.Left
                Direction.Right -> Direction.Right
                else -> error("invalid direction")
            }
        }

        Direction.Down -> {
            when (newDirection) {
                Direction.Left -> Direction.Right
                Direction.Right -> Direction.Left
                else -> error("invalid direction")
            }
        }
    }
}

enum class Direction(val value: Int) {
    Left(2), Right(0), Up(3), Down(1)
}

val instructions =
    "47L2R45L20L16L2R33L27R32L12R50L13R23R27L32L27R2R16R36L22L13R17L41R5L11R28R4R9R28R44L31L49R31L29R10L4L30L46L20R26R37R32L10L39R38L17R5L44R22L36L23L31R38R13L36R8R35L33R44R46R13R12L3L19L18L16L16L36L11R20R11R7L15R39R46L49L14R32L45R6R13L31L20R40L27L16R7R9R41L13L18R47R29L14L10L14L44R35R1R43R37L33L3R45R8L48L16L49R33L14R9L15L26L24R1L48L11L18L40L4L27R43R10L5L35L41L49R36L24R47L25L9L5R31R48L11L11R5R41R21R8R28L37R39L19L5L25L15R4L41L41L14R15R3R47L21L15R2L23L9L28L13R24L39L34L34L4R32R36R45R38R43L6L46R30L26R43L49R48R33R7R25R37R7L13L3R38R31L16L9L27R3L36R16R35L33R24L30L46R24L37L18R23R15L38L49L39R43R42R39L8L10R23L35R44L32L4R4R33R37L41L38R43L26L41L36L28L30L26R37L40L31R43L23R22R6R13R38L15L13L25R6L8R14L36R22R48R16R29L16L39R11L5R46L46R21L20R2R5R5R31L22L27L29L14L5R45R7R45R8R23L27R35L28R25L32L4R3R45R23R3L48L5L6L4L1R25L31L20R49R8R37L13R37L22L40L47R18L26R49L32L37R24R10L47L47L22R42L11L1R48R32L25R30L14R26R49R29L17R22L3R32L41L36L45R13L27L15L50R48L24L31R48R35L5R3L13L44R28R45L35R46L2L12L11L37R37R14L10L13L1L22R7L16L6L30R18R22R3R21L34R45L29L17L36L3L39R11L32L27R29R11L26L43L18R28L39L33R7L5R24L19R9L23L11R15L47R20R26L47R4R46R8L27R46R37L28L15L44L4L27L32L19L36L47R50L9R47L1R26R44L31R10L21L40R43L25L4R14L41L16L15R31R17L40L33R25R9L18L46R12L40R31L38L18L15R30L13R49R11L26R34L6L49L9R49L49R49R34L36R29R44L45L47L35L25R36L5R17R12L1L30R47L45L43R44R50R24R40R27R39R20L4L16L38L26R20L41L40R1R5R24L26R29R6L11R50R17L23L15R31L38R22R16L17L7R43L4L32L16L7L43R38R34L35R10L45R38L13L15L24R44R11L41R44R3R12L2L21L3L47R21R13R39L43R46L17R36R17L31R27R30L12L3R4L27L31L41R24L31R48L46L38L28L12R20L39R41L43R17L6R17R41L19R34L49L16L41R49R29L49L11R13L9R49R25L48R14R33L38L4L48R4L14R32R31L3L30R4R16L7R7R36L49L41R26R34R12L22L2R6R2L22L29L8L37L34L9L2R33R48L10R8R9R26R4R8L45R23L6R4L33L11R38R40R35L30L6L44L28R22R19L30L38L33L6R11R32R37R50R50R44L34L41R14L17R31L33L34R32L22R43L24R37L49R4R29R11R32R24L3R3R19R49R19L40L22R2L8L7L25R16L19L36L10L43R44R12R30R20R11R29L1L7R27L24R49R22L47R27L28R6R48L20R38R3L9L1R11R29R32R36R13L43L32L5L23L22R3R4R5R38R5L30L3R2L27R21L30R18L28L30R27L11L17L21L7L42R10L36L5L28R48R42R32L29L30R32L29R10L38L49L10R19R48R21R24L1L23R20L30L44R24L50R5R14L8L43R17L44R11L29R9L6R39R42L9L7R47R35R36R5R3L7R22R16L38L31R34R45R45L38L37R46L38L7L1L11L5R31L28L25R40R33L3L39R9R2R5L21R4L26R7R23L47L47R16R42R14L46R10R50L14L23R37R48R23R25R34R23L14R50R46R27L44R45R35L35R4R11R24R12R14R38R32R22R50L26L43R7L40L32R33R22L17R37L29R49R44L12R23R21R19R16L17R21R24R36R20R11R32L13L24L26R1R2R7L22L35L21L26R30L46R24L46L47L36R43R39L33R2L50L25L13R36L42L19L12L43L45L10R28R11L5L49L27R24R2L25L6L33R28R18L2L39L30R24R28R48L8L15R39R16R1R34R43R45R40L32L23L34L35L13L50L18L39R21L27L27R30L34L16R18L29L19R19R25L37R11L1L34R18L17R46R26R19L45L47R25L38R13R22L30L14L43R49R7L1L44L20R36R10R5L27R48L9L12R25R36L42R12L46R1R1L30R19L3R32R23R29R1R14R30R31L1L15L13R36L34R39L16L44R34R34R11L21R23R45L3R19R30L37R4L27R17R23L40L19R21L32L47R49R14L33R18R32L8R40R7R50R2R9L15R37L33R7R21L32L1L8L34L24R50R13L9R32R7R34L12R21L42L26L3R17L37L7R39R43L15L19R3L7R10L35R34R24R21L8L4R49R33R18R46R15L38L18R23L35L12L36R45R5L20L8L49L5R21R12L11R8R17R27L16L16L22R29L41L48L18L11L50R34R4L25R14R14R26R36L15L10R12L10L28R45L6L32L38L21L39R16R43L43R29R15L47L2L43L24R39L30L38R24R47R49L43R37L44L46R25R23L37L20L10L4L11L24R23L4R6R42L50L22R36L49L37R8R2R49L10L21R50R31L23R30R29R1L35R29L26L9R35R10R41L6L30L34R48L50R4L40L20R18R28R23L49L39R10L7L15L48L48R45R3R30L35L2L24R43R9L46R5R1R17L13R17R49R22R3L35R46L39L25L40R25L42L24R23R22R32L13L41R11R30L17R44R16L6R22R30L45L30L29L16L18L24L10R36R37L42L21R28L41R37R47R44R42R15L14R20L4L37R3R7L48L10L38L42L37L13L20L40R18L2R5L24L5L45R28R10R2L29R4R10L33R47R37L15R38L27R17R24L1R11L30L29L8R13R35R28R19L1R16L37L11R42L31R32R28R17R46R13R6R4R15R30R8L46L21R38L17L50R41R2R13L7L25R36R25L35L16R32R44R30R32L50L13R7R9L5R6L43L4R16L9L31R38R49R27L39L37R14L31R26R49L32L10L23L19L12R35L14L44R37R36L25L18L22R17L40L8R45R49R22R30R28R33R40R50L2L18R16R14L35R1L24R36L44R34L36L25L47L3L2R19L45L19R48R29R38R7R26R50R43R20R1R32R13R44L6R10R17L39L29L11L16L4L1R27R14R9L11L28L5L11R11R25R9R28R24L30L21R42L10R10R30R48L25R41L4R49L1R15L5R49R25R25R2R22L22L27R33L17R9L48L49L6R13R36L20R40R37L1R37R13R29R27R49L15L15R29L39R5R1R11L2R40R49R1L22R49R7L39R30L34L15L5L28R18L7L39L15L13R29R13R11L14R6R29L39R41R47L34R9R36L11R20L36L36L48L16R18R18L23L48L14R32L30R50R38R36L47R16R41L37R24R9R11R13L6L18L26L50R44L47R13R14L45R33R44R48L2L18R30L35L48L10R18R36R19R12L27R44R33L40R34R2R3L2L38L1R4R17R32R30L42L11R32L10R25L8R10R1L5L27R47R27R50R8L19L7L27L5R28L23L11L34R13R25L48R39L33L45R18R36R44R19R3L37L11L46L40R36L12L15L16L7L16R3R20R21L12R20L7L43R49R16L46L38L30R41L47L17L42L2L1R42L48L45R3R28R8L18R21R18R32R1L18R37L19R25L26R2L46L20R4R26R22L5L40L49L41R3R9R24L3R50R49R48L20R34L22L17L49R26L20L6R28R22L44L10L6R48L21L22L27L33R13L32R23R45L33R11R29L50R49L31R34L30R19L10R37L29L8L16R1L21R11R2R23L12R8L30L1R15R22L26R31L37L39L25L44R23L50R27L2L33R23L5L36L2L9L40R43L19R43L32R4L12R49L25R48L13R13L31R5L16L46L46R29L5R23R22R36L13R15R26R43L8L37R30R20L7L4R26L33L32R27L40L20L36R39L36L3L19R49R8L28R16R10R40R27R50R36L28R10R8R37R49L28L36L16R44R29L2L31R25L48L44L24L21R40R40R24L31L32R37R30R1R22R36L44L23R14R38R25R27L5L7R43L45L2R48R30R32R35R48L30L6L31R45R20L14R5R25L30R39R9L6L18R6R30L3L15R33R30R14L31R39L11R18L35L27R25R26R36R40R43L17R30L14R48R20L40L28R40R6L26L36L30R34L31L38L48L12R15R42L36L41R12L44L10L19L4L44R23R41R37L45L10R7R48L43R16L43L38R17L49R31L45R29L29L43R47R8L3R46R13R39L7R44L37R6L32R33R9R16L5L6R45L42L26L19L45R25R4R34R34L19L26L12R21L36L48R27L32L16R34R13L15R20L31L27R34R23L15L23R19L27R43R39L13L37L6R7L41L23R36R23R28L6R26L22R32L46R24L27L25R5R43L47R39L46R21R45L3L31R36R36L13R33L13R47R27R13L45L22R49R50L10R37R18L46L43R50L13R27R33L11L34R26L11R12R5R10L45R43L47L25R49L23R50R36R45R11L9L47L36R37R21R36L31L40R33R29L15L25R25L9L5R22L50R16L17L32L32L16L8R5R7R38L37"

//const val instructions = "10R5L5R10L4R5L5"