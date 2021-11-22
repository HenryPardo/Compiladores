package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Funcion(var tipoRetorno:Token,var identificador:Token,
              var listaParametros: ArrayList<Parametro>, var listaSentencias: ArrayList<Sentencia>) {

    fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Funcion")
        root.children.add(TreeItem("Tipo dato : "+tipoRetorno.lexema))
        root.children.add(TreeItem("Nombre : "+identificador.lexema))

        var rootParametros = TreeItem("Parametros")
        for(i in listaParametros){
            rootParametros.children.add(i.getArbolVisual())
        }
        root.children.add(rootParametros)

        var rootSentencia = TreeItem("Sentencias")
        for(i in listaSentencias){
            rootSentencia.children.add(i.getArbolVisual())
        }
        root.children.add(rootSentencia)

        return root
    }
}