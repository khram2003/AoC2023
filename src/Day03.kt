fun main() {
    val offsets = listOf(
        Pair(-1, -1),
        Pair(-1, 0),
        Pair(-1, 1),
        Pair(0, -1),
        Pair(0, 1),
        Pair(1, -1),
        Pair(1, 0),
        Pair(1, 1)
    )

    fun isAdjacentToSymbol(input: List<String>, row: Int, col: Int): Boolean {
        for (offset in offsets) {
            val (rowOffset, colOffset) = offset
            val newRow = row + rowOffset
            val newCol = col + colOffset
            if (newRow in input.indices && newCol in input[0].indices && input[newRow][newCol] != '.' && !input[newRow][newCol].isDigit()) {
                return true
            }
        }
        return false
    }

    data class NumberData(
        val number: Int,
        val start: Int,
        val end: Int
    )


    fun extractNumbersFromLineWithStartAndEndIndices(line: String): List<NumberData> {
        val numbers = mutableListOf<NumberData>()
        var number = 0
        var start = 0
        line.forEachIndexed { index, c ->
            if (c.isDigit()) {
                if (number == 0) {
                    start = index
                }
                number = number * 10 + c.toString().toInt()
            } else {
                if (number != 0) {
                    numbers.add(NumberData(number, start, index - 1))
                    number = 0
                }
            }
        }
        if (number != 0) {
            numbers.add(NumberData(number, start, line.length - 1))
        }
        return numbers
    }

    fun part1(input: List<String>): Int {
        val kek = mutableListOf<Int>()
        input.forEachIndexed { lineNum, line ->
            val numbers = extractNumbersFromLineWithStartAndEndIndices(line)
            numbers.forEach { numberData ->
                val (number, start, end) = numberData
                if ((start..end).any { isAdjacentToSymbol(input, lineNum, it) }) {
                    kek.add(number)
                }
            }
        }
        return kek.sum()
    }

    fun part2(input: List<String>): Int {
        val kek = mutableListOf<Pair<Int, NumberData>>()
        input.forEachIndexed { lineNum, line ->
            kek.addAll(extractNumbersFromLineWithStartAndEndIndices(line).map { Pair(lineNum, it) })
        }


        val numbersAreaMap = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()
        kek.forEach { (lineNum, numberData) ->
            val (number, start, end) = numberData
            (lineNum - 1..lineNum + 1).forEach { i ->
                (start - 1..end + 1).forEach { j ->
                    if (numbersAreaMap.containsKey(Pair(i, j))) {
                        numbersAreaMap[Pair(i, j)] =
                            (numbersAreaMap[Pair(i, j)]!! + mutableListOf(number)).toMutableList()
                    } else {
                        numbersAreaMap[Pair(i, j)] = mutableListOf(number)
                    }
                }
            }
        }
        val a = mutableListOf<List<Int>>()
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                if (c == '*' && numbersAreaMap[Pair(row, col)]!!.size == 2)
                    a.add(numbersAreaMap[Pair(row, col)]!!)
            }
        }

        return a.sumOf {
            var prod = 1
            it.forEach {
                prod *= it
            }
            prod
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}