package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Retorno(var expresion: Expresion?) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Retorno ")
        if(expresion != null){
            root.children.add(expresion?.getArbolVisual())
        }
        return root
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        if(expresion!=null){
            var tipoExp = expresion!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
            var s = tablaSimbolos.buscarSimboloFuncion(ambito,ArrayList())

            if(s != null){
                if(tipoExp == s.tipo) {
                    expresion!!.analizarSemantica(tablaSimbolos,listaErrores, ambito)
                }
                else{
                    listaErrores.add(Error("El tipo de dato del retorno ($tipoExp) no coincide con el tipo de dato de la funcion ${s.nombre}(${s.tipo})",s.fila,s.columna))
                }
            }
        }
    }
}