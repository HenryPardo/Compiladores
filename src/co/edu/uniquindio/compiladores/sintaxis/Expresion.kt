package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Expresion() {
    open fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Expresion")
        return root
    }

    open fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String):String{
        return ""
    }
}