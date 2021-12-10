package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
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

    fun obtenerTiposArgumentos(tablaSimbolos: TablaSimbolos,listaErrores: ArrayList<Error>,ambito:Ambito):ArrayList<String>{
        var listaArgs = ArrayList<String>()
        for (a in listaArgumentos!!){
           var s = tablaSimbolos.buscarSimboloValor(a.identificador.lexema, ambito)
            if(s != null){
                listaArgs.add(s.tipo)
            }
        }
        return listaArgs
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        var listaTiposArgs = obtenerTiposArgumentos(tablaSimbolos,listaErrores,ambito)
        var s = tablaSimbolos.buscarSimboloFuncion(identificador.lexema,listaTiposArgs)
        if(s==null){
            listaErrores.add(Error("la funcion ${identificador.lexema}(${listaTiposArgs}) no existe", identificador.fila,identificador.columna))
        }
    }
}