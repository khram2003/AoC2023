import kotlin.ranges.LongRange.Companion.EMPTY

private fun LongRange.isNotEmpty(): Boolean = first <= last

fun main() {


    fun parseMap(section: String): Map<LongRange, LongRange> {
        val lines = section.split("\n").drop(1)  // Skip the first line (title)
        val map = mutableMapOf<LongRange, LongRange>()

        lines.forEach { line ->
            val (destStart, sourceStart, length) = line.trim().split("\\s+".toRegex()).map(String::toLong)
            val sourceRange = sourceStart until sourceStart + length
            val destRange = destStart until destStart + length
            map[sourceRange] = destRange
        }

        return map
    }

    fun part1(input: List<String>): Long {
        val sections = input.joinToString("\n").split("\n\n")
        val seeds = sections[0].substringAfter(":").trim().split("\\s+".toRegex()).map(String::toLong)
        val seedSoil = parseMap(sections[1])
        val soilFertilizer = parseMap(sections[2])
        val fertilizerWater = parseMap(sections[3])
        val waterLight = parseMap(sections[4])
        val lightTemperature = parseMap(sections[5])
        val temperatureHumidity = parseMap(sections[6])
        val humidityLocation = parseMap(sections[7])

        return seeds.minOf { seed ->
            var soil = seed
            var found = false
            seedSoil.keys.forEach {
                if (!found && it.contains(seed)) {
                    val soilRange = seedSoil[it]!!
                    val seedIndex = it.indexOf(seed)
                    soil = soilRange.first + seedIndex
                    found = true
                }
            }

            var fertilizer = soil
            found = false
            soilFertilizer.keys.forEach {
                if (!found && it.contains(soil)) {
                    val fertilizerRange = soilFertilizer[it]!!
                    val soilIndex = it.indexOf(soil)
                    fertilizer = fertilizerRange.first + soilIndex
                    found = true
                }
            }

            var water = fertilizer
            found = false
            fertilizerWater.keys.forEach {
                if (!found && it.contains(fertilizer)) {
                    val waterRange = fertilizerWater[it]!!
                    val fertilizerIndex = it.indexOf(fertilizer)
                    water = waterRange.first + fertilizerIndex
                    found = true
                }
            }

            var light = water
            found = false
            waterLight.keys.forEach {
                if (!found && it.contains(water)) {
                    val lightRange = waterLight[it]!!
                    val waterIndex = it.indexOf(water)
                    light = lightRange.first + waterIndex
                    found = true
                }
            }

            var temperature = light
            found = false
            lightTemperature.keys.forEach {
                if (!found && it.contains(light)) {
                    val temperatureRange = lightTemperature[it]!!
                    val lightIndex = it.indexOf(light)
                    temperature = temperatureRange.first + lightIndex
                    found = true
                }
            }

            var humidity = temperature
            found = false
            temperatureHumidity.keys.forEach {
                if (!found && it.contains(temperature)) {
                    val humidityRange = temperatureHumidity[it]!!
                    val temperatureIndex = it.indexOf(temperature)
                    humidity = humidityRange.first + temperatureIndex
                    found = true
                }
            }

            var location = humidity
            found = false
            humidityLocation.keys.forEach {
                if (!found && it.contains(humidity)) {
                    val locationRange = humidityLocation[it]!!
                    val humidityIndex = it.indexOf(humidity)
                    location = locationRange.first + humidityIndex
                    found = true
                }
            }

            location
        }
    }


    fun part2(input: List<String>): Long {
        val sections = input.joinToString("\n").split("\n\n")
        val numbers = sections[0].substringAfter(":").trim().split("\\s+".toRegex()).map(String::toLong)
        val seedLists = numbers.chunked(2).map { (start, end) -> (start..end).toMutableList() }
        val seedSoil = parseMap(sections[1])
        val soilFertilizer = parseMap(sections[2])
        val fertilizerWater = parseMap(sections[3])
        val waterLight = parseMap(sections[4])
        val lightTemperature = parseMap(sections[5])
        val temperatureHumidity = parseMap(sections[6])
        val humidityLocation = parseMap(sections[7])

        fun LongRange.myIntersect(rangeB: List<Long>): List<Long> {
            val rangeA = this
            val intersect = mutableListOf<Long>()
            rangeB.forEach { number ->
                if (number in rangeA) {
                    intersect.add(number)
                }
            }
            return intersect.sorted()
        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    println(part1(testInput))
    check(part1(testInput).toInt() == 35)
//    check(part2(testInput) == 30)
    val input = readInput("Day05")
    part1(input).println()
//    part2(input).println()
}
