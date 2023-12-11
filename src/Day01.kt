fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = line.firstOrNull { it.isDigit() }?.toString() ?: "0"
            val lastDigit = line.lastOrNull { it.isDigit() }?.toString() ?: "0"
            (firstDigit + lastDigit).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val numberWords = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )
        return input.sumOf { line ->
            val digit = Regex("(${numberWords.keys.joinToString("|")}|1|2|3|4|5|6|7|8|9)").find(line)?.value ?: "0"
            val firstDigit = numberWords[digit] ?: digit

            val reversed = line.reversed()
            val reversedKeys = numberWords.keys.map { it.reversed() }
            val digit1 = Regex("(${reversedKeys.joinToString("|")}|1|2|3|4|5|6|7|8|9)").find(reversed)?.value ?: "0"
            val lastDigit = numberWords[digit1.reversed()] ?: digit1

            (firstDigit + lastDigit).toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
