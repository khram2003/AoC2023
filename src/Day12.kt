data class SpringCondition(val conditions: String, val groupSizes: List<Int>)


fun main() {

    fun parse(line: String): SpringCondition {
        val parts = line.split(" ")
        val conditions = parts[0]
        val groupSizes = parts[1].split(",").map { it.toInt() }

        return SpringCondition(conditions, groupSizes)
    }

    fun rec(conditions: String, groupSizes: List<Int>): Int {

        if (conditions.isEmpty() && groupSizes.isEmpty()) {
            return 1
        }

        if (conditions.isEmpty() && groupSizes.isNotEmpty()) {
            return 0
        }

        if (conditions.isNotEmpty() && groupSizes.isEmpty()) {
            return 0
        }

        var kekek = 0
        conditions.forEachIndexed { i, c ->
            val kek = conditions.substring(0..i).count { it == '?' }
            val newString = conditions.drop(i+1)
            if (groupSizes.isNotEmpty() && kek <= groupSizes[0]) {
                val newGroupSizes = groupSizes.drop(1)
                kekek += rec(newString, newGroupSizes)

            }

            kekek += rec(newString, groupSizes)
        }
        return kekek
    }

    fun part1(input: List<String>): Int {
        val springConditions = input.map { parse(it) }
        return springConditions.sumOf { springCondition ->
//            val unknownPositions = springCondition.conditions.mapIndexedNotNull { index, c -> if (c == '?') index else null }
            val groupSizes = springCondition.groupSizes

            springCondition.conditions.forEachIndexed { i, c ->
                val kek2 = 0
                val kek = springCondition.conditions.substring(0..i).count { it == '?' }
                val newString = springCondition.conditions.drop(i+1)
                if (groupSizes.isNotEmpty() && kek <= groupSizes[0]) {
                    val newGroupSizes = groupSizes.drop(1)
                    kek2 += rec(newString, newGroupSizes)

                }

                kek2 += rec(newString, groupSizes)
            }

        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    println(part1(testInput))
    check(part1(testInput) == 21)
//    check(part2(testInput) == 374)
    val input = readInput("Day12")
    part1(input).println()
//    part2(input).println()
}