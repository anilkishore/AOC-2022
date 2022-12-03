fun main() {
    val elfs = mutableListOf<Int>()
    var str: String? = readlnOrNull()?.trim()
    var cur = 0
    while(str != null) {
        if (str.isEmpty()) {
            elfs.add(cur)
            cur = 0
        } else {
            cur += str.toInt()
        }
        str = readlnOrNull()?.trim()
    }
    elfs.add(cur)

    elfs.sortDescending()
    var ans = 0
    for (i in 0..2)
        ans += elfs[i]

    println(ans)
}