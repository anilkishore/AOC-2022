class Valve(val id: String, val flowRate: Int = 0, val edges: List<String> = listOf())

fun main() {

    val input = readInput("Day16")
    val valves = input.map { inp ->
        val ss = inp.split(";")
        val s1 = ss[0].split(" ")
        val rate = ss[0].split("=")[1].toInt()
        val edges = ss[1].split(", ").map { it.takeLast(2) }
        Valve(s1[1], rate, edges)
    }.associateBy { it.id }

    fun dfs(u: Valve, tLeft: Int, used: Set<String>): Int {
        if (tLeft <= 1)
            return 0
//        println("At uid = ${u.id}")
        var best = 0
        if (u.flowRate > 0 && !used.contains(u.id)) {
            best = maxOf(best, (u.flowRate * tLeft - 1) + dfs(u, tLeft - 1, used.union(listOf("u.id"))))
        }
        for (vid in u.edges) {
            best = maxOf(best,  dfs(valves[vid]!!, tLeft - 1, used))
        }
        return best
    }

    fun part1(): Int {
        val src = valves["AA"] ?: return 0
        return dfs(src, 30, mutableSetOf<String>())
    }

    println(part1())
}