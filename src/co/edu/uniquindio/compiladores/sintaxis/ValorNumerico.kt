package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem
import kotlin.math.sign

class ValorNumerico(var signo: Token?,var numero: Token) {
    fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Valor  ")
        if(signo != null){
            root.children.add(TreeItem("Operador "+ signo!!.lexema))
        }
        if(numero != null){
            root.children.add(TreeItem("Valor  "+numero.lexema))
        }
        return root
    }

    override fun toString(): String {
        return "ValorNumerico(signo=$signo, numero = $numero)"
    }

    fun getJavaCode():String{
        if (signo != null)
        {
            return signo!!.getJavaCode()+" "+numero.getJavaCode()
        }else{
            return numero.getJavaCode()
        }
    }
}