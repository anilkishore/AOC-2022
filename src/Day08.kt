fun main() {

    fun part1(trees: List<String>): Int {
        val n = trees.size
        val mins = Array(n) { CharArray(n) { ':' } }

        for (i in 1 until n - 1)
            for ((js, jd) in arrayOf(1 to 1, n - 2 to -1)) {
                var j = js
                var acc = trees[i][js - jd]
                while (j > 0 && j + 1 < n) {
                    mins[i][j] = minOf(mins[i][j], acc)
                    acc = maxOf(acc, trees[i][j])
                    j += jd
                }

                j = js
                acc = trees[js - jd][i]
                while (j > 0 && j + 1 < n) {
                    mins[j][i] = minOf(mins[j][i], acc)
                    acc = maxOf(acc, trees[j][i])
                    j += jd
                }
            }

        var res = 0
        for (i in 1 until n - 1)
            for (j in 1 until n - 1)
                res += if (trees[i][j] > mins[i][j]) 1 else 0

        return res + 4 * n - 4
    }


    val input = readInput("Day08")
    println(part1(input))
}
