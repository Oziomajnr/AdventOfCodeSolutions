//Part1
fun main() {
    measureTimeMillis{
        println(kkk.trim().split(",").map {
            it.fold(0L) { a, b ->
                ((b.code + a) * 17) % 256
            }
        }.sum())
    }
}

//Part 2
fun main() {
    measureTimeMillis {
        val boxes = Array(256) {
            PriorityQueue<Lense> { a, b -> a.position.compareTo(b.position) }
        }
        kkk.trim().split(",").forEach {
            val hash = it.split("[=\\-]".toRegex()).first().fold(0) { a, b ->
                ((b.code + a) * 17) % 256
            }
            val curr = boxes[hash]

            if (it.contains("-")) {
                val oldEntry = curr.find { lenses -> lenses.label == it.split("-").first() }
                curr.remove(oldEntry)
            } else {
                val arr = it.split("=")
                val oldEntry = curr.find { lenses -> lenses.label == arr.first() }
                if (oldEntry != null) {
                    val newEntry = oldEntry.copy(value = arr[1].toInt())
                    curr.remove(oldEntry)
                    curr.offer(newEntry)
                } else {
                    curr.offer(Lense(arr.first(), arr[1].toInt(), (curr.maxOfOrNull { it.position } ?: 0) + 1))
                }
            }
        }
        boxes.mapIndexed { index, value ->
            var sum = 0
            var lenseIndex = 1
            while (value.isNotEmpty()) {
                val lense = value.poll()
                sum += (index + 1) * lense.value * lenseIndex
                lenseIndex++
            }
            sum
        }.sum().let(::println)
    }.let(::println)
}

data class Lense(val label: String, val value: Int, val position: Int)
