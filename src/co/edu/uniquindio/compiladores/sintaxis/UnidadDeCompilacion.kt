package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
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

    fun llenarTablaSimbolos(listaErrores: ArrayList<Error>,tablaSimbolos : TablaSimbolos){
        for (f in listaFunciones){
            f.llenarTablaSimbolos(tablaSimbolos,listaErrores,"UnidadCompilacion")
        }
    }

    fun analizarSemantica(listaErrores: ArrayList<Error>,tablaSimbolos : TablaSimbolos){
        for (f in listaFunciones){
            f.analizarSemantica(tablaSimbolos, listaErrores)
        }
    }

    fun getJavaCode():String{
        var codigo = "Import javax.swing.JOptionPane; public class Principal{"

        for ( f in listaFunciones){
            codigo += f.getJavaCode()
        }
        codigo += "}"
        return codigo
    }



}