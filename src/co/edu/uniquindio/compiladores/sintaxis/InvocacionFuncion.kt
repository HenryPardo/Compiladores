package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class InvocacionFuncion(var identificador:Token,var listaArgumentos: ListaArgumentos?) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Invocacion funcion")
        if(listaArgumentos != null) {
            root.children.add(listaArgumentos?.getArbolVisual())
        }
        return root
    }
}