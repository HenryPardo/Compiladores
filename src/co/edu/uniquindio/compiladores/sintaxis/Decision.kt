package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Decision(var expresionLogica: ExpresionLogica, var listaSentenciasSI: ArrayList<Sentencia>,
               var listaSentenciasNO: ArrayList<Sentencia>) : Sentencia(){
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Decision")
        root.children.add(expresionLogica.getArbolVisual())

        if(listaSentenciasSI.size > 0) {
            var sentenciaRoot = TreeItem("Sentencias SI")
            root.children.add(sentenciaRoot)
            for (i in listaSentenciasSI) {
                sentenciaRoot.children.add(i.getArbolVisual())
            }
        }
        if(listaSentenciasNO.size > 0) {

            var sentenciaNORoot = TreeItem("Sentencias NO")
            root.children.add(sentenciaNORoot)
            for(i in listaSentenciasNO){
                sentenciaNORoot.children.add(i.getArbolVisual())
            }
        }


        return root
    }

}