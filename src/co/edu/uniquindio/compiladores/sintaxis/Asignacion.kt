package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Asignacion(var variable: Variable, var expresion: Expresion) :Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("CicloWhile")
        root.children.add(variable.getArbolVisual())
        root.children.add(expresion.getArbolVisual())
        return root
    }
}