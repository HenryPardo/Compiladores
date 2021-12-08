package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class InvocacionFuncion(var identificador:Token,var listaArgumentos: ArrayList<ListaArgumentos>?) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Invocacion funcion")
        root.children.add(TreeItem("Nombre: "+identificador.lexema))
        if(listaArgumentos != null && listaArgumentos?.size != 0) {
            for (i in listaArgumentos!!) {
                root.children.add(i.getArbolVisual())
            }
        }
        return root
    }
}