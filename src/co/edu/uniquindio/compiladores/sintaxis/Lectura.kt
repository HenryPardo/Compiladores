package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Lectura(var variable: Variable) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Lectura")
        root.children.add(variable.getArbolVisual())
        return root
    }
}