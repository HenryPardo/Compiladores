package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class UnidadDeCompilacion(var listaFunciones:ArrayList<Funcion>,var listaVariables:ArrayList<Variable>) {

    fun getArbolVisual():TreeItem<String>
    {
        var root = TreeItem("Unidad de compilacion")
        for(f in listaFunciones){
            root.children.add(f.getArbolVisual())
        }
        for(f in listaVariables){
            root.children.add(f.getArbolVisual())
        }
        return root
    }

    override fun toString(): String {
        return "UnidadDeCompilacion(listaFunciones=$listaFunciones, listaVariables=$listaVariables)"
    }
}