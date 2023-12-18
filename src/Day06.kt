import kotlin.math.min
import kotlin.math.pow

fun main() {




    fun part1(input: List<String>): Int {
        val raceTimes = input[0].substringAfter("Time:").trim().split("\\s+".toRegex()).map(String::toInt)
        val recordDistances = input[1].substringAfter("Distance:").trim().split("\\s+".toRegex()).map(String::toInt)

        val totalWays = raceTimes.zip(recordDistances).map{ (totalTime, recordDistance) ->
            var waysToWin = 0
            for (timeHoldingButton in 0 until totalTime) {
                val distance = timeHoldingButton * (totalTime - timeHoldingButton)
                if (distance > recordDistance) {
                    waysToWin++
                }
            }
            waysToWin
        }.reduce(Int::times)

        return totalWays

    }

    fun part2(input: List<String>): Int {
        val totalTimeList = input[0].substringAfter("Time:").trim().split("\\s+".toRegex())
        val recordDistanceList = input[1].substringAfter("Distance:").trim().split("\\s+".toRegex())
        val totalTime = totalTimeList.joinToString("").toLong()
        val recordDistance = recordDistanceList.joinToString("").toLong()

        var waysToWin = 0
        for (timeHoldingButton in 0 until totalTime) {
            val distance = timeHoldingButton * (totalTime - timeHoldingButton)
            if (distance > recordDistance) {
                waysToWin++
            }
        }
        return waysToWin
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
//    println(part2(testInput))
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}