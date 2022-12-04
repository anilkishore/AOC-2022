fun main() {

    fun part1(input: List<String>): Int {

        fun IntRange.fullyContains(other: IntRange): Boolean =
            this.contains(other.first) && this.contains(other.last)

        return input.sumOf {
            val ranges = it.split(",").map { s ->
                val ss = s.split("-")
                IntRange(ss[0].toInt(), ss[1].toInt())
            }
            (if (ranges[0].fullyContains(ranges[1])
                or ranges[1].fullyContains(ranges[0])) 1 else 0).toInt()
        }
    }

    fun part2(input: List<String>): Int {

        return input.sumOf {
            val ranges = it.split(",").map { s ->
                val ss = s.split("-")
                IntRange(ss[0].toInt(), ss[1].toInt())
            }
            (if (ranges[0].intersect(ranges[1]).isNotEmpty()) 1 else 0).toInt()
        }
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}