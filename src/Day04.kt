import kotlin.math.min
import kotlin.math.pow

fun main() {


    fun part1(input: List<String>): Int {
        return input.sumOf { card ->
            val parts = card.split("|").map { it.trim() }
            val winningNumbers = parts[0].substringAfter(":").trim().split("\\s+".toRegex()).map(String::toInt).toSet()
            val numbers = parts[1].trim().split("\\s+".toRegex()).map(String::toInt).toSet()

            var points = 0
            if (numbers.intersect(winningNumbers).isNotEmpty()) {
                points = (2.0.pow(numbers.intersect(winningNumbers).size - 1)).toInt()
            }
            points
        }
    }

    fun part2(input: List<String>): Int {
        val mapa = mutableMapOf<Int, Int>()
        val mapb = mutableMapOf<Int, Int>()
        input.forEach { card ->
            val parts = card.split("|").map { it.trim() }
            val winningNumbers = parts[0].substringAfter(":").trim().split("\\s+".toRegex()).map(String::toInt).toSet()
            val numbers = parts[1].trim().split("\\s+".toRegex()).map(String::toInt).toSet()
            val matchedNumbersAmount = winningNumbers.intersect(numbers).size
            val cardNumber = card.substringBefore(":").split(' ').last().toInt()
            mapa[cardNumber] = matchedNumbersAmount
            mapb[cardNumber] = 1
        }
        val maxCardNumber = mapa.keys.maxOrNull()!!
        mapa.forEach{ (cardNumber, matchedNumbersAmount) ->
            (cardNumber+1 ..  min(cardNumber + matchedNumbersAmount, maxCardNumber)).forEach { number ->
                mapb[number] = mapb[number]!! + mapb[cardNumber]!!
            }
        }

        return mapb.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
//    println(part2(testInput))
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}