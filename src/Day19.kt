import java.util.regex.Pattern
import kotlin.streams.toList

fun main() {

    fun part1(lines: List<String>): Int {
        val numPattern = Pattern.compile("\\d+")
        return lines.sumOf { s ->
            val nums = numPattern.matcher(s).results().mapToInt { it.group().toInt() }.toList()
            val or = intArrayOf(nums[1])
            val cl = intArrayOf(nums[2])
            val ob = intArrayOf(nums[3], nums[4])
            val geo = intArrayOf(nums[5], 0, nums[6])
            or[0] = 1

            val r = 1
            nums[0] * r
        }
    }

    val input = readInput("Day19")
    println(part1(input))
}