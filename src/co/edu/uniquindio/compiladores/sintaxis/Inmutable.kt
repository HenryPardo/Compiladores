package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Inmutable(var tipoDato: Token, var nombre: Token): Sentencia() {
    override fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Variable solo lectura")
        root.children.add(TreeItem("Tipo dato: " +tipoDato.lexema))
        root.children.add(TreeItem("Nombre: " +nombre.lexema))
        return root
    }

    override fun toString(): String {
        return "Variable(tipoDato=$tipoDato, nombre=$nombre)"
    }


}
