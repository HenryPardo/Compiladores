package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ListaArgumentos(var identificador: Token) {
    fun getArbolVisual(): TreeItem<String>{
        var root = TreeItem("Argumento "+identificador.lexema)

        return root
    }
}