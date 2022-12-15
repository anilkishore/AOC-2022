import java.util.regex.Pattern
import kotlin.math.abs
import kotlin.streams.toList
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class Segment(var start: Int, var end: Int) : Comparable<Segment> {

    fun overlap(other: Segment): Int {
        if (other.end < other.start)
            return 0
        val maxStart = maxOf(start, other.start)
        val minEnd = minOf(end, other.end)
        return maxOf(0, minEnd - maxStart + 1)
    }

    override fun compareTo(other: Segment): Int =
        if (start != other.start) start - other.start
        else end - other.end
}

@OptIn(ExperimentalTime::class)
fun main() {

    val lim = 4000000

    fun part1(input: List<List<Int>>, yLimit: Int = 2000000): Int {
        val beaconXs = mutableSetOf<Int>()
        val segments = input.map { s ->
            val (sx, sy, bx, by) = s
            if (by == yLimit) beaconXs.add(bx)
            val dx = abs(sx - bx) + abs(sy - by) - abs(sy - yLimit)
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

        // Part-2
        val xLimit = Segment(0, lim)
        for (i in 0 until merged.size - 1) {
            val gap = Segment(merged[i].end + 1, merged[i + 1].start - 1)
            if (gap.overlap(xLimit) == 1) {
                println("Part 2 answer = ${gap.start.toLong() * lim + yLimit}")
            }
        }

        return merged.sumOf { it.end - it.start + 1 } - beaconXs.size
    }

    fun part2(input: List<List<Int>>) {
        for (y in 0..lim)
            part1(input, y)
    }

    val input = readInput("Day15")
    val numPattern = Pattern.compile("-?\\d+")
    val numsList = input.map { s ->
        val matcher = numPattern.matcher(s)
        matcher.results().mapToInt { r -> r.group().toInt() }.toList()
    }
    println(part1(numsList))
    println("Runtime (ms) = ${measureTime { part2(numsList) }.inWholeMilliseconds}") // 1.5-1.7s
}