package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Sentencia {

    open fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Sentencia")
        return root
    }

    open fun llenarTablaSimbolos(tablaSimbolos : TablaSimbolos, listaErrores: ArrayList<Error>, ambito : String){

    }

    open fun analizarSemantica(tablaSimbolos : TablaSimbolos,listaErrores: ArrayList<Error>, ambito : String){

    }
}