fun main() {

    fun part1(nums: List<Int>): Int {
        val n = nums.size
        val numList = mutableListOf<Pair<Int, Int>>()
        for (i in nums.indices) {
            numList.add(Pair(nums[i], i))
        }
        for (i in nums.indices) {
            var p = 0

            for(j in nums.indices) {
                if (numList[j].second == i) {
                    p = j
                    break
                }
            }

            // 1, 2, -3, 3, -2, 0, 4
            val k = numList[p].first
        }
        return 0
    }

    val input = readInput("Day20").map { it.toInt() }
    println(part1(input))
}