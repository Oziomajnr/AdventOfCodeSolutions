val cardNumber: List<Char> = "A, K, Q, T, 9, 8, 7, 6, 5, 4, 3, 2, J".split(", ").map { it.first() }.reversed()
val input = File("input1.txt").readText()

fun main() {
    println(input.trim().split("\n").map {
        it.split(" ").let {
            Card(it[0].toCardType(), it[1].toInt(), it[0])
        }
    }.sortedWith { a, b ->
        val comparisonByRank = a.cardType.rank.compareTo(b.cardType.rank)
        if (comparisonByRank == 0) {
            a.cardValue.zip(b.cardValue).forEach { value: Pair<Char, Char> ->
                val comparison: Int = cardNumber.indexOf(value.first).compareTo(cardNumber.indexOf(value.second))
                if (comparison != 0) {
                    return@sortedWith comparison
                }
            }
            return@sortedWith 0
        } else {
            return@sortedWith comparisonByRank
        }
    }.mapIndexed { index, card ->
        ((index + 1) * card.wight).toLong()
    }.sum())
}

fun String.toCardType(): CardType {

    if (this.contains('J')) {
        val max = cardNumber.drop(1).maxWith { a, b ->
            val cc = this.replace('J', a).toCardType().rank.compareTo(this.replace('J', b).toCardType().rank)
            if (cc == 0)
                cardNumber.indexOf(a).compareTo(cardNumber.indexOf(b))
            else {
                cc
            }
        }
        return this.replace('J', max).toCardType()
    }
    return when {
        this.toSet().size == 1 -> CardType.FiveOfAKind()
        this.groupBy { it }.map { it.value.size }.contains(4) -> CardType.FourOfAKind()
        this.groupBy { it }.map { it.value.size }.contains(3) && this.toSet().size == 2 -> CardType.FullHouse()

        this.groupBy { it }.map { it.value.size }
            .contains(3) && this.toSet().size == 3 -> CardType.ThreeOfAKind()

        this.groupBy { it }.map { it.value.size }.contains(2) && this.toSet().size == 3 -> CardType.TwoPairs()

        this.groupBy { it }.map { it.value.size }.contains(2) && this.toSet().size == 4 -> CardType.OnePair()

        this.toSet().size == 5 -> CardType.HighCard()
        else -> error("Should not get here")
    }
}


sealed interface CardType {
    val rank: Int

    data class FiveOfAKind(override val rank: Int = 6) : CardType
    data class FourOfAKind(override val rank: Int = 5) : CardType
    data class FullHouse(override val rank: Int = 4) : CardType
    data class ThreeOfAKind(override val rank: Int = 3) : CardType
    data class TwoPairs(override val rank: Int = 2) : CardType

    data class OnePair(override val rank: Int = 1) : CardType

    data class HighCard(override val rank: Int = 0) : CardType
}

data class Card(val cardType: CardType, val wight: Int, val cardValue: String)
