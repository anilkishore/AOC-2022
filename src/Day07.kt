class Dir(val par: Dir? = null, var size: Int = 0, val subDirs: HashMap<String, Dir> = hashMapOf())

fun main() {

    var part2ans: Int = 7e7.toInt()

    fun foldSizes(cur: Dir) {
        for (subDir in cur.subDirs.values) {
            foldSizes(subDir)
            cur.size += subDir.size
        }
    }

    fun findSum(cur: Dir, needSize: Int): Int {
        if (cur.size >= needSize) {
            part2ans = minOf(part2ans, cur.size)
        }
        var res = if (cur.size <= 100000) cur.size else 0
        for (subDir in cur.subDirs.values) {
            res += findSum(subDir, needSize)
        }
        return res
    }

    fun solve(commands: List<String>): Int {
        val root = Dir()
        var cur = root
        for (cmd in commands.drop(1)) {
            val strs = cmd.split(" ")
            if (cmd.startsWith("$ ")) {
                if (strs[1] == "cd") {
                    cur = (if (strs[2] == "..") cur.par else cur.subDirs[strs[2]])!!
                }
            } else {
                if (cmd.startsWith("dir ")) {
                    cur.subDirs[strs[1]] = Dir(cur)
                } else {
                    cur.size += strs[0].toInt()
                }
            }
        }
        foldSizes(root)
        val needSize = (3e7 - (7e7 - root.size)).toInt()
        return findSum(root, needSize)
    }

    val input = readInput("Day07")
    println(solve(input))
    println(part2ans)
}