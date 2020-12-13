data class Equation(val mod: Long, val rem: Long)

fun sieve(equations: List<Equation>): Long {
    val first = equations.first()
    var solution = first.rem % first.mod
    var step = first.mod
    for (equation in equations.drop(1)) {
        while ((solution + equation.rem) % equation.mod != 0L) {
            solution += step
        }
        step *= equation.mod
    }
    return solution
}
