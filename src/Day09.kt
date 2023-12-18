import kotlin.math.min
import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { history ->
            val valuesGrossbuch = mutableListOf(history.split(" ").map { it.toInt() }.toMutableList())
            var values = valuesGrossbuch.last()
            while (values.size > 1 && !values.zipWithNext().all { (a, b) -> a == b }) {
                values = values.zipWithNext { a, b -> b - a }.toMutableList()
                valuesGrossbuch.add(values)
//                println(valuesGrossbuch)
            }
            val reversedGrossbuch = valuesGrossbuch.reversed()
            reversedGrossbuch.forEachIndexed { index, list ->
                if (index == 0) {
                    reversedGrossbuch[index].add(reversedGrossbuch[index].last())
                } else {
                    reversedGrossbuch[index].add(reversedGrossbuch[index].last() + reversedGrossbuch[index - 1].last())
                }
            }
//            println(reversedGrossbuch.last().last())
            reversedGrossbuch.last().last()
        }


    }

    fun part2(input: List<String>): Int {
        return input.sumOf { history ->
            val valuesGrossbuch = mutableListOf(history.split(" ").map { it.toInt() }.toMutableList())
            var values = valuesGrossbuch.last()
            while (values.size > 1 && !values.zipWithNext().all { (a, b) -> a == b }) {
                values = values.zipWithNext { a, b -> b - a }.toMutableList()
                valuesGrossbuch.add(values)
//                println(valuesGrossbuch)
            }
            val reversedGrossbuch = mutableListOf<MutableList<Int>>()
            valuesGrossbuch.reversed().forEach { list ->
                reversedGrossbuch.add(list.reversed().toMutableList()) }

//            println(reversedGrossbuch)

            reversedGrossbuch.forEachIndexed { index, list ->
                if (index == 0) {
                    reversedGrossbuch[index].add(reversedGrossbuch[index].last())
                } else {
                    reversedGrossbuch[index].add(reversedGrossbuch[index].last() - reversedGrossbuch[index - 1].last())
                }
//                print(reversedGrossbuch)
            }
//            println(reversedGrossbuch.last().last())
            reversedGrossbuch.last().last()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
//    println(part1(testInput))
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}