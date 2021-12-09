package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Impresion(var expresion: Expresion?, var identificador: Token?) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Impresion")
        if(expresion != null) {
            root.children.add(expresion?.getArbolVisual())
        }
        root.children.add(TreeItem("Nombre "+identificador?.lexema))
        return root
    }

    override fun toString(): String {
        return "Impresion(identificador = $identificador, expresion = $expresion)"
    }
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        super.analizarSemantica(tablaSimbolos, listaErrores, ambito)
    }

    override fun getJavaCode(): String {
        return "JOptionPane.showMessageDialog(null, "+ expresion!!.getJavaCode()+");"
    }
}