package co.edu.uniquindio.compiladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico

fun main(){
    val lexico = AnalizadorLexico("casa #25 #.2 #0.3 <comentario linea!> <comentario \n ¡bloque !> _cadena123_ -%- . : , ()[]{}")
    lexico.analizar()
    println(lexico.listaTokens)
}

