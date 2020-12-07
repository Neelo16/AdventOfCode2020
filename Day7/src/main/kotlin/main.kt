fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

data class Bag(val color: String) {
    val edges: MutableList<Edge> = mutableListOf()
}

data class Edge(val from: Bag, val to: Bag, val amount: Int) {
    init {
        from.edges.add(this)
        to.edges.add(this)
    }
}

fun parseLine(bags: MutableMap<String, Bag>, line: String) {
    val regex = Regex("(.*) bags contain (\\d+) ([^,.]+) bags?[,.]")
    val emptyBagRegex = Regex("(.*) bags contain no other bags.")
    val matches = regex.find(line) ?: emptyBagRegex.find(line)!!
    val remainingBagsRegex = Regex(", (\\d+) ([^,.]+) bags?\\.?")
    val outerBagColor = matches.destructured.component1()
    val remainingBags = mutableListOf<String>()
    remainingBags.addAll(matches.destructured.toList().drop(1))
    remainingBagsRegex.findAll(line, matches.range.last).forEach { remainingBags.addAll(it.destructured.toList()) }

    val outerBag = bags.computeIfAbsent(outerBagColor) { Bag(it) }
    val iterator = remainingBags.iterator()
    while (iterator.hasNext()) {
        val amount = iterator.next().toInt()
        val color = iterator.next()
        val innerBag = bags.computeIfAbsent(color) { Bag(it) }
        Edge(from=outerBag, to=innerBag, amount=amount)
    }
}

fun contains(bag: Bag, color: String): Boolean = bag.edges.any { it.from == bag && (it.to.color == color || contains(it.to, color)) }

fun contained(bag: Bag): Int {
    var count = 0
    for (edge in bag.edges.filter { it.from == bag }) {
        count += edge.amount + edge.amount * contained(edge.to)
    }
    return count
}

fun main(args: Array<String>) {
    val input = getResourceAsText("/input.txt").trim().lines()
    val bags = mutableMapOf<String, Bag>()
    input.forEach { parseLine(bags, it) }
    println("Part 1: ${bags.values.count{ contains(it, "shiny gold") }}")
    println("Part 2: ${contained(bags["shiny gold"]!!)}")
}