import java.util.*
import kotlin.math.abs

fun main() {

    val input = readInput("Day18")
    val cubes = input.map { s -> s.split(",").map { it.toInt() } }
    val cubeSet = cubes.toMutableSet()
    val max = cubes.flatten().max() + 1
    val min = cubes.flatten().min() - 1

    fun part1(): Int {
        var res = cubes.size * 6
        for (i in cubes.indices)
            for (j in i + 1 until cubes.size) {
                if ((0..2).sumOf { k -> abs(cubes[i][k] - cubes[j][k]) } == 1)
                    res -= 2
            }
        return res
    }

    val airVis = mutableSetOf<List<Int>>()

    fun dfs(start: List<Int>) {
        val stack = Stack<List<Int>>()
        stack.add(start)
        while (stack.isNotEmpty()) {
            val cube = stack.pop()
            airVis.add(cube)
            for (d in listOf(-1, 1))
                repeat(3) { k ->
                    val ncube = (0..2).map { i -> cube[i] + if (i == k) d else 0 }.toList()
                    if (ncube.max() <= max &&
                        ncube.min() >= min &&
                        !cubeSet.contains(ncube) &&
                        !airVis.contains(ncube)
                    ) {
                        stack.push(ncube)
                    }
                }
        }
    }

    fun part2(): Int {
        dfs(listOf(min, min, min))
        return cubes.sumOf { cube ->
            listOf(-1, 1).map { d ->
                listOf(0, 1, 2).map { k ->
                    val ncube = (0..2).map { i -> cube[i] + if (i == k) d else 0 }.toList()
                    if (airVis.contains(ncube)) 1 else 0
                }
            }.flatten().sum()
        }
    }

    println(part1())
    println(part2())
}