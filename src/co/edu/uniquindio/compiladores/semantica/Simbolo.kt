package co.edu.uniquindio.compiladores.semantica

class Simbolo (){

    var nombre: String = ""
    var tipo: String = ""
    var ambito: Ambito? = null
    var fila : Int = 0
    var columna : Int = 0
    var tiposParametros : ArrayList<String>? = null

    /**
    *constructor para crear un simbolo de tipo valor
     */
    constructor(nombre: String, tipoDato: String, ambito: Ambito, fila: Int, columna: Int):this(){
        this.nombre = nombre
        this.tipo = tipoDato
        this.ambito = ambito
        this.fila = fila
        this.columna = columna
    }

    /**
     * constructor para crear un simbolo de tipo funcion
     */
    constructor( nombre: String, tipoRetorno: String, ambito: Ambito, tiposParametros : ArrayList<String>, fila : Int):this(){
        this.nombre = nombre
        this.tipo = tipoRetorno
        this.ambito = ambito
        this.tiposParametros = tiposParametros
        this.fila = fila
    }

    override fun toString(): String {
        return if(tiposParametros == null){
            "Simbolo(nombre='$nombre', tipo='$tipo', ambito='${ambito?.nombre}', fila=$fila, columna=$columna"
        } else{
            "Simbolo(nombre='$nombre', tipo='$tipo', ambito='${ambito?.nombre}', fila=$fila, columna=$columna, tiposParametros=$tiposParametros)"
        }
    }
}