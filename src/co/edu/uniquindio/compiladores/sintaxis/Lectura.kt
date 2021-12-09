package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Lectura(var variable: Variable) : Sentencia() {
    private var simbolo: Simbolo? = null

    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Lectura")
        root.children.add(variable.getArbolVisual())
        return root
    }

    override fun toString(): String {
        return "Lectura(variable = $variable)"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        simbolo = tablaSimbolos.buscarSimboloValor(variable.lexema, ambito, variable.fila, variable.columna)

        if(simbolo == null){
            listaErrores.add(
                Error(
                    "El campo (${variable.lexema}) no existe dentro del ambito ($ambito)",
                    variable.fila,
                    variable.columna
                )
            )
        }
    }

    override fun getJavaCode(): String {
        return when (simbolo!!.tipo){
            "int" -> {
                variable.getJavaCode()+" = Integer.parInt(JOptionPane.showInputDialog(null, \"Escribir:\") ); "
            }
            "decimal" -> {
                variable.getJavaCode() + " = Double.parseDouble(JOptionPane.showInputDialog(null, \"Escribir:\") ); "
            }
            else -> {
                variable.getJavaCode()+ " = JOptionPane.showInputDialog(null, \"Escribir:\"); "
            }
        }
    }

}