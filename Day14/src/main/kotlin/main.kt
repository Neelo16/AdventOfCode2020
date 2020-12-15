import java.lang.StringBuilder

fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

class Memory {
    val memory = mutableMapOf<Long, Long>().withDefault { 0 }
    var mask = "X".repeat(36)
    companion object {
        private val allOnes: Long

         init {
             var temp = 0L
             for (i in 0 until 36)
                 temp = (temp shl 1) + 1
             allOnes = temp
         }
    }

    fun processLine(line: String, floating: Boolean = false) {
        val maskRegex = Regex("mask = ([01X]{36})")
        val memoryWriteRegex = Regex("mem\\[(\\d+)] = (\\d+)")
        if (maskRegex.matches(line)) {
            mask = maskRegex.matchEntire(line)!!.destructured.component1()
        } else if (memoryWriteRegex.matches(line)) {
            val matches = memoryWriteRegex.matchEntire(line)!!.destructured
            val address = matches.component1().toLong()
            val value = matches.component2().toLong()
            if (floating)
                applyAddressMask(address).forEach { memory[it] = value }
            else
                memory[address] = applyMask(value)
        }
    }

    private fun applyMask(value: Long) = applyMask(
        value.toString(2).padStart(36, '0')
    ).toLong(2)

    private fun applyMask(value: String): String {
        val maskedValue = StringBuilder(value)
        mask.withIndex().filter{ (_, bit) -> bit in "01"}.forEach { (index, bit) ->
            maskedValue.setCharAt(index, bit)
        }
        return maskedValue.toString()
    }

    private fun applyAddressMask(address: Long) = applyAddressMask(
        address.toString(2).padStart(36, '0')
    )

    private fun applyAddressMask(address: String): List<Long> {
        val maskedAddress = StringBuilder(address)
        mask.withIndex().filter { it.value == '1' }.forEach { (index, _) ->
            maskedAddress.setCharAt(index, '1')
        }
        var values = listOf(maskedAddress.toString())
        mask.withIndex().filter { (_, bit) -> bit == 'X' }.forEach { (index, _) ->
            val newValues = mutableListOf<String>()
            values.forEach { maskedValue ->
                "01".forEach { char ->
                    newValues.add(StringBuilder(maskedValue).also { it.setCharAt(index, char) }.toString())
                }
            }
            values = newValues
        }
        return values.map{ it.toLong(2) }.toList()
    }

    fun sumValues() = memory.values.sum()
}

fun main() {
    val input = getResourceAsText("/input.txt").trim().lines()
    println()
    val memory = Memory()
    input.forEach(memory::processLine)
    println("Part 1: ${Memory().also{ input.forEach(it::processLine) }.sumValues()}")
    println("Part 2: ${Memory().also { mem -> input.forEach{ mem.processLine(it, true) } }.sumValues()}")
}
