package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class CicloFor(var asignacion: Asignacion,var expresionLogica: ExpresionLogica,
               var incremento: Incremento?, var decremento: Decremento?, var sentencias: ArrayList<Sentencia>) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("CicloWhile")
        root.children.add(asignacion.getArbolVisual())
        root.children.add(expresionLogica.getArbolVisual())
        root.children.add(incremento?.getArbolVisual())
        root.children.add(decremento?.getArbolVisual())

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