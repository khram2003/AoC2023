import java.math.BigInteger
import kotlin.math.min
import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Int {
        val lines = input.filter { it.isNotBlank() }
        val instructions = lines.first()
        val nodeMap = lines.drop(1).associate { line ->
            val (node, destinations) = line.split(" = ")
            val (left, right) = destinations.removeSurrounding("(", ")").split(", ").map { it.trim() }
            node to Pair(left, right)
        }

        var current = "AAA"
        var steps = 0
        var instructionIndex = 0

        while (current != "ZZZ") {
            val (left, right) = nodeMap[current] ?: error("Invalid node: $current")
            current = if (instructions[instructionIndex % instructions.length] == 'L') left else right
            instructionIndex++
            steps++
        }

        return steps
    }


    fun lcm(a: BigInteger, b: BigInteger): BigInteger {
        if (a.gcd(b) == BigInteger.ZERO) println("$a, $b")
        return a.multiply(b).divide(a.gcd(b))
    }

    fun lcmList(numbers: List<BigInteger>): BigInteger {
        return numbers.reduce { acc, number -> lcm(acc, number) }
    }

    fun part2(input: List<String>): BigInteger {
        val lines = input.filter { it.isNotBlank() }
        val instructions = lines.first()
        val nodeMap = lines.drop(1).associate { line ->
            val (node, destinations) = line.split(" = ")
            val (left, right) = destinations.removeSurrounding("(", ")").split(", ").map { it.trim() }
            node to Pair(left, right)
        }

        val startingNodes = nodeMap.keys.filter { it.endsWith("A") }
        val visitedFirstTime = mutableMapOf<Pair<String, Int>, BigInteger>()
        var currentNodes = startingNodes.map { it to 0 }
        var steps = BigInteger.ZERO

        while (currentNodes.any { !it.first.endsWith("Z") }) {
            val nextNodes = currentNodes.map { (node, index) ->
                val nextIndex = (index + 1) % instructions.length
                val nextNode = if (instructions[index] == 'L') nodeMap[node]?.first else nodeMap[node]?.second ?: node
                Pair(nextNode, nextIndex)
            }

            for (pair in nextNodes) {
                if (visitedFirstTime.containsKey(pair) && !pair.first!!.endsWith("Z")) {
                    val cycleLengths = visitedFirstTime.filterKeys { !it.first.endsWith("Z") }
                        .mapValues { steps - it.value }
                        .filter { it.value > BigInteger.ZERO }
                    return lcmList(cycleLengths.values.toList())
                }
                visitedFirstTime.putIfAbsent(pair as Pair<String, Int>, steps)
            }

            currentNodes = nextNodes as List<Pair<String, Int>>
            steps++
        }


        return steps
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
//    println(BigInteger.valueOf(Long.MAX_VALUE))
    println(part2(testInput))
//    check(part1(testInput) == 6)
//    check(part2(testInput) == 6)
    val input = readInput("Day08")
//    part1(input).println()
    part2(input).println()
}