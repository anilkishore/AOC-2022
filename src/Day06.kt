fun main() {

    fun solve(str: String, lim: Int): Int {
        for (i in lim..str.length)
            if (str.subSequence(i-lim, i).toSet().size == lim)
                return i
        return -1
    }

    val input = readInput("Day06")
    println(solve(input[0], 4))
    println(solve(input[0], 14))
}