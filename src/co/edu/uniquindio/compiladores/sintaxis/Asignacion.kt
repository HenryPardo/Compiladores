package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos

import javafx.scene.control.TreeItem


/*class Asignacion(var variable: Variable, var expresion: Expresion) :Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Asignacion")
        root.children.add(variable.getArbolVisual())
        root.children.add(expresion.getArbolVisual())
        return root
    }*/
class Asignacion(var variable: Variable?,var identificador : Token?, var expresion: Expresion) :Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var root = TreeItem("Asignacion")
        if(variable != null){
            root.children.add(variable!!.getArbolVisual())
        }
        if(identificador != null){
            root.children.add(TreeItem("Identificador "+ identificador!!.lexema))
        }
        root.children.add(expresion.getArbolVisual())
        return root
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        var s : Simbolo? = null
        if(variable == null){
            s = tablaSimbolos.buscarSimboloValor(identificador!!.lexema,ambito)
            if(s==null){
                listaErrores.add(Error("El campo ${identificador!!.lexema} no existe en el ambito $ambito",identificador!!.fila,identificador!!.columna))
            }else{
                var tipo = s.tipo
                if(expresion!=null){
                    var tipoExp = expresion!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
                    if(tipo != tipoExp){
                        listaErrores.add(Error("El tipo de dato de la expresion ($tipo) no coincide con el tipo ($tipoExp) de dato del campo ${identificador!!.lexema}",identificador!!.fila,identificador!!.columna))
                    }
                }
            }
        }else{
            s = tablaSimbolos.buscarSimboloValor(variable!!.nombre.lexema,ambito)
            if(s==null){
                listaErrores.add(Error("El campo ${variable!!.nombre.lexema} no existe en el ambito $ambito",variable!!.nombre.fila,variable!!.nombre.columna))
            }else{
                var tipo = s.tipo
                if(expresion!=null){
                    var tipoExp = expresion!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
                    if(tipo != tipoExp){
                        listaErrores.add(Error("El tipo de dato de la expresion ($tipo) no coincide con el tipo ($tipoExp) de dato del campo ${variable!!.nombre.lexema}",variable!!.nombre.fila,variable!!.nombre.columna))
                    }
                }
            }
        }
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        if (variable != null){
            tablaSimbolos.guardarSimboloValor(variable!!.nombre.lexema,variable!!.tipoDato.lexema,ambito,variable!!.nombre.fila,variable!!.nombre.columna)
        }
        else{
            tablaSimbolos.buscarSimboloValor(identificador!!.lexema,ambito)
        }
    }


}