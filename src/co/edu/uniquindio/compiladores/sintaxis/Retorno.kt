package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Retorno(var expresion: Expresion?) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Retorno ")
        if(expresion != null){
            root.children.add(expresion?.getArbolVisual())
        }
        return root
    }

    override fun getJavaCode(): String {
        return "return " + expresion!!.getJavaCode()+";"
    }
}