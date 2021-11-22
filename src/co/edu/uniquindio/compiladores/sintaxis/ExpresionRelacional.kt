package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionRelacional(var primeraExpresion: ExpresionAritmetica, var operadorRelacional: Token,
var segundaExpresion: ExpresionAritmetica): Expresion() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Expresion relacional")
        root.children.add(primeraExpresion.getArbolVisual())
        root.children.add(TreeItem("Operador " +operadorRelacional.lexema))
        root.children.add(segundaExpresion.getArbolVisual())
        return root
    }
}