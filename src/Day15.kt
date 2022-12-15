import java.util.regex.Pattern
import kotlin.math.abs
import kotlin.streams.toList

class Segment(var start: Int, var end: Int) : Comparable<Segment> {

    override fun toString() = "[$start, $end]"
    override fun compareTo(other: Segment): Int =
        if (start != other.start) start - other.start
        else end - other.end
}

fun main() {

    val Y_LIMIT = 2000000

    fun part1(input: List<String>): Int {
        val beaconXs = mutableSetOf<Int>()
        val numPattern = Pattern.compile("-?\\d+")
        val segments = input.map { s ->
            val matcher = numPattern.matcher(s)
            val (sx, sy, bx, by) = matcher.results().mapToInt { r -> r.group().toInt() }.toList()
            if (by == Y_LIMIT) beaconXs.add(bx)
            val dx = abs(sx - bx) + abs(sy - by) - abs(sy - Y_LIMIT)
            if (dx < 0) Segment(0, -1)
            else Segment(sx - dx, sx + dx)
        }.filter { it.start <= it.end }.toTypedArray()

        segments.sort()

        var merged = mutableListOf(segments[0])
        for (s in segments) {
            val curEnd = merged.last().end
            if (s.start <= curEnd)
                merged.last().end = maxOf(curEnd, s.end)
            else
                merged.add(s)
        }

//        println(merged.joinToString())

        return merged.sumOf { it.end - it.start + 1 } - beaconXs.size
    }

    val input = readInput("Day15")
    println(part1(input))
}