package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionRelacional(var primeraExpresion: ExpresionAritmetica, var operadorRelacional: Token,
var segundaExpresion: ExpresionAritmetica): Expresion() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Expresion relacional")
        root.children.add(primeraExpresion.getArbolVisual())
        root.children.add(TreeItem("Operador " +operadorRelacional.lexema))
        root.children.add(segundaExpresion.getArbolVisual())
        return root
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito, listaErrores: ArrayList<Error>): String {
        return "boolean"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if(primeraExpresion != null && segundaExpresion != null){
            primeraExpresion.analizarSemantica(tablaSimbolos,listaErrores, ambito)
            segundaExpresion.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun getJavaCode(): String {

        return if (primeraExpresion != null && segundaExpresion != null) {
            primeraExpresion.getJavaCode()+operadorRelacional.getJavaCode()+segundaExpresion.getJavaCode()
        }else{
            operadorRelacional.getJavaCode()
        }
    }

}