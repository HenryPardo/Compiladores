package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Incremento(var identificador: Token) : Sentencia(){
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Incremento")
        root.children.add(TreeItem("Nombre : "+identificador.lexema))
        return root
    }

    override fun getJavaCode(): String {
        return if (identificador != null) {
            "++"
        }else{
            ""
        }
    }
}