fun main() {

    val grid = mutableSetOf<Pair<Int, Int>>()

    fun part1(input: List<String>, bottom: Boolean = false): Int {

        fun diff(x: Int, y: Int) =
            if (x == y) 0 else { if (x < y) 1 else -1 }

        var maxy = 0

        for (s in input) {
            val splits = s.split(" -> ")
            for (i in 0 until splits.size - 1) {
                val a = splits[i].split(",")
                val b = splits[i+1].split(",")
                var ax = a[0].toInt()
                var ay = a[1].toInt()
                val bx = b[0].toInt()
                val by = b[1].toInt()
                maxy = maxOf(maxy, ay, by)
                val dx = diff(ax, bx)
                val dy = diff(ay, by)
                grid.add(Pair(ax, ay))
                while(ax != bx || ay != by) {
                    ax += dx
                    ay += dy
                    grid.add(Pair(ax, ay))
                }
            }
        }

        var res = 0
        val max = 200

        while (true) {
            var x = 500
            var y = 0
            var steps = 0
            while (steps < max) {
                ++steps

                if (!grid.contains(Pair(x, y+1))) {
                    ++y
                } else if (!grid.contains(Pair(x-1, y+1))) {
                    --x
                    ++y
                } else if (!grid.contains(Pair(x+1, y+1))) {
                    ++x
                    ++y
                } else {
                    break
                }

                if (y + 1 == maxy)
                    break;
            }

//            if (bottom) {
//                println(">>>>  here")
//                if (x == 500 && y == 0)
//                    break;
//            } else
                if (steps == max)
                break;

            grid.add(Pair(x, y))
            println("Settled at $x, $y. Steps = $steps")
            ++res
        }

        return res
    }

    fun part2(input: List<String>) = part1(input, true)

    val input = readInput("Day14")
    println(part1(input))
//    println(part2(input))
}