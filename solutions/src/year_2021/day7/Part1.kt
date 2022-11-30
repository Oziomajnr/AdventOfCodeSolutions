    private val fileInput =
        File("input.txt").readLines()
            .first().split(",")
            .map {
                it.toInt()
            }

    private fun solvePart1() {
        val max = fileInput.maxOrNull()!!
        val groupedInput = fileInput.groupBy {
            it
        }.mapValues {
            it.value.count()
        }

      val  costForPosition =  (0..max).map{
           it to groupedInput.map { (i, count) ->
                abs(i - it) * count
            }   .sum()
        }
            
        println(costForPosition.minByOrNull { it.second }!!)
    }
    
    @Test
    fun test() {
        solvePart1()
    }
