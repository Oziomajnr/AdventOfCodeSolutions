    private val fileInput =
        File("input.txt").readLines()

    val firstPart = fileInput.map {
        it.split(" | ")[0]
    }.map {
        it.split(" ")
    }

    val input = fileInput.map {
        it.split(" | ")[1]
    }.map {
        it.split(" ")
    }.map {
        it.map {
            it.toSortedSet()
        }
    }


    fun solvePart2(inputKey: List<String>, inputValue: List<SortedSet<Char>>): Int {

        val displaySegments = getDisplayConfiguration(inputKey)

        val nine =
            listOf(0, 1, 2, 3, 5, 6).map { displaySegments[it] }.toCharArray().concatToString()
        val eight =
            listOf(0, 1, 2, 3, 4, 5, 6).map { displaySegments[it] }.toCharArray().concatToString()
        val seven = listOf(0, 2, 5).map { displaySegments[it] }.toCharArray().concatToString()
        val six =
            listOf(0, 1, 3, 4, 5, 6).map { displaySegments[it] }.toCharArray().concatToString()
        val five = listOf(0, 1, 3, 5, 6).map { displaySegments[it] }.toCharArray().concatToString()
        val four = listOf(1, 2, 3, 5).map { displaySegments[it] }.toCharArray().concatToString()
        val three = listOf(0, 2, 3, 5, 6).map { displaySegments[it] }.toCharArray().concatToString()
        val two = listOf(0, 2, 3, 4, 6).map { displaySegments[it] }.toCharArray().concatToString()
        val one = listOf(2, 5).map { displaySegments[it] }.toCharArray().concatToString()
        val zero =
            listOf(0, 1, 2, 4, 5, 6).map { displaySegments[it] }.toCharArray().concatToString()


        val entries = listOf(zero, one, two, three, four, five, six, seven, eight, nine).map {
            it.toSortedSet()
        }.groupBy {
            it
        }.mapValues {
            it.value.first()
        }

        val result = inputValue.map { currentString ->
            entries.keys.indexOf(currentString)
        }.map { it.toString() }.reduce { acc, value ->
            acc + value
        }.toInt()
        return result
    }

    fun getDisplayConfiguration(input: List<String>): CharArray {
        val result = CharArray(7)
        // look for 2 char string
        val twoCharacterString = input.find {
            it.length == 2
        }!!
        val threeCharacterString = input.find {
            it.length == 3
        }!!
        val positionZero =
            threeCharacterString.toCharArray().toList() - twoCharacterString.toCharArray().toList()
        result[0] = positionZero.first()
        //look for a character that is in all the input except one
        val fifthChar = ('a'..'g').first { rangeChar ->
            input.filter {
                !it.contains(rangeChar)
            }.size == 1
        }
        result[5] = fifthChar

        val secondChar = twoCharacterString.first {
            it != fifthChar
        }
        result[2] = secondChar

        val sixthDigit = ('a'..'g').filter { rangeChar ->
            val x = input.filter {
                it.length in 2..4
            }

            val y = input.filter {
                it.length !in 2..4
            }

            (y.filter {
                it.contains(rangeChar)
            }.size == y.size) && (x.filter {
                !it.contains(rangeChar)
            }.size == x.size)
        }

        result[6] = sixthDigit.first()
        val charactersFound = result.filterIndexed { index, char ->
            index in listOf(0, 5, 2, 6)
        }
        val thirdCharacter = input.filter {
            it.length == 5
        }.filter {
            it.filter {
                charactersFound.contains(it)
            }.length == 4
        }.map {
            it.filter {
                !charactersFound.contains(it)
            }
        }

        result[3] = thirdCharacter.first().first()

        val fourthCharacter = (('a'..'g').filter {
            !result.filterIndexed { index, char ->
                index in listOf(0, 5, 2, 6, 3)
            }.contains(it)
        }).filter { rangeChar ->
            !input.filter {
                it.length == 4
            }.first().contains(rangeChar)
        }
        result[4] = fourthCharacter.first()

        val firstCharacter = ('a'..'g').toSet() - result.toSet()
        result[1] = firstCharacter.first()
        return result
    }

    @Test
    fun test() {
        println(firstPart.mapIndexed { index, value ->
            solvePart2(value, input[index])
        }.sum())
    }
