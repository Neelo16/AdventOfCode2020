package instruction

import Computer

class NopInstruction(value: Int) : Instruction(value) {
    override fun doExecute(computer: Computer) {
        // do nothing
    }
}