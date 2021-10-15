package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TextArea
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.ListView


class controlador {
    @FXML
    lateinit var codigo: TextArea;

    @FXML lateinit var resultado: ListView<String>

    fun analizarCodigo(e: ActionEvent){
        resultado.items.clear()
        //casa #25 #.2 #0.3 <comentario linea!> <comentario ¡bloque !> _cadena123_ -%- . : , ()[]{}
        val lexico = AnalizadorLexico(codigo.text)
        lexico.analizar()
        for (token in lexico.listaTokens){
            resultado.items.add(token.toString())
        }

    }


}