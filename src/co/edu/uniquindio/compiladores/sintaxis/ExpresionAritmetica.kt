package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
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

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito, listaErrores: ArrayList<Error>): String {
        if(exp1!=null && exp2 != null && valor == null){
            var tipo1 = exp1!!.obtenerTipo(tablaSimbolos,ambito, listaErrores)
            var tipo2 = exp2!!.obtenerTipo(tablaSimbolos,ambito, listaErrores)

            if(tipo1 == "double" || tipo2 == "double"){
                return "double"
            }
            else {
                return "int"
            }
        }

        else if(exp1 != null && exp2 == null && valor == null){
            println(exp1!!.obtenerTipo(tablaSimbolos,ambito, listaErrores))
            return exp1!!.obtenerTipo(tablaSimbolos,ambito, listaErrores)

        }

        else if(valor != null && exp2 != null && exp1 == null){
            var tipo1 = obtenerTipoCampo(tablaSimbolos,ambito,valor!!,listaErrores)
            /*var tipo1 = ""
            if(valor!!.numero.categoria == Categoria.ENTERO){
                tipo1 = "int"
            }
            else if(valor!!.numero.categoria == Categoria.DECIMAL){
                tipo1 = "double"
            }
            else{
                var simbolo = tablaSimbolos.buscarSimboloValor(valor!!.numero.lexema, ambito)
                if(simbolo != null){
                    tipo1 = simbolo.tipo
                }
                else{
                    listaErrores.add(Error("El campo ${valor!!.numero.lexema} no existe dentro del ambito $ambito", valor!!.numero.fila,valor!!.numero.columna))
                }
            }*/
            var tipo2 = exp2!!.obtenerTipo(tablaSimbolos,ambito, listaErrores)

            if(tipo1 == "double" || tipo2 == "double"){
                return "double"
            }
            else {
                return "int"
            }
        }

        else if(valor != null && exp2 == null && exp1 == null){
            return obtenerTipoCampo(tablaSimbolos,ambito, valor!!,listaErrores)
            /*if(valor!!.numero.categoria == Categoria.ENTERO){
                 return "int"
            }
            else if(valor!!.numero.categoria == Categoria.DECIMAL){
                return "double"
            }
            else{
                var simbolo = tablaSimbolos.buscarSimboloValor(valor!!.numero.lexema, ambito)
                if(simbolo != null){
                    return simbolo.tipo
                }
                else{
                 listaErrores.add(Error("El campo ${valor!!.numero.lexema} no existe dentro del ambito $ambito", valor!!.numero.fila,valor!!.numero.columna))
                }
            }*/
        }
        return ""
    }

    fun obtenerTipoCampo(tablaSimbolos: TablaSimbolos, ambito: Ambito, valorNum: ValorNumerico,listaErrores: ArrayList<Error>):String{
        if(valorNum.numero.categoria == Categoria.ENTERO){
            return "int"
        }
        else if(valorNum.numero.categoria == Categoria.DECIMAL){
            return "double"
        }
        else{
            var simbolo = tablaSimbolos.buscarSimboloValor(valorNum!!.numero.lexema, ambito)
            if(simbolo != null){
                return simbolo.tipo
            }
            else{
                 listaErrores.add(Error("El campo ${valor!!.numero.lexema} no existe dentro del ambito $ambito", valor!!.numero.fila,valor!!.numero.columna))
                }
        }
        return ""
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (valor != null){
            if(valor!!.numero.categoria == Categoria.IDENTIFICADOR){
                var simbolo = tablaSimbolos.buscarSimboloValor(valor!!.numero.lexema,ambito)
                if (simbolo != null){
                    if(simbolo.tipo != "double" && simbolo.tipo !="int"){
                        listaErrores.add(Error("El campo (${valor!!.numero.lexema} es tipo ${simbolo.tipo} y se espera de tipo numerico (int o double)", valor!!.numero.fila,valor!!.numero.columna))
                    }
                }
                else{
                    listaErrores.add(Error("El campo (${valor!!.numero.lexema}) no existe dentro del ambito ($ambito)",valor!!.numero.fila,valor!!.numero.columna))
                }
            }
        }
        if(exp1 != null){
            exp1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
        if(exp2 != null){
            exp2!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
       }
    }
}

