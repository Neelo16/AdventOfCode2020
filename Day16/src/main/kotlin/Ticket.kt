class Ticket(val values: List<Int>) {
    fun invalidValues(categories: Collection<Category>): List<Int> {
        val matchedCategories = mutableSetOf<Category>()
        val invalidValues = mutableListOf<Int>()
        for (value in values) {
            categories.find{ it.isValid(value) }?.let {
                matchedCategories.add(it)
            } ?: invalidValues.add(value)
        }
        return invalidValues
    }

    operator fun get(key: Int) = values[key]

    override fun toString(): String {
        return "Ticket(values=$values)"
    }
}