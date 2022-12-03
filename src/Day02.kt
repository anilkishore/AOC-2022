// $ kotlinc Day02.kt -include-runtime -d test.jar
// $ java -jar test.jar < input.txt

fun main() {

    // Part 2
    fun score(inp: String?): Int {
        if (inp.isNullOrEmpty())
            return 0;
        val a = inp[0] - 'A'
        val res =
            when (inp[2] - 'X') {
                0 -> 0 + (a+2)%3 + 1
                1 -> 3 + a + 1
                else -> 6 + (a+1)%3 + 1
            }

        return res
    }

    var str: String? = readlnOrNull()?.trim()
    var res = 0

    while(!str.isNullOrEmpty()) {
        res += score(str)
        str = readlnOrNull()?.trim()
    }

    println(res)
}