package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Variable(var tipoDato: Token, var nombre: Token, var expresion: Expresion): Sentencia() {
    override fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Variable")
        root.children.add(TreeItem("Tipo dato: " +tipoDato.lexema))
        root.children.add(TreeItem("Nombre: " +nombre.lexema))
        root.children.add(TreeItem("expresion: "+expresion.lexema))
        return root
    }

    override fun toString(): String {
        return "Variable(tipoDato=$tipoDato, nombre=$nombre)"
    }

    override fun getJavaCode():String{

        if (nombre != null)
        {
            return nombre.getJavaCode()+"="+expresion!!.getJavaCode()
        }else{
            return nombre.getJavaCode()

        }

    }
}
