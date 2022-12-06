fun main() {

    fun solve(str: String, lim: Int): Int {
        val arr = str.toCharArray()
        for (i in lim..str.length)
            if (hashSetOf(*arr.copyOfRange(i - lim, i).toTypedArray()).size == lim)
                return i
        return -1
    }

    val input = readInput("Day06")
    println(solve(input[0], 4))
    println(solve(input[0], 14))
}