import java.util.*
import java.util.regex.Pattern
import kotlin.streams.toList

fun main() {

    fun part1(input: List<String>, together: Boolean = false) {
        val (moves, colsExtra) = input.partition { it.startsWith("move") }
        val cols = colsExtra.dropLast(2).reversed()
        val n = (cols[0].length + 1) / 4
        val stacks = Array<Stack<Char>>(n + 1) { Stack() }
        cols.forEach {
            it.chunkedSequence(4)
                .mapIndexed { i, s ->
                    if (s[1] != ' ')
                        stacks[i + 1].push(s[1])
                }.toList()
        }

        val numPattern = Pattern.compile("\\d+")
        moves.forEach {
            val matcher = numPattern.matcher(it)
            matcher.results()
            val (cnt, from, to) = matcher.results().mapToInt { r -> r.group().toInt() }.toList()
            var fromStk = stacks[from]

            if (together) {
                fromStk = Stack()
                repeat(cnt) {
                    fromStk.push(stacks[from].pop())
                }
            }

            repeat(cnt) {
                stacks[to].push(fromStk.pop())
            }
        }

        for (i in 1..n)
            print(if (stacks[i].isEmpty()) " " else stacks[i].peek())
        println()
    }

    fun part2(input: List<String>) {
        part1(input, true)
    }

    val input = readInput("Day05")
    part1(input)
    part2(input)
}