fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

fun findMult(values: IntArray, target: Int, numVals: Int, prevVals: IntArray): Int {
    if (numVals == 1) {
        for (head in values)
            if (prevVals.sum() + head == target)
                return prevVals.reduce { acc, it -> acc * it } * head
        return 0
    } else {
        for ((i, head) in values.withIndex()) {
            val result = findMult(values.sliceArray(IntRange(i, values.size-1)), target, numVals-1, intArrayOf(*prevVals, head))
            if (result != 0) {
                return result
            }
        }
    }
    return 0
}

fun main() {
    val target = 2020
    val input = getResourceAsText("/input.txt")
    val values = input.trim().split("\n").map { it.toInt() }.sorted().toIntArray()
    print("Part 1: ")
    println(findMult(values, target, 2, IntArray(0)))
    print("Part 2: ")
    println(findMult(values, target, 3, IntArray(0)))
}