package co.edu.uniquindio.compiladores.semantica

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.sintaxis.UnidadDeCompilacion

class AnalizadorSemantico (var unidadDeCompilacion: UnidadDeCompilacion){

    val listaErrores : ArrayList<Error> = ArrayList()
    var tablaSimbolos : TablaSimbolos = TablaSimbolos(listaErrores)

    fun llenarTablaSimbolos(){
        unidadDeCompilacion.llenarTablaSimbolos(listaErrores,tablaSimbolos)
    }

    fun analizarSemantica(){
        unidadDeCompilacion.analizarSemantica(listaErrores,tablaSimbolos)
    }

}