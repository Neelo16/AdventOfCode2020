fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

fun isOperator(token: String) = token in listOf("+", "*")
fun isNumber(token: String) = token.all(Char::isDigit)
fun apply(op: String, a: Long, b: Long): Long {
    return when (op) {
        "+" -> a + b
        "*" -> a * b
        else ->  throw RuntimeException("Invalid operator $op")
    }
}

@Suppress("UNUSED_PARAMETER")
fun noPrecedence(token: String) = 1

fun addPrecedence(token: String) = when (token) {
    "+" -> 2
    else -> 1
}

fun toPostfix(line: String, precedence: (String) -> Int = ::noPrecedence): List<String> {
    val tokens = tokenize(line)
    val outputStack = mutableListOf<String>()
    val opStack = mutableListOf<String>()
    for (token in tokens) {
        when {
            isNumber(token) -> outputStack.add(token)
            isOperator(token) -> {
                while (opStack.isNotEmpty() &&
                        precedence(opStack.last()) >= precedence(token) &&
                        opStack.last() != "(") {
                    outputStack.add(opStack.removeLast())
                }
                opStack.add(token)
            }
            token == "(" -> opStack.add(token)
            token == ")" -> {
                while (opStack.last() != "(") {
                    outputStack.add(opStack.removeLast())
                }
                opStack.removeLast()
            }
        }
    }
    while (opStack.isNotEmpty()) {
        outputStack.add(opStack.removeLast())
    }
    return outputStack
}

fun calculate(line: String, precedence: (String) -> Int = ::noPrecedence): Long {
    val postfixNotation = toPostfix(line, precedence)
    val outputStack = mutableListOf<Long>()
    for (token in postfixNotation) {
        when {
            isNumber(token) -> outputStack.add(token.toLong())
            else -> {
                val b = outputStack.removeLast()
                val a = outputStack.removeLast()
                outputStack.add(apply(token, a, b))
            }
        }
    }
    return outputStack.last()
}

fun tokenize(line: String): List<String> {
    val regex = Regex("\\d+|[+*()]")
    val match = regex.find(line) ?: return listOf()
    val startIndex = match.range.first
    val endIndex = match.range.last + 1
    return listOf(line.substring(startIndex, endIndex)) + tokenize(line.substring(endIndex))
}

fun main() {
    val input = getResourceAsText("/input.txt").trim().lines()
    println("Part 1: ${input.map(::calculate).sum()}")
    println("Part 2: ${input.map{ calculate(it, ::addPrecedence) }.sum()}")
}
