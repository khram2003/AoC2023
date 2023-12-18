import kotlin.math.min
import kotlin.math.pow

fun main() {

    val handTypeOrder =
        listOf("Five of a kind", "Four of a kind", "Full house", "Three of a kind", "Two pair", "One pair", "High card")
    val cardValueOrder = "AKQJT98765432"

    data class HandType(val type: String, val values: List<Char>)

    data class Hand(val cards: String, val bid: Int, val type: HandType) : Comparable<Hand> {
        override fun compareTo(other: Hand): Int {
            val typeComparison = handTypeOrder.indexOf(this.type.type).compareTo(handTypeOrder.indexOf(other.type.type))
            if (typeComparison != 0) return typeComparison

            this.type.values.zip(other.type.values).forEach { (thisVal, otherVal) ->
                val valueComparison = cardValueOrder.indexOf(thisVal).compareTo(cardValueOrder.indexOf(otherVal))
                if (valueComparison != 0) return valueComparison
            }
            return 0
        }
    }

    fun classifyHand(hand: String): HandType {
        val counts = hand.groupingBy { it }.eachCount().toList().sortedWith(compareByDescending<Pair<Char, Int>> { it.second }.thenBy { cardValueOrder.indexOf(it.first) })
//        { cardValueOrder.indexOf(it.first) })
        val type = when {
            counts.first().second == 5 -> "Five of a kind"
            counts.first().second == 4 -> "Four of a kind"
            counts.first().second == 3 && counts[1].second == 2 -> "Full house"
            counts.first().second == 3 -> "Three of a kind"
            counts.first().second == 2 && counts[1].second == 2 -> "Two pair"
            counts.first().second == 2 -> "One pair"
            else -> "High card"
        }
        val sortedValues = counts.flatMap { (card, count) -> List(count) { card } }
        return HandType(type, sortedValues)
    }

    fun part1(input: List<String>): Int {
        val hands = input.map { line ->
            val (hand, bid) = line.split(" ")
            Hand(hand, bid.toInt(), classifyHand(hand))
        }.sorted().reversed()
        println(hands)
        return hands.mapIndexed { index, hand -> hand.bid * (index + 1) }.sum()


    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    println(part1(testInput))
    check(part1(testInput) == 6440)
//    check(part2(testInput) == 71503)
    val input = readInput("Day07")
    part1(input).println()
//    part2(input).println()
}