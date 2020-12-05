fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

private fun isValidYear(value: String, range: IntRange) =
    value.matches(Regex("^\\d{4}$")) && value.toInt() in range

fun byr(value: String): Boolean = isValidYear(value, 1920..2002)

fun iyr(value: String): Boolean = isValidYear(value, 2010..2020)

fun eyr(value: String): Boolean = isValidYear(value, 2020..2030)

fun hgt(value: String): Boolean {
    val regex = Regex("^(\\d+)(in|cm)$")
    val match = regex.matchEntire(value)?.destructured ?: return false
    val height = match.component1().toInt()
    return when (match.component2()) {
        "cm" -> height in 150..193
        "in" -> height in 59..76
        else -> false
    }
}

fun hcl(value: String): Boolean = value.matches(Regex("^#[0-9a-f]{6}$"))

fun ecl(value: String): Boolean {
    return value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
}

fun pid(value: String): Boolean {
    return value.matches(Regex("^\\d{9}$"))
}

data class Field(val id: String, val value: String)

fun extractFields(passport: String): Sequence<Field> {
    val regex = Regex("((\\w+):(\\S+)\\s*)")
    return regex.findAll(passport).map {
         Field(it.groups[2]!!.value, it.groups[3]!!.value)
     }
}

fun isMostlyValid(passport: String): Boolean {
    val requiredFields = listOf(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid",
    )
    return requiredFields.all(extractFields(passport).map { it.id }::contains)
}

fun isValid(passport: String): Boolean {
    val requiredFields = mapOf(
        "byr" to ::byr,
        "iyr" to ::iyr,
        "eyr" to ::eyr,
        "hgt" to ::hgt,
        "hcl" to ::hcl,
        "ecl" to ::ecl,
        "pid" to ::pid,
        "cid" to { true }
    )
    if (!isMostlyValid(passport))
        return false
    return extractFields(passport).all { requiredFields[it.id]!!(it.value) }
}

fun main() {
    val input = getResourceAsText("/input.txt").lines()
    val passports = mutableListOf<String>()
    val batchIterator = input.iterator()
    do {
        val passport = mutableListOf<String>()
        var line = batchIterator.next()
        while (line != "") {
            passport.add(line)
            line = batchIterator.next()
        }
        passports.add(passport.joinToString(" "))
    } while (batchIterator.hasNext())
    println("Part 1: ${passports.filter(::isMostlyValid).count()}")
    println("Part 2: ${passports.filter(::isValid).count()}")
}