package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Parametro(var tipoDato: Token, var identificador: Token) {

    fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Parametro")
        root.children.add(TreeItem("Tipo dato : "+tipoDato.lexema))
        root.children.add(TreeItem("Nombre : "+identificador.lexema))
        return root
    }

}