fun main() {

    fun part1(ops: List<String>): Int {
        var value = 1
        var res = 0
        var cycle = 0
        for (s in ops) {
            ++cycle
            if (s.startsWith("noop")) {
                if ((cycle - 20) % 40 == 0)
                    res += cycle * value
            } else {
                if ((cycle - 20) % 40 == 0)
                    res += cycle * value
                ++cycle
                if ((cycle - 20) % 40 == 0)
                    res += cycle * value
                value += s.split(" ")[1].toInt()
            }
        }
        return res
    }

    fun pp(value: Int, position: Int): Char =
        if (kotlin.math.abs((position % 40) - value) <= 1) '#' else '.'

    fun part2(ops: List<String>) {
        var value = 1
        var cycle = 0
        for (s in ops) {
            if (cycle % 40 == 0) println()
            ++cycle
            if (s.startsWith("noop")) {
                print(pp(value, cycle - 1))
            } else {
                print(pp(value, cycle - 1))
                if (cycle % 40 == 0) println()
                ++cycle
                print(pp(value, cycle - 1))
                value += s.split(" ")[1].toInt()
            }
        }
    }

    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}
