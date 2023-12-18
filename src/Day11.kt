import kotlin.math.abs
import kotlin.math.min
import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Long {
        val rows = input.size
        val cols = input[0].length
        val emptyRows = BooleanArray(rows) { y -> input[y].all { it == '.' } }
        val emptyCols = BooleanArray(cols) { x -> input.all { it[x] == '.' } }
        val expanded = mutableListOf<String>()
        for (y in input.indices) {
            val newRow = buildString {
                for (x in input[y].indices) {
                    append(input[y][x])
                    if (emptyCols[x]) {
                        append(input[y][x])
                    }
                }
            }
            expanded.add(newRow)
            if (emptyRows[y]) expanded.add(newRow)
        }

        val galaxies = mutableListOf<Pair<Pair<Long, Long>, Long>>()

        for (y in expanded.indices) {
            for (x in expanded[y].indices) {
                if (expanded[y][x] == '#') {
                    galaxies.add(Pair(Pair(x.toLong(), y.toLong()), (galaxies.size + 1).toLong()))
                }
            }
        }

        val pairs = mutableListOf<Pair<Pair<Pair<Long, Long>, Long>, Pair<Pair<Long, Long>, Long>>>()
        for (i in 0 until galaxies.size - 1) {
            for (j in i + 1 until galaxies.size) {
                pairs.add(Pair(galaxies[i], galaxies[j]))
            }
        }


        return pairs.sumOf { (p1, p2) ->
            val point1 = p1.first
            val point2 = p2.first
            println(
                "${p1.second} ${p2.second} ${
                    abs(point1.first - point2.first) + abs(point1.second - point2.second)
                } "
            )
            println("$point1, $point2 \n")
            abs(point1.first - point2.first) + abs(point1.second - point2.second)
        }
    }

    fun part2(input: List<String>): Long {
        val rows = input.size
        val cols = input[0].length
        val emptyRows = mutableListOf<Long>()
        (0 until rows).forEach { kek ->
            if (input[kek].all { it == '.' }) {
                emptyRows.add(kek.toLong())
            }
        }

        val emptyCols = mutableListOf<Long>()
        (0 until cols).forEach { kek ->
            if (input.all { it[kek] == '.' }) {
                emptyCols.add(kek.toLong())
            }
        }
        println(emptyRows)
        println(emptyCols)
        val expanded = input

        val galaxies = mutableListOf<Pair<Pair<Long, Long>, Long>>()

        for (y in expanded.indices) {
            for (x in expanded[y].indices) {
                if (expanded[y][x] == '#') {
                    val emtyYPrefix = emptyRows.count { it < y }
                    val emtyXPrefix = emptyCols.count { it < x }
                    println("$x, $y, $emtyXPrefix, $emtyYPrefix")
                    galaxies.add(Pair(Pair((x + emtyXPrefix*999999).toLong(), (y + emtyYPrefix*999999).toLong()), (galaxies.size + 1).toLong()))
                }
            }
        }

        val pairs = mutableListOf<Pair<Pair<Pair<Long, Long>, Long>, Pair<Pair<Long, Long>, Long>>>()
        for (i in 0 until galaxies.size - 1) {
            for (j in i + 1 until galaxies.size) {
                pairs.add(Pair(galaxies[i], galaxies[j]))
            }
        }

        return pairs.sumOf { (p1, p2) ->
            val point1 = p1.first
            val point2 = p2.first
            abs(point1.first - point2.first) + abs(point1.second - point2.second)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
//    println(part1(testInput))
//    check(part1(testInput) == 374)
//    check(part2(testInput) == 374)
    val input = readInput("Day11")
//    part1(input).println()
    part2(input).println()
}