import java.lang.Math.abs

fun main() {

    fun part1(trees: List<String>): Int {
        val n = trees.size
        val mins = Array(n) { CharArray(n) { ':' } }
        val scores = Array(n) { IntArray(n) { 1 } }

        for (i in 1 until n - 1)
            for ((js, jd) in arrayOf(1 to 1, n - 2 to -1)) {
                var j = js
                var acc = trees[i][js - jd]
                var pj = js - jd
                while (j > 0 && j + 1 < n) {
                    mins[i][j] = minOf(mins[i][j], acc)
                    val s: Int =
                    if (trees[i][j] >= acc) {
                        acc = trees[i][j]
                        pj = j
                        abs(j - js + 1)
                    } else {
                        abs(j - pj)
                    }
                    scores[i][j] *= s
                    if (i == 1 && j == 2) println("$js $jd, s = $s")
                    j += jd
                }

                j = js
                acc = trees[js - jd][i]
                while (j > 0 && j + 1 < n) {
                    mins[j][i] = minOf(mins[j][i], acc)
                    acc = maxOf(acc, trees[j][i])
                    val s = if (trees[j][i] >= acc) {
                        acc = trees[j][i]
                        pj = j
                        abs(j - js + 1)
                    } else {
                        abs(j - pj)
                    }
                    scores[j][i] *= s
                    if (i == 2 && j == 1) println("$js $jd, s = $s")
                    j += jd
                }
            }

        var res = 0
        var bestScore = 0
        for (i in 1 until n - 1)
            for (j in 1 until n - 1) {
                res += if (trees[i][j] > mins[i][j]) 1 else 0
                bestScore = maxOf(bestScore, scores[i][j])
            }

        println(bestScore)
        return res + 4 * n - 4
    }


    val input = readInput("Day08_test")
    println(part1(input))
}
