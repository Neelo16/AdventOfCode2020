package instruction

import Computer
import java.lang.RuntimeException

abstract class Instruction(val value: Int) {
    var executed: Boolean = false
    protected abstract fun doExecute(computer: Computer)

    fun execute(computer: Computer) {
        executed = true
        doExecute(computer)
    }

    companion object {
        fun makeInstruction(line: String): Instruction {
            val match = Regex("(\\w+) ([+-]\\d+)").matchEntire(line)!!.destructured
            val type = match.component1()
            val value = match.component2().toInt()
            return when (type) {
                "acc" -> AccInstruction(value)
                "jmp" -> JmpInstruction(value)
                "nop" -> NopInstruction(value)
                else -> throw RuntimeException("Unimplemented instruction $type")
            }
        }
    }
}
