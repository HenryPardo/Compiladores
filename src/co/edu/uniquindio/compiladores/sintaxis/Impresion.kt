package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Impresion(var expresion: Expresion?, var identificador: Token?) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Impresion")
        if(expresion != null) {
            root.children.add(expresion?.getArbolVisual())
        }
        root.children.add(TreeItem("Nombre "+identificador?.lexema))
        return root
    }
}