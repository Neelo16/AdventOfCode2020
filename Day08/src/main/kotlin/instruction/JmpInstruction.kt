package instruction

import Computer

class JmpInstruction(value: Int) : Instruction(value) {
    override fun doExecute(computer: Computer) {
        computer.ip += this.value - 1
    }
}