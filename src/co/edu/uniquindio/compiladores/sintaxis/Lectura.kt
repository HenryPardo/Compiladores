package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Lectura(var variable: Variable) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Lectura")
        root.children.add(variable.getArbolVisual())
        return root
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        var s = tablaSimbolos.buscarSimboloValor(variable.nombre.lexema,ambito)
        if(s==null){
            listaErrores.add(Error("el campo ${variable.nombre.lexema} no existe dentro del ambito $ambito",variable.nombre.fila,variable.nombre.columna))
        }
    }
}