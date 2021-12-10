package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionCadena(var cadena: Token,var expresion: Expresion?) : Expresion() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Expresion cadena")
        if(cadena != null) {
            root.children.add(TreeItem("Cadena "+cadena.lexema))
        }
        if(expresion != null){
            root.children.add(expresion?.getArbolVisual())
        }
        return root
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito, listaErrores: ArrayList<Error>): String {
        return "string"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if(expresion != null){
            expresion!!.analizarSemantica(tablaSimbolos,listaErrores,ambito)
        }
    }
}