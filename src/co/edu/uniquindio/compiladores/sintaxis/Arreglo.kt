package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
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
}