package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.AnalizadorSemantico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico
import co.edu.uniquindio.compiladores.sintaxis.UnidadDeCompilacion
import javafx.scene.control.*


class controlador {
    @FXML
    lateinit var codigo: TextArea;

    @FXML lateinit var resultado: ListView<String>

    @FXML lateinit var arbolVisual: TreeView<String>

    private lateinit var lexico: AnalizadorLexico
    private lateinit var sintaxis: AnalizadorSintactico
    private var unidadCompilacion:UnidadDeCompilacion? = null

   /*fun analizarCodigo(e: ActionEvent){
        resultado.items.clear()
        //casa #25 #.2 #0.3 <comentario linea!> <comentario ¡bloque !> _cadena123_ -%- . : , ()[]{}
        val lexico = AnalizadorLexico(codigo.text)
        lexico.analizar()

        for (token in lexico.listaTokens){
            resultado.items.add(token.toString())
        }

        var sintatico = AnalizadorSintactico(lexico.listaTokens)

        arbolVisual.root = sintatico.esUnidadDeCompilacion()?.getArbolVisual()

        println(sintatico.esUnidadDeCompilacion()?.getArbolVisual())
        println(sintatico.esUnidadDeCompilacion())
    }*/

   fun analizarCodigo(e: ActionEvent){
        resultado.items.clear()

        if(codigo.text.isNotEmpty()){
            lexico = AnalizadorLexico(codigo.text)
            lexico.analizar()

            for (token in lexico.listaTokens){
                resultado.items.add(token.toString())
            }

            //agregar los errores de lexico a la interfaz

            if(lexico.listaErrores.isEmpty()){ //aqui siempre va a entrar porque no almacenamos ningun error
                sintaxis = AnalizadorSintactico(lexico.listaTokens)
                
                unidadCompilacion = sintaxis.esUnidadDeCompilacion()

                //agregar los errores de sintaxis a la interfaz

                if(unidadCompilacion != null){
                    arbolVisual.root =unidadCompilacion?.getArbolVisual()

                    val semantica = AnalizadorSemantico(unidadCompilacion!!)
                    semantica.llenarTablaSimbolos()
                    println( semantica.tablaSimbolos)

                    semantica.analizarSemantica()
                    println( semantica.listaErrores)

                }
                else{
                    arbolVisual.root = TreeItem("unidad de compilacion")
                }
            }
            else{
                val alerta = Alert(Alert.AlertType.ERROR)
                alerta.headerText = null
                alerta.contentText = "Hay errores lexicos en el codigo fuente"
                alerta.showAndWait()
            }

        }

    }
}