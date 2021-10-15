package co.edu.uniquindio.compiladores.vista

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage


class Vista : Application() {

    override fun start(primaryStage: Stage?) {
        val loader = FXMLLoader(Vista::class.java.getResource("/Vista.fxml"))


        val parent:Parent = loader.load()
        val scene = Scene( parent )
        primaryStage?.scene = scene
        primaryStage?.title = "Compilador"
        primaryStage?.show()

    }

    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            launch(Vista::class.java)
        }
    }
}