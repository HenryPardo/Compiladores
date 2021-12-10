package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Funcion(var tipoRetorno:Token,var identificador:Token,
              var listaParametros: ArrayList<Parametro>, var listaSentencias: ArrayList<Sentencia>) {

    fun getArbolVisual(): TreeItem<String>
    {
        var root = TreeItem("Funcion")
        root.children.add(TreeItem("Tipo dato : "+tipoRetorno.lexema))
        root.children.add(TreeItem("Nombre : "+identificador.lexema))

        var rootParametros = TreeItem("Parametros")
        for(i in listaParametros){
            rootParametros.children.add(i.getArbolVisual())
        }
        root.children.add(rootParametros)

        var rootSentencia = TreeItem("Sentencias")
        for(i in listaSentencias){
            rootSentencia.children.add(i.getArbolVisual())
        }
        root.children.add(rootSentencia)

        return root
    }

    fun obtenerTipoParametros():ArrayList<String>{
        var lista = ArrayList<String>()
        for (p in listaParametros){
            lista.add(p.tipoDato.lexema)
        }
        return lista
    }

    fun llenarTablaSimbolos(tablaSimbolos : TablaSimbolos,listaErrores: ArrayList<Error>, ambito : Ambito){
        tablaSimbolos.guardarSimboloFuncion(identificador.lexema, tipoRetorno.lexema,ambito,obtenerTipoParametros(),identificador.fila, identificador.columna )

        for (p in listaParametros){
            tablaSimbolos.guardarSimboloValor( p.identificador.lexema, p.tipoDato.lexema, Ambito(identificador.lexema,identificador.fila), p.identificador.fila, p.identificador.columna )
        }

        for (s in listaSentencias){
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, Ambito(identificador.lexema,identificador.fila) )
        }
    }

    fun analizarSemantica(tablaSimbolos : TablaSimbolos,listaErrores: ArrayList<Error>){
        for (s in listaSentencias){
            s.analizarSemantica(tablaSimbolos, listaErrores, Ambito(identificador.lexema,identificador.fila) )
        }
    }

    fun getJavaCode():String{

        var codigo = ""

        if (identificador.lexema == "principal"){
            codigo = "public static void main(String[] args){"
        }else {
            codigo = "static" + tipoRetorno.getJavaCode() + " " + identificador.getJavaCode() + " ( "
            if (listaParametros.isNotEmpty()) {
                for (p in listaParametros) {
                    codigo += p.getJavaCode() + ","
                }
                codigo = codigo.substring(0, codigo.length - 1)

            }
            codigo += "){"
        }
        for (s in listaSentencias){
            codigo += s.getJavaCode()
        }
        codigo += "}"
        return codigo
    }
}