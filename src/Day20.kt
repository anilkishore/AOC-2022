fun main() {

    fun part1(nums: List<Long>, mult: Long = 1, times: Int = 1): Long {
        val n = nums.size
        val numList = mutableListOf<Pair<Long, Int>>()
        for (i in nums.indices) {
            numList.add(Pair(nums[i] * mult, i))
        }

        repeat(times) {
            for (i in nums.indices) {
                var p = 0

                for (j in nums.indices) {
                    if (numList[j].second == i) {
                        p = j
                        break
                    }
                }

                val k = numList[p].first
                if (k == 0L) continue
                val elem = numList.removeAt(p)
                var nk = ((p + k % (n - 1) + (n - 1)) % (n - 1)).toInt()
                if (nk == 0) nk = (n - 1)
                numList.add(nk, elem)
            }
        }

        var p = 0
        for (j in nums.indices) {
            if (numList[j].first == 0L) {
                p = j
                break
            }
        }

        return listOf(1000, 2000, 3000).sumOf { k ->
            val nk = (p + k) % n
            numList[nk].first
        }
    }

    fun part2(nums: List<Long>): Long {
        return part1(nums, 811589153L, 10)
    }

    val input = readInput("Day20").map { it.toLong() }
    println(part1(input))
    println(part2(input))
}