package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionAritmetica() : Expresion() {
    var valor : ValorNumerico? = null
    var exp1: ExpresionAritmetica? = null
    var operador: Token? = null
    var exp2: ExpresionAritmetica? = null

    constructor(valor : ValorNumerico, operador: Token?, exp2: ExpresionAritmetica?) : this() {
        this.exp1 = null
        this.valor = valor
        if (operador != null) {
            this.operador = operador
        }
        if (exp2 != null) {
            this.exp2 = exp2
        }
    }
    constructor(exp1: ExpresionAritmetica?,  operador: Token?, exp2: ExpresionAritmetica?):this(){
        if (exp1 != null) {
            this.exp1 = exp1
        }
        if (operador != null) {
            this.operador = operador
        }
        if (exp2 != null) {
            this.exp2 = exp2
        }
    }
    constructor(valor : ValorNumerico):this(){
        this.valor = valor
    }
    constructor(exp1: ExpresionAritmetica?):this(){
        if (exp1 != null) {
            this.exp1 = exp1
        }
    }

    override fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Expresion aritmetica")
        if(valor != null){
            root.children.add(valor!!.getArbolVisual())
        }

        if(exp1 != null){
            root.children.add(exp1!!.getArbolVisual())
        }
        if(operador != null){
            root.children.add(TreeItem("Operador "+ operador!!.lexema))
        }
        if(exp2 != null){
            root.children.add(exp2?.getArbolVisual())
        }
        return root
    }

    override fun getJavaCode(): String {

        if(exp1 != null && operador != null && exp2 != null){
            return  "("+ exp1!!.getJavaCode()+")"+ operador!!.getJavaCode()+ exp2!!.getJavaCode()
        }else if (exp1 != null && operador == null && exp2 == null && valor == null){
            return "("+ exp1!!.getJavaCode()+")"
        }else if (valor != null && operador != null && exp1 != null){
            return valor!!.getJavaCode()+ operador!!.getJavaCode()+ exp1!!.getJavaCode()
        }else{
            return valor!!.getJavaCode()
        }


    }
}