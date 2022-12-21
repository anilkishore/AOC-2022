import java.lang.Exception

sealed class Operation {
    abstract fun run(a: Long, b: Long): Long
    abstract fun getA(b: Long, result: Long): Long
    abstract fun getB(a: Long, result: Long): Long
}

object Add : Operation() {
    override fun run(a: Long, b: Long) = a + b

    override fun getA(b: Long, result: Long) = result - b

    override fun getB(a: Long, result: Long) = result - a
}

object Subtract : Operation() {
    override fun run(a: Long, b: Long) = a - b

    override fun getA(b: Long, result: Long) = result + b

    override fun getB(a: Long, result: Long) = a - result
}

object Multiply : Operation() {
    override fun run(a: Long, b: Long) = a * b

    override fun getA(b: Long, result: Long) = result / b

    override fun getB(a: Long, result: Long) = result / a
}

object Divide : Operation() {
    override fun run(a: Long, b: Long) = a / b

    override fun getA(b: Long, result: Long) = result * b

    override fun getB(a: Long, result: Long) = a / result
}


sealed class Variable {
    abstract fun getValue(): Long
}

class NumberVariable(private val value: Long) : Variable() {
    override fun getValue(): Long = value
}

class OperationVariable(val a: String, val b: String, val op: Operation) : Variable() {
    override fun getValue(): Long = op.run(findValue(a), findValue(b))
}

class Result(val value: Long, val isAlongHumn: Boolean)

val params = mutableMapOf<String, Variable>()
val valuePairs = mutableMapOf<String, Result>()

fun findValue(str: String): Long = (params[str] ?: throw Exception("Unknown param $str")).getValue()

fun fillValuePairs(str: String): Result {
    val variable = params[str] ?: throw Exception("Unknown param $str")
    val res = when (variable) {
        is NumberVariable -> Result(variable.getValue(), str == "humn")
        is OperationVariable -> {
            val ap = fillValuePairs(variable.a)
            val bp = fillValuePairs(variable.b)
            Result(variable.op.run(ap.value, bp.value), ap.isAlongHumn or bp.isAlongHumn)
        }
    }
    valuePairs[str] = res
    return res
}

fun trickle(str: String, expValue: Long) {
    if (str == "humn") {
        println("Part 2 ans: $expValue")
        return
    }
    val variable = params[str] as? OperationVariable ?: error("Not OperationVariable")
    val resA = valuePairs[variable.a] ?: error("")
    val resB = valuePairs[variable.b] ?: error("")
    if (resA.isAlongHumn) { // ? op b = exp
        val aVal = variable.op.getA(resB.value, expValue)
        trickle(variable.a, aVal)
    } else { // a op ? = exp
        val bVal = variable.op.getB(resA.value, expValue)
        trickle(variable.b, bVal)
    }
}

fun main() {

    fun getOperation(op: String): Operation {
        return when (op) {
            "+" -> Add
            "-" -> Subtract
            "*" -> Multiply
            "/" -> Divide
            else -> throw Exception("Unknown operator $op")
        }
    }

    fun parse(lines: List<String>) {
        for (line in lines) {
            val ss = line.split(": ", " ")
            params[ss[0]] =
                if (ss.size == 2) NumberVariable(ss[1].toLong())
                else OperationVariable(ss[1], ss[3], getOperation(ss[2]))
        }
    }

    fun part1() {
        println("Part 1 ans: ${findValue("root")}")
    }

    fun part2() {
        val root = params["root"] as? OperationVariable ?: error("No root")
        fillValuePairs("root")
        if (valuePairs[root.a]!!.isAlongHumn)
            trickle(root.a, valuePairs[root.b]!!.value)
        else
            trickle(root.b, valuePairs[root.a]!!.value)
    }

    val input = readInput("Day21")
    parse(input)
    part1()
    part2()
}