fun main() {
    fun part1(input: List<String>): Int {
        val maxCubes = mapOf("red" to 12, "green" to 13, "blue" to 14)
        return input.sumOf { game ->
            val gameId = game.substringAfter("Game ").substringBefore(":").toInt()
            val rounds = game.substringAfter(": ").split("; ")

            if (rounds.all { round ->
                    round.split(", ").all {
                        val (count, color) = it.split(" ")
                        count.toInt() <= maxCubes[color]!!
                    }
                }) gameId else 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { game ->
            val maxCubes = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            val rounds = game.substringAfter(": ").split("; ")
            rounds.forEach { round ->
                round.split(", ").forEach {
                    val (count, color) = it.split(" ")
                    if (count.toInt() > maxCubes[color]!!) maxCubes[color] = count.toInt()
                }
            }

            maxCubes["red"]!! * maxCubes["green"]!! * maxCubes["blue"]!!
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2286)
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}