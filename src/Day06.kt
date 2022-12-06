fun main() {

    fun solve(str: String, lim: Int): Int {
        val arr = str.toCharArray()
        for(i in lim..  str.length) {
            if (hashSetOf(*arr.copyOfRange(i-lim, i).toTypedArray()).size == lim)
                return i
        }
        return -1
    }

    fun part1(str: String) = solve(str, 4)
    fun part2(str: String) = solve(str, 14)

    val input = readInput("Day06")
    println(part1(input[0]))
    println(part2(input[0]))
}