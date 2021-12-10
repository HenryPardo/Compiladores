package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Variable(var tipoDato: Token, var nombre: Token): Sentencia() {
    override fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Variable")
        root.children.add(TreeItem("Tipo dato: " +tipoDato.lexema))
        root.children.add(TreeItem("Nombre: " +nombre.lexema))
        return root
    }

    override fun toString(): String {
        return "Variable(tipoDato=$tipoDato, nombre=$nombre)"
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        tablaSimbolos.guardarSimboloValor(nombre.lexema,tipoDato.lexema,ambito,nombre.fila,nombre.columna)
    }

    override fun getJavaCode():String{

        return if (tipoDato != null) {
            tipoDato.getJavaCode()+" "+nombre.getJavaCode()
        }else{
            nombre.getJavaCode()
        }
    }
}
