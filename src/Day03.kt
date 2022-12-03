fun main() {

    fun part1(input: List<String>): Int {
        return input.asSequence().map {
            val first = it.substring(0, it.length / 2).toSet()
            val second = it.substring(it.length / 2).toSet()
            val ch = first.intersect(second).first()
            if (ch in 'a'..'z') (ch - 'a' + 1) else 26 + (ch - 'A' + 1)
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.asSequence().chunked(3).map {
            val ch = it.map { s -> s.toSet() }.reduce { a, b -> a.intersect(b)}.first()
            if (ch in 'a'..'z') (ch - 'a' + 1) else 26 + (ch - 'A' + 1)
        }.sum()
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}