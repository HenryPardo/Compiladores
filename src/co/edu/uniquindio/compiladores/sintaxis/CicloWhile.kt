package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class CicloWhile(var expresionLogica: ExpresionLogica, var sentencias: ArrayList<Sentencia>): Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("CicloWhile")
        root.children.add(expresionLogica.getArbolVisual())

        if(sentencias.size > 0) {
            var sentenciaRoot = TreeItem("Sentencias ")
            root.children.add(sentenciaRoot)
            for (i in sentencias) {
                sentenciaRoot.children.add(i.getArbolVisual())
            }
        }
        return root
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        for ( s in sentencias){
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }
    }
}