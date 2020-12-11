fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

abstract class PasswordPolicy(line: String) {
    protected val firstNumber: Int
    protected val secondNumber: Int
    protected val character: String
    protected val password: String

    init {
        val regex = Regex("(\\d+)-(\\d+) (\\w): (\\w+)")
        val matches =
            regex.matchEntire(line.trim())?.destructured?.toList() ?: throw IllegalArgumentException("Wrong line format")
        firstNumber = matches[0].toInt()
        secondNumber = matches[1].toInt()
        character = matches[2]
        password = matches[3]
    }

    abstract fun isvalid(): Boolean
}

class OccurrencesPasswordPolicy(line: String): PasswordPolicy(line) {
    override fun isvalid(): Boolean {
        val count = password.count { character.contains(it) }
        return count in firstNumber..secondNumber
    }
}

class PositionPasswordPolicy(line: String): PasswordPolicy(line) {
    override fun isvalid(): Boolean {
        return character.contains(password[firstNumber - 1]) && !character.contains(password[secondNumber - 1]) ||
               character.contains(password[secondNumber - 1]) && !character.contains(password[firstNumber - 1])
    }
}

fun main() {
    val input = getResourceAsText("/input.txt").trim()
    println("Part 1: ${input.lines().map { OccurrencesPasswordPolicy(it) }.count{ it.isvalid() }}")
    println("Part 2: ${input.lines().map { PositionPasswordPolicy(it) }.count{ it.isvalid() }}")
}