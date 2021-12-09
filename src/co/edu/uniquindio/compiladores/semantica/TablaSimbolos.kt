package co.edu.uniquindio.compiladores.semantica
import co.edu.uniquindio.compiladores.lexico.Error
class TablaSimbolos (var listaErrores : ArrayList<Error>){

    var listaSimbolos : ArrayList<Simbolo> = ArrayList()

    /**
     * Permite guardar un simbolo  que representa una variable, una constante, un parametro o un arreglo
     */
    fun guardarSimboloValor (nombre: String, tipoDato: String, ambito: String, fila: Int, columna: Int){

        val s = buscarSimboloValor(nombre,ambito)

        if(s==null){
            listaSimbolos.add(Simbolo(nombre, tipoDato, ambito, fila, columna))
        }
        else{
            listaErrores.add(Error ("el campo $nombre ya existe dentro del ambito $ambito",fila,columna))
        }

    }

    /**
     * Permite guardar un simbolo  que representa una funcion
     */
    fun guardarSimboloFuncion (nombre: String, tipoRetorno: String, ambito: String, tiposParametros : ArrayList<String>, fila: Int, columna: Int){
        val s = buscarSimboloFuncion(nombre,tiposParametros)
        if(s == null){
            listaSimbolos.add(Simbolo(nombre, tipoRetorno, ambito, tiposParametros))
        }
        else{
            listaErrores.add(Error ("la funcion $nombre ya existe dentro del ambito $ambito",fila,columna))
        }
    }

    /**
     * permite buscar un valor dentro de la tabla de simbolos
     */
    fun buscarSimboloValor (nombre:String, ambito: String) : Simbolo? {
        for (s in listaSimbolos){
            if(s.ambito != null) {
                if (s.nombre == nombre && s.ambito == ambito) {
                    return s
                }
            }
        }
        return null
    }
    /**
     * permite buscar una funcion dentro de la tabla de simbolos
     */
    fun buscarSimboloFuncion (nombre:String, tiposParametros: ArrayList<String> ) : Simbolo? {
        for (s in listaSimbolos){
            if(s.tiposParametros != null){
                if( s.nombre == nombre && s.tiposParametros == tiposParametros){
                    return s
                }
            }
        }
        return null
    }

    override fun toString(): String {
        return "TablaSimbolos(listaSimbolos=$listaSimbolos)"
    }


}