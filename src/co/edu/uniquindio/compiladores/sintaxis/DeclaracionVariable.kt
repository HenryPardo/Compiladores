package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class DeclaracionVariable(var tipoDato: Token, var listaVariables: ArrayList<Variable>):Expresion() {

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem("Declaracion")
        for (variable in listaVariables){
            raiz.children.add(variable.getArbolVisual(tipoDato))
        }
        return raiz
    }

    /**
    override fun toString(): String {
        return "DeclaracionVarible(tipoDato = $tipoDato, listaVariables = $listaVariables)"
    }

    override fun llenarTablaSimbolos( tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        for (v in listaVariables){
            tablaSimbolos.guardarSimboloValor(v.nombre.lexema, tipoDato.lexema,true, ambito, v.nombre)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String){
        for (v in listaVariables){
            if (v.expresion != null){

            }
        }
    }

    **/

    override fun getJavaCode(): String {
        var codigo = tipoDato.getJavaCode()+" "
        for (v in listaVariables){
            codigo += v.getJavaCode()+","
        }
        codigo = codigo.substring(0,codigo.length-1)
        codigo += ";"
        return codigo
    }


}