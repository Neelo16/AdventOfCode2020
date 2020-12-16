fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

fun findCategories(fields: List<Pair<String, List<Int>>>, completeCategories: Map<String, Int> = mapOf()): Map<String, Int>? {
    if (fields.isEmpty()) return completeCategories
    val (currentName, currentFields) = fields.first()
    val remainingFields = fields.drop(1)
    for (field in currentFields) {
        findCategories(
            remainingFields.map { pair ->
                Pair(pair.first, pair.second.filter { it != field })
            },
            completeCategories + mapOf(currentName to field)
        )?.let {
            return it
        }
    }
    return null
}

fun main() {
    val input = getResourceAsText("/input.txt").trim().lines()
    val categories = input.takeWhile(String::isNotBlank).map(::Category)
    val myTicket = Ticket(input.dropWhile { it != "your ticket:" }.drop(1).first().split(",").map(String::toInt))
    val nearbyTickets = input.dropWhile { it != "nearby tickets:" }.drop(1).map {
        Ticket(it.split(",").map(String::toInt))
    }
    val invalidValues = nearbyTickets.map{ it.invalidValues(categories) }.reduce(List<Int>::plus)
    println("Part 1: ${invalidValues.sum()}")
    val validTickets = listOf(myTicket) + nearbyTickets.filter { it.invalidValues(categories).isEmpty() }
    val validFields = mutableMapOf<String, List<Int>>()
    for (category in categories) {
        val fields = 0.until(myTicket.values.size).filter {
            validTickets.map { ticket -> ticket[it] }.all { category.isValid(it) }
        }
        validFields[category.name] = fields
    }
    val mappings = findCategories(validFields.entries.map{ Pair(it.key, it.value) }.toList().sortedBy{ it.second.size })!!
    println("Part 2: ${mappings.filter {
        it.key.startsWith("departure")
    }.values.map{ myTicket[it] }.fold(1L, Long::times)}")
}
