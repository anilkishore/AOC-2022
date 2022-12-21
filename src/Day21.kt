import java.lang.Exception

fun add(a: Long, b: Long): Long = a + b
fun subtract(a: Long, b: Long): Long = a - b
fun multiply(a: Long, b: Long): Long = a * b
fun divide(a: Long, b: Long): Long = a / b

sealed class Variable {
    abstract fun getValue(): Long
}

class NumberVariable(private val value: Long) : Variable() {
    override fun getValue(): Long = value
}

class OperationVariable(val a: String, val b: String, val op: (Long, Long) -> Long) : Variable() {
    override fun getValue(): Long = op(findValue(a), findValue(b))
    fun evaluate(a: Long, b: Long): Long = op(a, b)
    fun findA(bVal: Long, expValue: Long): Long {
        return when (op) {
            ::add -> expValue - bVal
            ::subtract -> expValue + bVal
            ::multiply -> expValue / bVal
            ::divide -> expValue * bVal
            else -> error("No-op")
        }
    }

    fun findB(aVal: Long, expValue: Long): Long {
        return when (op) {
            ::add -> expValue - aVal
            ::subtract -> aVal - expValue
            ::multiply -> expValue / aVal
            ::divide -> aVal / expValue
            else -> error("No-op")
        }
    }
}

val params = mutableMapOf<String, Variable>()
val valuePairs = mutableMapOf<String, Pair<Long, Boolean>>()
fun findValue(str: String): Long {
    val variable = params[str] ?: throw Exception("Unknown param $str")
    return variable.getValue()
}

fun fillValuePairs(str: String): Pair<Long, Boolean> {
    val variable = params[str] ?: throw Exception("Unknown param $str")
    val res = when (variable) {
        is NumberVariable -> Pair(variable.getValue(), str == "humn")
        is OperationVariable -> {
            val ap = fillValuePairs(variable.a)
            val bp = fillValuePairs(variable.b)
            Pair(variable.evaluate(ap.first, bp.first), ap.second or bp.second)
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
    if (valuePairs[variable.a]!!.second) {
        // ? op b
        val bVal = valuePairs[variable.b]!!.first
        val aVal = variable.findA(bVal, expValue)
        trickle(variable.a, aVal)
    } else {
        // a op ?
        val aVal = valuePairs[variable.a]!!.first
        val bVal = variable.findB(aVal, expValue)
        trickle(variable.b, bVal)
    }
}

fun main() {

    fun getOp(op: String): (Long, Long) -> Long {
        return when (op) {
            "+" -> ::add
            "-" -> ::subtract
            "*" -> ::multiply
            "/" -> ::divide
            else -> throw Exception("Unknown operator $op")
        }
    }

    fun parse(lines: List<String>) {
        for (line in lines) {
            val ss = line.split(": ", " ")
            params[ss[0]] =
                if (ss.size == 2) NumberVariable(ss[1].toLong())
                else OperationVariable(ss[1], ss[3], getOp(ss[2]))
        }
    }

    fun part1() {
        println("Part 1 ans: ${findValue("root")}")
    }

    fun part2() {
        val root = params["root"] as? OperationVariable ?: error("No root")
        fillValuePairs("root")
        if (valuePairs[root.a]!!.second)
            trickle(root.a, valuePairs[root.b]!!.first)
        else
            trickle(root.b, valuePairs[root.a]!!.first)
    }

    val input = readInput("Day21")
    parse(input)
    part1()
    part2()
}