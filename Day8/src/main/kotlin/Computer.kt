import instruction.Instruction

class Computer(val code: Array<Instruction>) {
    var ip: Int = 0
    var accumulator: Int = 0
    var normalTermination: Boolean = false

    fun run() {
        do {
            val instruction = code[ip]
            if (instruction.executed)
                break
            instruction.execute(this)
        } while (++ip < code.size)
        if (ip == code.size)
            normalTermination = true
    }
}