import java.lang.Math.floorMod

data class Equation(val mod: Long, val rem: Long)

fun sieve(equations: List<Equation>): Long {
    val first = equations.first()
    var solution = floorMod(-first.rem, first.mod)
    var step = first.mod
    for (equation in equations.drop(1)) {
        val rem = floorMod(-equation.rem, equation.mod)
        while (solution % equation.mod != rem) {
            solution += step
        }
        step *= equation.mod
    }
    return solution
}
