
class TestMeKotlin @JvmOverloads constructor(val a: String, val b: String? = "default")

class Value(val num: Int) {
    override fun equals(other: Any?): Boolean {
        if (other !is Value)
            return false
        return this.num == other.num
    }

    override fun hashCode(): Int {
        return num
    }
}
fun main() {
    val set = LinkedHashSet<Value>()

    set.add(Value(1))
    set.add(Value(2))
    set.add(Value(1))
    set.add(Value(2))

    val it = set.iterator()
    while (it.hasNext())
        println(it.next().num)
}