import kotlin.math.ceil

fun calculateEnclosedArea(
    pipeLayout: List<String>,
    pipeNetwork: Map<Pair<Int, Int>, List<Pair<Int, Int>>>,
    start: Pair<Int, Int>
): Int {
    val rows = pipeLayout.size
    val cols = pipeLayout[0].length
    val outside = Array(rows) { Array(cols) { false } }

    for (y in pipeLayout.indices) {
        for (x in pipeLayout[y].indices) {
            if (y == 0 || x == 0 || y == rows - 1 || x == cols - 1) {
                floodFillOutside(pipeLayout, outside, x, y)
            }
        }
    }

    return countInsideTiles(pipeLayout, outside, pipeNetwork)
}

fun floodFillOutside(pipeLayout: List<String>, outside: Array<Array<Boolean>>, x: Int, y: Int) {
    if (y !in pipeLayout.indices || x !in pipeLayout[y].indices || outside[y][x] || pipeLayout[y][x] != '.') return

    outside[y][x] = true
    floodFillOutside(pipeLayout, outside, x + 1, y)
    floodFillOutside(pipeLayout, outside, x - 1, y)
    floodFillOutside(pipeLayout, outside, x, y + 1)
    floodFillOutside(pipeLayout, outside, x, y - 1)
}

fun countInsideTiles(
    pipeLayout: List<String>,
    outside: Array<Array<Boolean>>,
    pipeNetwork: Map<Pair<Int, Int>, List<Pair<Int, Int>>>
): Int {
    var count = 0
    for (y in pipeLayout.indices) {
        for (x in pipeLayout[y].indices) {
            if (pipeLayout[y][x] == '.' && !outside[y][x] && Pair(x, y) !in pipeNetwork) {
                count++
            }
        }
    }
    return count
}

fun parsePipeNetwork(pipeLayout: MutableList<MutableList<Char>>): Map<Pair<Int, Int>, List<Pair<Int, Int>>> {
    val network = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Int, Int>>>()

    for (y in pipeLayout.indices) {
        for (x in pipeLayout[y].indices) {
            val tile = pipeLayout[y][x]
            if (tile != '.' && tile != 'S') {
                val neighbors = getNeighbors(x, y, tile, pipeLayout)
                network[Pair(x, y)] = neighbors.toMutableList()
            }
        }
    }

    return network
}

fun printDistanceMatrix(
    pipeLayout: MutableList<MutableList<Char>>,
    pipeNetwork: Map<Pair<Int, Int>, List<Pair<Int, Int>>>,
    start: Pair<Int, Int>
) {
    val distanceMatrix = bfsDistanceMatrix(pipeNetwork, start, pipeLayout.size, pipeLayout[0].size)

    for (row in distanceMatrix) {
        println(row.joinToString(" ") { if (it == -1) "." else it.toString() })
    }
}

fun bfsDistanceMatrix(
    pipeNetwork: Map<Pair<Int, Int>, List<Pair<Int, Int>>>,
    start: Pair<Int, Int>,
    rows: Int,
    cols: Int
): Array<Array<Int>> {
    val distanceMatrix = Array(rows) { Array(cols) { -1 } }
    val queue = ArrayDeque<Pair<Pair<Int, Int>, Int>>()
    queue.add(Pair(start, 0))

    while (queue.isNotEmpty()) {
        val (current, distance) = queue.removeFirst()
        val (x, y) = current

        if (distanceMatrix[y][x] == -1) {
            distanceMatrix[y][x] = distance
            pipeNetwork[current]?.forEach { neighbor ->
                queue.add(Pair(neighbor, distance + 1))
            }
        }
    }

    return distanceMatrix
}


fun findStartPosition(pipeLayout: MutableList<MutableList<Char>>): Pair<Int, Int> {
    for (y in pipeLayout.indices) {
        for (x in pipeLayout[y].indices) {
            if (pipeLayout[y][x] == 'S') {
                return Pair(x, y)
            }
        }
    }
    throw IllegalArgumentException("Starting position 'S' not found")
}

fun bfsMaxDistance(pipeNetwork: Map<Pair<Int, Int>, List<Pair<Int, Int>>>, start: Pair<Int, Int>): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    val queue = ArrayDeque<Pair<Pair<Int, Int>, Int>>()
    queue.add(Pair(start, 0))
    var maxDistance = 0

    while (queue.isNotEmpty()) {
        val (current, distance) = queue.removeFirst()
        if (visited.add(current)) {
            maxDistance = maxOf(maxDistance, distance)
            pipeNetwork[current]?.forEach { neighbor ->
                queue.add(Pair(neighbor, distance + 1))
            }
        }
    }

    return maxDistance
}

fun getNeighbors(x: Int, y: Int, tile: Char, layout: MutableList<MutableList<Char>>): List<Pair<Int, Int>> {
    return when (tile) {
        '|' -> listOfNotNull(
            getNeighbor(x, y - 1, layout, listOf('F', '|', '7')),
            getNeighbor(x, y + 1, layout, listOf('J', '|', 'L'))
        )

        '-' -> listOfNotNull(
            getNeighbor(x - 1, y, layout, listOf('F', '-', 'L')),
            getNeighbor(x + 1, y, layout, listOf('J', '-', '7'))
        )

        'L' -> listOfNotNull(
            getNeighbor(x, y - 1, layout, listOf('|', 'F', '7')),
            getNeighbor(x + 1, y, layout, listOf('-', 'J', '7'))
        )

        'J' -> listOfNotNull(
            getNeighbor(x, y - 1, layout, listOf('|', 'F', '7')),
            getNeighbor(x - 1, y, layout, listOf('-', 'L', 'F'))
        )

        '7' -> listOfNotNull(
            getNeighbor(x, y + 1, layout, listOf('|', 'J', 'L')),
            getNeighbor(x - 1, y, layout, listOf('-', 'L', 'F'))
        )

        'F' -> listOfNotNull(
            getNeighbor(x, y + 1, layout, listOf('|', 'J', 'L')),
            getNeighbor(x + 1, y, layout, listOf('-', 'J', '7'))
        )

        else -> emptyList()
    }
}

fun getNeighbor(
    x: Int,
    y: Int,
    layout: MutableList<MutableList<Char>>,
    possibleNeighbors: List<Char>
): Pair<Int, Int>? {
    if (y in layout.indices && x in layout[y].indices && layout[y][x] in possibleNeighbors) {
        return Pair(x, y)
    }
    return null
}

fun main() {

    fun fixStartWith(input: List<MutableList<Char>>, start: Pair<Int, Int>, fix: Char) {
        val (x, y) = start
        println(start)
        if (y - 1 in input.indices && y + 1 in input.indices && input[y - 1][x] != '.' && input[y + 1][x] != '.' && fix == '|') {
            input[y][x] = '|'
        } else if (x - 1 in input[y].indices && x + 1 in input[y].indices && input[y][x - 1] != '.' && input[y][x + 1] != '.' && fix == '-') {
            input[y][x] = '-'
        } else if (y - 1 in input.indices && x + 1 in input[y].indices && input[y - 1][x] != '.' && input[y][x + 1] != '.' && fix == 'L') {
            input[y][x] = 'L'
        } else if (y - 1 in input.indices && x - 1 in input[y].indices && input[y - 1][x] != '.' && input[y][x - 1] != '.' && fix == 'J') {
            input[y][x] = 'J'
        } else if (y + 1 in input.indices && x - 1 in input[y].indices && input[y + 1][x] != '.' && input[y][x - 1] != '.' && fix == '7') {
            input[y][x] = '7'
        } else if (y + 1 in input.indices && x + 1 in input[y].indices && input[y + 1][x] != '.' && input[y][x + 1] != '.' && fix == 'F') {
            input[y][x] = 'F'
        } else {
            throw IllegalArgumentException("Cannot fix start with $fix")
        }

    }

    val fixes = listOf('|', '-', 'L', 'J', '7', 'F')

    fun part1(input_: List<String>): Int {
        val input = mutableListOf<MutableList<Char>>()
        input_.forEach { line ->
            input.add(line.toMutableList())
        }
        val start = findStartPosition(input)
        var maxDist = 0
        fixes.forEach { fix ->
            try {
                fixStartWith(input, start, fix)
                val pipeNetwork = parsePipeNetwork(input)
//                println(pipeNetwork)
                maxDist = maxOf(maxDist, bfsMaxDistance(pipeNetwork, start))
                if (fix == '|') {
                    println(printDistanceMatrix(input, pipeNetwork, start))
                }

            } catch (e: IllegalArgumentException) {
                println("sasi")
//                println(e.message)
            }

        }



        return ceil(0.5 * maxDist).toInt()

    }

    fun part2(input_: List<String>): Int {

        val input = mutableListOf<MutableList<Char>>()
        input_.forEach { line ->
            input.add(line.toMutableList())
        }
        val start = findStartPosition(input)
        var maxDist = 0
        fixes.forEach { fix ->
            try {
                fixStartWith(input, start, fix)
                val pipeNetwork = parsePipeNetwork(input)
//                println(pipeNetwork)
//                maxDist = maxOf(maxDist, bfsMaxDistance(pipeNetwork, start))
                val distanceMatrix = bfsDistanceMatrix(pipeNetwork, start, input.size, input[0].size)
                
            } catch (e: IllegalArgumentException) {
                println("sasi")
//                println(e.message)
            }

        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
//    println(part1(testInput))

//    check(part1(testInput) == 4)
    check(part2(testInput) == 4)
    val input = readInput("Day10")
//    part1(input).println()
    part2(input).println()
}