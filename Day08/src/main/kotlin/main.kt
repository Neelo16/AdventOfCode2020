import instruction.Instruction
import instruction.JmpInstruction
import instruction.NopInstruction

fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText()
}

fun main() {
    val code = getResourceAsText("/input.txt").trim().lines().
            map(Instruction::makeInstruction).toTypedArray()
    val computer = Computer(code)
    computer.run()
    println("Part 1: ${computer.accumulator}")
    for ((i, instruction) in code.withIndex()) {
        if (instruction is NopInstruction || instruction is JmpInstruction) {
            val newCode = code.copyOf()
            newCode.forEach{ it.executed = false }
            newCode[i] = if (instruction is NopInstruction)
                JmpInstruction(instruction.value)
            else
                NopInstruction(instruction.value)
            val newComputer = Computer(newCode)
            newComputer.run()
            if (newComputer.normalTermination) {
                println("Part 2: ${newComputer.accumulator}")
                break
            }
        }
    }
}