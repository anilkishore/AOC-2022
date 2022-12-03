fun main() {

    fun part1(input: List<String>): Int {
        return input.asSequence().map {
            val first = it.substring(0, it.length / 2).asIterable()
            val second = it.substring(it.length / 2).asIterable()
            val ch = first.intersect(second).first()
            if (ch in 'a'..'z') (ch - 'a' + 1) else 26 + (ch - 'A' + 1)
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.asSequence().chunked(3).map {
            val ch = it[0].asIterable().intersect(it[1].asIterable()).intersect(it[2].asIterable()).first()
            if (ch in 'a'..'z') (ch - 'a' + 1) else 26 + (ch - 'A' + 1)
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println(part1(testInput))
    check(part1(testInput) == 157)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}