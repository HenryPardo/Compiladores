package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Expresion() {
    open fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Expresion")
        return root
    }

    open fun analizarSemantica(tablaSimbolos: TablaSimbolos,listaErrores: ArrayList<Error>, ambito: Ambito){}

    open fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito, listaErrores: ArrayList<Error>):String{
        return ""
    }
}