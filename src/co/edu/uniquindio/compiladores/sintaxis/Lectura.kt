package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Ambito
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

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        var s = tablaSimbolos.buscarSimboloValor(variable.nombre.lexema,ambito)
        if(s==null){
            listaErrores.add(Error("el campo ${variable.nombre.lexema} no existe dentro del ambito $ambito",variable.nombre.fila,variable.nombre.columna))
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