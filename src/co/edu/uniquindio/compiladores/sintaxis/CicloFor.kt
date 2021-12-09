package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class CicloFor(var asignacion: Asignacion,var expresionLogica: ExpresionLogica,
               var incremento: Incremento?, var decremento: Decremento?, var sentencias: ArrayList<Sentencia>) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("CicloFor") // decia ciclo WHILE
        root.children.add(asignacion.getArbolVisual())
        root.children.add(expresionLogica.getArbolVisual())
        root.children.add(incremento?.getArbolVisual())
        root.children.add(decremento?.getArbolVisual())

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

    override fun getJavaCode(): String {
        var codigo = "for ("+expresionLogica.getJavaCode()+"){"

        for (s in sentencias){
            codigo += s.getJavaCode()
        }
        codigo += "}"
        return  codigo
    }
}