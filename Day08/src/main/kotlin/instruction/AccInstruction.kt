package instruction

import Computer

class AccInstruction(value: Int) : Instruction(value) {
    override fun doExecute(computer: Computer) {
        computer.accumulator += this.value
    }
}