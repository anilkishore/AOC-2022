import java.util.LinkedList
import java.util.Queue

fun main() {

    fun part1(grid: List<String>): Int {

        data class Cell(val r: Int, val c: Int) {
            fun neighbors(): List<Cell> = listOf(Cell(r-1, c), Cell(r, c-1), Cell(r+1, c), Cell(r, c+1))

            fun elev(): Char {
                val c = grid[r][c]
                if (c == 'S')
                    return 'a'
                else if (c == 'E')
                    return 'z'
                return c
            }
        }

        fun findIt(ch: Char): Cell {
            for (i in grid.indices) {
                var j = grid[i].indexOf(ch)
                if (j >= 0)
                    return Cell(i, j)
            }
            return Cell(-1, -1)
        }

        val start = findIt('S')
        var end = findIt('E')

        val Q: Queue<Cell> =  LinkedList()
        val visited = hashSetOf<Cell>()


        fun checkAndAdd(cell: Cell) =
            if (visited.contains(cell)) false
            else {
                Q.offer(cell)
                visited.add(cell)
                cell == end
            }

        checkAndAdd(start)
        var steps = 0
        while (Q.isNotEmpty()) {
            ++steps
            println()
            println("Steps #$steps")
            for (i in Q.size downTo 1) {
                val curc = Q.poll()
                println(curc)
                curc?.neighbors()?.filter {
                    c -> c.r in grid.indices && c.c in grid[0].indices && (curc.elev() <= c.elev() + 1)
                }?.forEach {
                    println(" $it")
                    if (checkAndAdd(it)) {
                        return steps
                    }
                }
            }
            println(Q)
        }
        return -1
    }

    val input = readInput("Day12")
    println(part1(input))
}


