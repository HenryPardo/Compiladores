package co.edu.uniquindio.compiladores.lexico

class Error(var Error : String, var Fila: Int, var Columna: Int) {

    override fun toString(): String {
        return "Error(Error='$Error', Fila=$Fila, Columna=$Columna)"
    }
}