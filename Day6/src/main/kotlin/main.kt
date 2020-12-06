fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

fun main() {
    val input = getResourceAsText("/input.txt").lines()
    var acc = mutableListOf<String>()
    val groups = mutableListOf<List<String>>()
    for (line in input) {
        if (line.isEmpty()) {
            groups.add(acc)
            acc = mutableListOf()
        } else {
            acc.add(line)
        }
    }
    val anyCount = groups.map { it.joinToString("").toSet().size }.sum()
    println("Part 1: $anyCount")
    val everyCount = groups.map { group ->
        val answers = mutableMapOf<Char, Int>().withDefault { 0 }
        group.forEach { it.forEach { answer -> answers[answer] = answers.getValue(answer) + 1 } }
        answers.values.count { it == group.size }
    }.sum()
    println("Part 2: $everyCount")
}