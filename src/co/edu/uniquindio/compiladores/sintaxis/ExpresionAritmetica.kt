package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class ExpresionAritmetica() : Expresion() {
    constructor(valor : ValorNumerico,  operador: Token?, exp2: ExpresionAritmetica?) : this() {

    }
    constructor(exp1: ExpresionAritmetica?,  operador: Token?, exp2: ExpresionAritmetica?):this(){

    }
    constructor(valor : ValorNumerico):this(){

    }
    constructor(exp1: ExpresionAritmetica?):this(){

    }
}