package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ListaArgumentos(var identificador: Token, var argumentos: ArrayList<ListaArgumentos>) {
    fun getArbolVisual(): TreeItem<String>{
        var root = TreeItem("Lista Argumentos")
        for(i in argumentos){
            root.children.add(i.getArbolVisual())
        }
        return root
    }
}