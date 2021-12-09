package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionLogica() :Expresion() {
    var valor : ExpresionRelacional? = null
    var exp1: ExpresionLogica? = null
    var operador: Token? = null
    var exp2: ExpresionLogica? = null

    constructor(exp1 : ExpresionLogica?,  operador: Token?, exp2: ExpresionLogica?) : this() {
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
    constructor(exp1: ExpresionRelacional?,  operador: Token?, exp2: ExpresionLogica?):this(){
        if (exp1 != null) {
            this.valor = exp1
        }
        if (operador != null) {
            this.operador = operador
        }
        if (exp2 != null) {
            this.exp2 = exp2
        }
    }
    constructor(valor : ExpresionRelacional?):this(){
        if (valor != null) {
            this.valor = valor
        }
    }
    constructor(exp1: ExpresionLogica?):this(){
        if (exp1 != null) {
            this.exp1 = exp1
        }
    }

    override fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Expresion Logica")
        if(valor != null){
            root.children.add(valor!!.getArbolVisual())
        }

        if(exp1 != null){
            root.children.add(exp1!!.getArbolVisual())
        }
        if(operador != null){
            root.children.add(TreeItem("Operador "+ operador!!.lexema))
        }
        if(exp1 != null){
            root.children.add(exp1!!.getArbolVisual())
        }
        return root
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String, listaErrores: ArrayList<Error>): String {
        return "boolean"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        if (valor != null){
            valor!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
        else if(exp1 != null){
            exp1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
        else if(exp1 != null && exp2 != null){
            exp1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            exp2!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }
}