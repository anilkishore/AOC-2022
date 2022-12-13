import java.util.*

fun main() {

    fun endIndex(str: String, s: Int): Int {
        var cnt = 0
        var i = s
        while (true) {
            if (str[i] == '[') ++cnt else {
                if (str[i] == ']') --cnt
            }
            if (cnt == 0) return i;
            ++i
        }
    }

    fun numIndex(str: String, s: Int, e: Int): Int {
        var i = s
        while (i < e && str[i].isDigit())
            ++i
        return i
    }

    fun compare(str1: String, s1: Int, e1: Int, str2: String, s2: Int, e2: Int, sp: Int = 0): Int {
        if (s1 >= e1) return if (s2 >= e2) 0 else -1
        if (s2 >= e2) return 1
        if (str1[s1] == ',') return compare(str1, s1 + 1, e1, str2, s2, e2)
        if (str2[s2] == ',') return compare(str1, s1, e1, str2, s2 + 1, e2)
        val ss1: Int
        val ss2: Int
        val res: Int
        if (str1[s1] == '[') {
            ss1 = endIndex(str1, s1)
            res = if (str2[s2] == '[') {
                ss2 = endIndex(str2, s2)
                compare(str1, s1 + 1, ss1, str2, s2 + 1, ss2, sp + 4)
            } else {
                ss2 = numIndex(str2, s2, e2)
                compare(str1, s1 + 1, ss1, str2, s2, ss2, sp + 4)
            }
        } else if (str2[s2] == '[') {
            ss1 = numIndex(str1, s1, e1)
            ss2 = endIndex(str2, s2)
            res = compare(str1, s1, ss1, str2, s2 + 1, ss2, sp + 4)
        } else {
            ss1 = numIndex(str1, s1, e1)
            ss2 = numIndex(str2, s2, e2)
            res = str1.substring(s1, ss1).toInt().compareTo(str2.substring(s2, ss2).toInt())
        }
        return if (res != 0) res else compare(str1, ss1 + 1, e1, str2, ss2 + 1, e2, sp + 4)
    }

    fun compare(str1: String, str2: String): Int {
        return compare(str1, 0, str1.length, str2, 0, str2.length)
    }

    fun part1(input: List<String>): Int {
        return input.chunked(3).mapIndexed { index, packs ->
            if (compare(packs[0], packs[1]) < 0) index + 1 else 0
        }.sum()
    }

    fun part2(input: List<String>) {
        val more = listOf("[[6]]", "[[2]]")
        val mlist = more.toMutableList()
        input.filter { it.isNotEmpty() }.forEach { mlist.add(it) }
        mlist.sortWith { s1, s2 -> compare(s1, s2) }
        var res = 1
        for (i in mlist.indices)
            if (mlist[i] in more)
                res *= (i + 1)
        println(res)
    }

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}