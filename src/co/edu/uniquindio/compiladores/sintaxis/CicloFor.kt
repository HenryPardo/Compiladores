package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class CicloFor(var asignacion: Asignacion,var expresionLogica: ExpresionLogica,
               var incremento: Incremento?, var decremento: Decremento?, var sentencias: ArrayList<Sentencia>) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Ciclo for")
        root.children.add(asignacion.getArbolVisual())
        root.children.add(expresionLogica.getArbolVisual())
        if(incremento != null){
            root.children.add(incremento?.getArbolVisual())
        }
        if(decremento != null){
            root.children.add(decremento?.getArbolVisual())
        }
        if(sentencias.size > 0) {
            print(""+sentencias.size+"acá \n")

            var sentenciaRoot = TreeItem("Sentencias ")
            for (i in sentencias) {
                sentenciaRoot.children.add(i.getArbolVisual())
            }
            root.children.add(sentenciaRoot)
        }
        return root

    }
}