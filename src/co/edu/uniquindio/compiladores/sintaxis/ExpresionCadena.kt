package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
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

}