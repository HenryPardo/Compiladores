package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class ExpresionLogica() :Expresion() {
    constructor(valor : ExpresionLogica?,  operador: Token?, exp2: ExpresionLogica?) : this() {

    }
    constructor(exp1: ExpresionRelacional?,  operador: Token?, exp2: ExpresionLogica?):this(){

    }
    constructor(valor : ExpresionRelacional?):this(){

    }
    constructor(exp1: ExpresionLogica?):this(){

    }
}