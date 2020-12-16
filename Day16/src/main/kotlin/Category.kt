class Category(line: String) {
    val name: String
    val ranges: List<IntRange>
    init {
        val categoryRegex = Regex("(.+): (\\d+-\\d+) or (\\d+-\\d+)")
        val matches = categoryRegex.matchEntire(line)!!.destructured.toList()
        name = matches.first()
        ranges = matches.drop(1).map {
            val range = it.split("-").map(String::toInt).toIntArray()
            IntRange(range.first(), range.last())
        }
    }

    override fun toString(): String {
        return "Category(name='$name', ranges=$ranges)"
    }

    fun isValid(value: Int) = ranges.any { value in it }
}