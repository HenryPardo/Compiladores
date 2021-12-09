package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem
import kotlin.math.exp

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

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        /*expresionLogica.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        for( s in sentencias){
            s.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }*/

        if(expresionLogica != null){
            var t = expresionLogica.valor!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
            if( t =="boolean"){
                expresionLogica.analizarSemantica(tablaSimbolos, listaErrores, ambito)
                for( s in sentencias){
                    s.analizarSemantica(tablaSimbolos, listaErrores, ambito)
                }
            }
            else{
                listaErrores.add(Error("el tipo de la expresion ($t) no es expresion logica",expresionLogica.operador!!.fila,expresionLogica.operador!!.columna))
            }
        }
    }
}