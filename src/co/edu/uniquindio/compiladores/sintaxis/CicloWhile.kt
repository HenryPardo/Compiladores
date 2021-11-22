package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class CicloWhile(var expresionLogica: ExpresionLogica, var sentencias: ArrayList<Sentencia>): Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("CicloWhile")
        root.children.add(expresionLogica.getArbolVisual())

        if(sentencias.size > 0) {
            var sentenciaRoot = TreeItem("Sentencias ")
            root.children.add(sentenciaRoot)
            for (i in sentencias) {
                sentenciaRoot.children.add(i.getArbolVisual())
            }
        }
        return root

    }
}