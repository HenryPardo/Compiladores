package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Decision(var expresionLogica: ExpresionLogica, var listaSentenciasSI: ArrayList<Sentencia>,
               var listaSentenciasNO: ArrayList<Sentencia>) : Sentencia(){
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Decision")
        root.children.add(expresionLogica.getArbolVisual())

        if(listaSentenciasSI.size > 0) {
            var sentenciaRoot = TreeItem("Sentencias SI")
            root.children.add(sentenciaRoot)
            for (i in listaSentenciasSI) {
                sentenciaRoot.children.add(i.getArbolVisual())
            }
        }
        if(listaSentenciasNO.size > 0) {

            var sentenciaNORoot = TreeItem("Sentencias NO")
            root.children.add(sentenciaNORoot)
            for(i in listaSentenciasNO){
                sentenciaNORoot.children.add(i.getArbolVisual())
            }
        }
        return root
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        for(s in listaSentenciasSI){
            s.llenarTablaSimbolos(tablaSimbolos,listaErrores,ambito)
        }
        if(listaSentenciasNO!=null){
            for(n in listaSentenciasNO){
                n.llenarTablaSimbolos(tablaSimbolos,listaErrores, ambito)
            }
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        expresionLogica.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        for( s in listaSentenciasSI){
            s.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
        for( n in listaSentenciasNO){
            n.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }
}