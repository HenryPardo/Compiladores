package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class AsignacionInmutable(var inmutable: Inmutable, var expresion: Expresion) :Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Asignacion solo lectura ")
        root.children.add(inmutable.getArbolVisual())
        root.children.add(expresion.getArbolVisual())
        return root
    }
}