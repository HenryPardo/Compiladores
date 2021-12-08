package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Arreglo(var nombre: Token, var tipoDato : Token, var listaExpresiones:ArrayList<Expresion>)  : Sentencia() {
    override fun getArbolVisual() : TreeItem<String>{
        val raiz = TreeItem(" Arreglo ")
        raiz.children.add(TreeItem("Identificador "+nombre.lexema +" tipoDato "+tipoDato.lexema))
        if(listaExpresiones.size > 0){
            val raizExpresion = TreeItem("Argumentos")
            for(e in listaExpresiones){
                raizExpresion.children.add(e.getArbolVisual())
            }
            raiz.children.add(raizExpresion)
        }
        return raiz
    }

    override fun toString(): String {
        return "Arreglo(nombre=$nombre, tipoDato=$tipoDato, listaExpresiones=$listaExpresiones)"
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        tablaSimbolos.guardarSimboloValor(nombre.lexema, tipoDato.lexema, ambito, nombre.fila, nombre.columna)
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {

        for (e in listaExpresiones){
            if(e.obtenerTipo(tablaSimbolos, ambito) !=tipoDato.lexema){
                listaErrores.add(Error("El tipo de dato de la expresion no coincide con el tipo de dato del arreglo" , nombre.fila,nombre.columna))
            }
        }
    }
}