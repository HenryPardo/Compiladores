package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionAritmetica() : Expresion() {
    var valor : ValorNumerico? = null
    var exp1: ExpresionAritmetica? = null
    var operador: Token? = null
    var exp2: ExpresionAritmetica? = null

    constructor(exp1: ExpresionAritmetica?,  operador: Token?, exp2: ExpresionAritmetica?):this(){ //constructor 1
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

    constructor(exp1: ExpresionAritmetica?):this(){ //constructor 2
        if (exp1 != null) {
            this.exp1 = exp1
        }
    }

    constructor(valor : ValorNumerico, operador: Token?, exp2: ExpresionAritmetica?) : this() { //constructor 3
        this.exp1 = null
        this.valor = valor
        if (operador != null) {
            this.operador = operador
        }
        if (exp2 != null) {
            this.exp2 = exp2
        }
    }

    constructor(valor : ValorNumerico):this(){ //constructor 4
        this.valor = valor
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

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String): String {
        if(exp1!=null && exp2 != null){
            var tipo1 = exp1!!.obtenerTipo(tablaSimbolos,ambito)
            var tipo2 = exp2!!.obtenerTipo(tablaSimbolos,ambito)

            if(tipo1 == "DECIMAL" || tipo2 == "DECIMAL"){
                return "DECIMAL"
            }
            else {
                return "ENTERO"
            }
        }

        else if(exp1 != null){
            return exp1!!.obtenerTipo(tablaSimbolos,ambito)
        }

        else if(valor != null && exp2 != null){
            var tipo1 = obtenerTipoValorNumerico(tablaSimbolos,ambito,valor!!)
            var tipo2 = exp2!!.obtenerTipo(tablaSimbolos,ambito)

            if(tipo1 == "DECIMAL" || tipo2 == "DECIMAL"){
                return "DECIMAL"
            }
            else {
                return "ENTERO"
            }
        }

        else if(valor != null){
            return obtenerTipoValorNumerico(tablaSimbolos,ambito, valor!!)
            /*if(valor!!.numero.categoria == Categoria.ENTERO){
                 return "ENTERO"
            }
            else if(valor!!.numero.categoria == Categoria.DECIMAL){
                return "DECIMAL"
            }
            else{
                var simbolo = tablaSimbolos.buscarSimboloValor(valor!!.numero.lexema, ambito)
                if(simbolo != null){
                    return simbolo.tipo
                }

            }*/
        }
        return ""
    }

    fun obtenerTipoValorNumerico(tablaSimbolos: TablaSimbolos, ambito: String, valorNum: ValorNumerico):String{
        if(valorNum.numero.categoria == Categoria.ENTERO){
            return "ENTERO"
        }
        else if(valorNum.numero.categoria == Categoria.DECIMAL){
            return "DECIMAL"
        }
        else{
            var simbolo = tablaSimbolos.buscarSimboloValor(valorNum!!.numero.lexema, ambito)
            if(simbolo != null){
                return simbolo.tipo
            }
        }
        return ""
    }
}