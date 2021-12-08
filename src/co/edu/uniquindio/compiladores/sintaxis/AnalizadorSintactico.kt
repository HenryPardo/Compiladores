package co.edu.uniquindio.compiladores.sintaxis
import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token

class AnalizadorSintactico (var listaTokens:ArrayList<Token> ) {
    var posicionActual = 0
    var tokenActual = listaTokens[posicionActual]
    var listaErrores = ArrayList<Error>()
    fun obtenerSiguienteToken(){
        posicionActual++;
        if( posicionActual < listaTokens.size ) {
            tokenActual = listaTokens[posicionActual]
        }
    }

    fun reportarError(mensaje:String){
        listaErrores.add(Error(mensaje,tokenActual.fila,tokenActual.columna))
    }

    /**
     * <UnidadCompilacion> ::= [<ListaVariables>] [<ListaFunciones>]
     */
    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {
        val ListaVariables: ArrayList<Variable> = esListaVariables()
        val listaFunciones: ArrayList<Funcion> = esListaFunciones()
        return if (listaFunciones.size > 0 || ListaVariables.size > 0) {
            UnidadDeCompilacion(listaFunciones,ListaVariables)
        } else null
    }




    /**
     * <Funcion> ::=  func <TipoRetorno> identificador “(“[<ListaParametro>] ”)” ”{“ [<ListaSentencias>] “}”
     */
    fun esFuncion(): Funcion?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA_METODO){
            obtenerSiguienteToken()
            var tipoRetorno = esTipoRetorno()
            if(tipoRetorno != null){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                    var nombreFuncion = tokenActual
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.PARENTESIS_IZQ){
                        obtenerSiguienteToken()
                        var listaParametros = esListaParametros()

                        if(tokenActual.categoria == Categoria.PARENTESIS_DER){
                            obtenerSiguienteToken()
                            if(tokenActual.categoria == Categoria.LLAVE_IZQ) {
                                obtenerSiguienteToken()
                                var listaSencias = esListaSentencias()
                                if(listaSencias.size > 0){
                                  //  obtenerSiguienteToken()
                                }
                                if (tokenActual.categoria == Categoria.LLAVE_DER) {
                                    obtenerSiguienteToken()
                                    return Funcion(tipoRetorno,nombreFuncion, listaParametros, listaSencias)
                                }
                            }
                        }

                    }
                }
            }else{
               reportarError("El tipo de retorno no es valido")
            }
        }
        return null
    }
    /**
     *<ListaFunciones> ::= <Funcion> [ListaFunciones]
     */
    fun esListaFunciones(): ArrayList<Funcion>{
        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()
        while(funcion != null){
            listaFunciones.add(funcion)
            funcion = esFuncion()
      //      obtenerSiguienteToken()
        }
        return listaFunciones
    }

    /**
     * <ListaVariables> ::= <DeclaracionVariable> [<ListaVariables>]
     */
    fun esListaVariables(): ArrayList<Variable>{
        var listaVariables = ArrayList<Variable>()
        var variable = esDeclaracionMutable()
        while(variable != null){
            listaVariables.add(variable)
            variable = esDeclaracionMutable()
        }
        return listaVariables
    }
    /**
     *<ListaSentencia>::=<Setencia>[<ListaSentencia>]
     */
    fun esListaSentencias(): ArrayList<Sentencia> {
        var listaSentencias = ArrayList<Sentencia>()
        var sentencia = esSentencia()
        while(sentencia != null){
            listaSentencias.add(sentencia)
            sentencia = esSentencia()
        }
        return listaSentencias
    }

    /**
     * <DeclaracionMutable> ::= <TipoDato> : identificador
     */
    fun esVariable(): Variable?{
        var tipoDato = esTipoDato()
        if(tipoDato != null){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.DOS_PUNTOS) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    var identificador = tokenActual
                    return Variable(tipoDato, identificador)
                }
            }
        }
        return null
    }

    /**
     * <Sentencia> ::= <Decision> | <Asignacion> |
     * <Impresion> | <CicloWhile> | <CicloFor> | <Retorno> | <Lectura>  | <InvocacionFuncion> | <ExpresionSentencia>
     */
    fun esSentencia(): Sentencia?{
        var sentencia:Sentencia? =  esDeclaracionMutable()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esAsignacion()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esImpresion()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esCicloWhile()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esCicloFor()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esRetorno()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esLectura()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esInvocacionFuncion()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esDecision()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esIncremento()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esDecremento()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esDeclaracionInMutable()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esAsignacionInmutable()
        if(sentencia != null){
            return sentencia
        }
        return sentencia
    }

    fun esExpresionSentencia(): ExpresionSentencia?{
        return null
    }

    /**
     * <InvocacionFuncion> ::= identificador”(“ [<ListaArgumentos>]”)”
     */
    fun esInvocacionFuncion(): InvocacionFuncion?{
        if(tokenActual.categoria == Categoria.IDENTIFICADOR){
            var identificador = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.PARENTESIS_IZQ){
                obtenerSiguienteToken()
                var invo =  InvocacionFuncion(identificador,ArrayList<ListaArgumentos>())
                var ListaArgumentos = esListaArgumentos(invo)


                //if(tokenActual.categoria == Categoria.PARENTESIS_DER){
                    return invo
              // }

            }
        }
        return null
    }

    /**
    <ListaArgumentos> ::= identificador [“,” <ListaArgumentos>]
     */
    fun esListaArgumentos(invocacion : InvocacionFuncion): ListaArgumentos?{
        if(tokenActual.categoria == Categoria.IDENTIFICADOR){
            var identificador = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.COMA){
                obtenerSiguienteToken()
                invocacion.listaArgumentos?.add(ListaArgumentos(identificador))
                var argumento = esListaArgumentos(invocacion)
                while(argumento != null){
                    //invocacion.listaArgumentos?.add(argumento)
                    obtenerSiguienteToken()
                    argumento = esListaArgumentos(invocacion)
                }
                return ListaArgumentos(identificador)
            }else{
                if(tokenActual.categoria == Categoria.PARENTESIS_DER){
                    invocacion.listaArgumentos?.add(ListaArgumentos(identificador))
                    return ListaArgumentos(identificador)
                }else{
                    return null
                }
            }
        }
        return null
    }
    /**
     * <Lectura> ::= READ <Declaracion>
     */
    private fun esLectura(): Lectura?{

        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA_LECTURA){
            obtenerSiguienteToken()
            var variable = esVariable()
            if(variable != null){
             return Lectura(variable)
            }
        }
        return null
    }

    /**
     * <Retorno> ::= RETURNS  [<Expresion>] FinSentencia
     */
    private fun esRetorno(): Retorno?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA_RETORNO){

            obtenerSiguienteToken()
            var expresion = esExpresion()
            return Retorno(expresion)
        }
        return null
    }

    /**
     * FOREXEC”(“ <Asignacion>  “:”  <ExpresionLogica> “:” <incremento> | <decremento>”)” “{“ <ListaSentencia> “}”

     */
    fun esCicloFor(): CicloFor? {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA_CICLO_FOR){
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_IZQ) {
            obtenerSiguienteToken()
            var asignacion = esAsignacion()
            if(asignacion != null){
                //obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.DOS_PUNTOS){
                    obtenerSiguienteToken()
                    var expresionLogica = esExpresionLogica()
                    if(expresionLogica != null){
                       // obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.DOS_PUNTOS){
                            obtenerSiguienteToken()
                            var incremento = esIncremento()
                            var decremento : Decremento? = null

                            if(incremento == null){
                                decremento = esDecremento()
                            }

                            if(incremento != null || decremento != null){
                                obtenerSiguienteToken()
                                if (tokenActual.categoria == Categoria.PARENTESIS_DER) {
                                    obtenerSiguienteToken()
                                    if (tokenActual.categoria == Categoria.LLAVE_IZQ){
                                        obtenerSiguienteToken()
                                        var sentencias : ArrayList<Sentencia> = esListaSentencias()
                                        if (tokenActual.categoria == Categoria.LLAVE_DER){
                                            obtenerSiguienteToken()
                                            return CicloFor(asignacion,expresionLogica,incremento, decremento, sentencias )

                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        }
        return null
    }

    /**
     *<Incremento> ::= “^^”identificador

     */
    fun esIncremento(): Incremento?{
        if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_SUMA){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_SUMA) {
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    return Incremento(tokenActual)
                }
            }

         }
        return null
    }

    /**
     *<Decremento> ::= identificador  “~~”
     */
    fun esDecremento(): Decremento ?{
        if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_RESTA) {
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_RESTA) {
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    return Decremento(tokenActual)
                }
            }
        }
        return null
    }
    /**
     * <CicloWhile> ::= DURING ”(“<ExpresionLogica”)” ”{“[<ListaSentencia>] ”}”
     */
    fun esCicloWhile(): CicloWhile?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA_CICLO_WHILE){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.PARENTESIS_IZQ){
                obtenerSiguienteToken()
                var expresionLogica = esExpresionLogica()
                if(expresionLogica != null){
                    //obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.PARENTESIS_DER){
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.LLAVE_IZQ){
                            obtenerSiguienteToken()
                            var listaSentencia : ArrayList<Sentencia> = esListaSentencias()
                            if(tokenActual.categoria == Categoria.LLAVE_DER){
                                obtenerSiguienteToken()
                                return CicloWhile(expresionLogica, listaSentencia)
                            }
                        }
                    }
                }
            }
        }
        return null
    }
    /**
     * <Impresion> ::= WRITE <Expresion> | identificador FinSentencia
     */
    fun esImpresion(): Impresion?{

        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA_IMPRIMIR){
            obtenerSiguienteToken()

            if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                var identificador = tokenActual
                if(identificador != null){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.FINAL){
                        return Impresion(null, identificador)
                    }
                }
            }else{
                var expresion = esExpresion()
                if(expresion != null){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.FINAL){
                        return Impresion(expresion,null)
                    }
                }
            }

        }


        return null


    }

    /**
     * <Asignacion> ::= = <DeclaracionMutable>  = <Expresion>
     */
    fun esAsignacion(): Asignacion?{
        if(tokenActual.categoria == Categoria.OPERADOR_RELACIONAL_IGUAL) {
            obtenerSiguienteToken()
            var declaracion = esDeclaracionMutable()

        if(declaracion != null){
            if(tokenActual.categoria == Categoria.OPERADOR_RELACIONAL_IGUAL) {
                obtenerSiguienteToken()
                var expresion = esExpresion()
            if(expresion != null){
                return Asignacion(declaracion,expresion)
            }
            }
        }
        }
        return null
    }

    /**
     * <Asignacion> ::= = | <DeclaracionInmutable>  = <Expresion>
     */
    fun esAsignacionInmutable(): AsignacionInmutable?{
        if(tokenActual.lexema == "&"){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.OPERADOR_RELACIONAL_IGUAL) {
                obtenerSiguienteToken()
                var declaracion = esDeclaracionInMutable()
                if(declaracion != null){
                    if(tokenActual.categoria == Categoria.OPERADOR_RELACIONAL_IGUAL) {
                        obtenerSiguienteToken()
                        var expresion = esExpresion()
                        if(expresion != null){
                            return AsignacionInmutable(declaracion,expresion)
                        }
                    }
                }
            }
        }
        return null
    }
    /**
     *<DeclaracionMutable> ::= "ALT" <TipoDato> : identificador
     */
    fun esDeclaracionMutable(): Variable?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA_VARIABLE){
            obtenerSiguienteToken()
            var variable = esVariable()
            if(variable != null){
                obtenerSiguienteToken()
                return variable
            }
        }
        return null
    }
    /**
     *<DeclaracionInMutable> ::= "|ALT" <TipoDato> : identificador
     */
    fun esDeclaracionInMutable(): Inmutable?{
        if(tokenActual.lexema == "|"){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.PALABRA_RESERVADA_VARIABLE){
                obtenerSiguienteToken()
                var variable = esVariable()
                if(variable != null){
                    obtenerSiguienteToken()
                    return Inmutable(variable.tipoDato,variable.nombre);
                }
            }
        }
        return null
    }

    /**
     *<Expresion> ::= <ExpresionLogica> | <ExpresionAritmetica> | <ExpresionCadena> | <ExpresionRelacional>
     */
    fun esExpresion(): Expresion?{
        var expresion:Expresion? =  esExpresionRelacional()
        if(expresion != null){
            return expresion
        }
        expresion = esExpresionAritmetica()
        if(expresion != null){
            return expresion
        }
        expresion = esExpresionLogica()
        if(expresion != null){
            return expresion
        }
//    expresion = esExpresionCadena()
        if(expresion != null){
            return expresion
        }

        return null
    }

    /**
     * <ExpresionCadena> ::= cadenaDeCaracteres ["^"<Expresion> ] | <Expresion> “^” cadenaCaracteres
     */
    fun esExpresionCadena(): ExpresionCadena?{
        if(tokenActual.categoria == Categoria.CADENA){
            var caracteres = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_SUMA) {
                var expresion = esExpresion()
                return ExpresionCadena(caracteres, expresion)
            }
        }else{
            var expresion = esExpresion()
            if(expresion != null){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_SUMA) {
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.CADENA) {
                        var caracteres = tokenActual
                        return ExpresionCadena(caracteres, expresion)
                    }
                }
            }
        }
        return null
    }

    /**
     *<ExpresionRelacional> ::= ¬ <ExpresionAritmetica> operadorRelacional <ExpresionAritmetica>
     *
     */
    fun esExpresionRelacional(): ExpresionRelacional?{
        if(tokenActual.categoria == Categoria.OPERADOR_RELACIONAL_DIFERENTE){
            obtenerSiguienteToken()
            var primeraExpresion = esExpresionAritmetica()

            if(primeraExpresion != null){
                if(tokenActual.categoria == Categoria.OPERADOR_RELACIONAL_IGUAL ||
                    tokenActual.categoria == Categoria.OPERADOR_RELACIONAL_MAYOR ||
                    tokenActual.categoria == Categoria.OPERADOR_RELACIONAL_MENOR ||
                    tokenActual.categoria == Categoria.OPERADOR_RELACIONAL_MAYOR_IGUAL ||
                    tokenActual.categoria == Categoria.OPERADOR_RELACIONAL_MENOR_IGUAL ) { //CAMBIAR a relacional

                    var operadorRelacional = tokenActual
                    obtenerSiguienteToken()

                    var segundaExpresion = esExpresionAritmetica()
                    if(segundaExpresion != null){
                        return ExpresionRelacional(primeraExpresion,operadorRelacional,segundaExpresion)
                    }

                }
            }
        }
        return null
    }

    /**
     * <ExpresionAritmetica> ::= <ExpresionAritmetica> operadorAritmetico <ExpresionAritmetica> | "("<ExpresionAritmetica>")" | <ValorNumerico>
     */
    fun esExpresionAritmetica(): ExpresionAritmetica?{
        if(tokenActual.categoria == Categoria.PARENTESIS_IZQ){
            obtenerSiguienteToken()

            val exp1 = esExpresionAritmetica()

            if(exp1 != null){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.PARENTESIS_DER){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_DIVISION ||
                        tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_RESTA ||
                        tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_SUMA ||
                        tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_MODULO ||
                        tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_MULTIPLICACION){
                        val operador = tokenActual;
                        obtenerSiguienteToken()
                        val exp2 = esExpresionAritmetica()
                        if(exp2 != null){
                            return ExpresionAritmetica(exp1, operador, exp2)
                        }
                    }else{
                        return ExpresionAritmetica(exp1)
                    }
                }
            }
        }else{
            val valor = esValorNumerico()
            if(valor != null){
                obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_DIVISION ||
                        tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_RESTA ||
                        tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_SUMA ||
                        tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_MODULO ||
                        tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_MULTIPLICACION){
                        val operador = tokenActual;
                        obtenerSiguienteToken()
                        val exp2 = esExpresionAritmetica()
                        if(exp2 != null){
                            return ExpresionAritmetica(valor, operador, exp2)
                        }
                    }else{
                        return ExpresionAritmetica(valor)
                    }

            }
        }

        return null
    }
    fun esValorNumerico(): ValorNumerico?{
        var signo : Token? = null
        if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_SUMA ||
            tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_RESTA){
            obtenerSiguienteToken()
            signo = tokenActual
        }
        if(tokenActual.categoria == Categoria.ENTERO ||
            tokenActual.categoria == Categoria.DECIMAL ||
            tokenActual.categoria == Categoria.IDENTIFICADOR){
            val termino = tokenActual
            return ValorNumerico(signo,termino)
        }
        return null
    }
    /**
     *
     *   <Decision> ::=  IFIS “(“<ExpresionLogica>”)” “{“ [<ListaSentencia>]} “}” [ IFNOT “{“ [<ListaSentencia>]“}” ]
     */
    fun esDecision(): Decision?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA_CONDICION_IF){
            obtenerSiguienteToken()
               if(tokenActual.categoria == Categoria.PARENTESIS_IZQ){
               obtenerSiguienteToken()
                var expresionLogica = esExpresionLogica()

                if(expresionLogica != null) {
                    //obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.PARENTESIS_DER) {
                       obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.LLAVE_IZQ) {
                            obtenerSiguienteToken()
                            var listaSentenciasSI: ArrayList<Sentencia> = esListaSentencias()
                          //  obtenerSiguienteToken()

                            if (tokenActual.categoria == Categoria.LLAVE_DER) {
                                obtenerSiguienteToken()
                                if(tokenActual.categoria == Categoria.PALABRA_RESERVADA_CONDICION_ELSE){
                                obtenerSiguienteToken()
                                if (tokenActual.categoria == Categoria.LLAVE_IZQ) {
                                    obtenerSiguienteToken()
                                    var listaSentenciasSINO: ArrayList<Sentencia> = esListaSentencias()
                                  //  obtenerSiguienteToken()
                                    if (tokenActual.categoria == Categoria.LLAVE_DER) {
                                        obtenerSiguienteToken()
                                        return Decision(expresionLogica,listaSentenciasSI, listaSentenciasSINO)
                                    }
                                }
                                }else{
                                    obtenerSiguienteToken()
                                    return Decision(expresionLogica,listaSentenciasSI, ArrayList<Sentencia>())
                                }
                            }
                        }
                  }
                }
           }
        }
        return null
    }

    /**
     *<ExpresionLogica> ::= ~<ExpresionLogica> operadorLogico <ExpresionLogica> |
     * ("<ExpresionLogica>")" | <ExpresionRelacional>

     */
    fun esExpresionLogica(): ExpresionLogica?{
            if(tokenActual.categoria == Categoria.PARENTESIS_IZQ){
                obtenerSiguienteToken()
                val exp1 = esExpresionLogica()

                if(exp1 != null){
                    if(tokenActual.categoria == Categoria.PARENTESIS_DER){
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_DIVISION ||
                            tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_RESTA ||
                            tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_SUMA ||
                            tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_MODULO ||
                            tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_MULTIPLICACION){
                            val operador = tokenActual;
                            obtenerSiguienteToken()
                            val exp2 = esExpresionLogica()
                            if(exp2 != null){
                                return ExpresionLogica(exp1, operador, exp2)
                            }
                        }else{
                            return ExpresionLogica(exp1)
                        }
                    }
                }
            }else{
                val valor = esExpresionRelacional()

                if(valor != null){

                  //  obtenerSiguienteToken()

                    if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_DIVISION ||
                        tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_RESTA ||
                        tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_SUMA ||
                        tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_MODULO ||
                        tokenActual.categoria == Categoria.OPERADOR_ARITMETICO_MULTIPLICACION){
                        val operador = tokenActual;
                        obtenerSiguienteToken()
                        val exp2 = esExpresionLogica()
                        if(exp2 != null){
                            return ExpresionLogica(valor, operador, exp2)
                        }
                    }else{
                        return ExpresionLogica(valor)
                    }

                }
            }

        return null
    }

    fun backTracking(posicion: Int){
        tokenActual =    listaTokens[posicion]

    }
    /**
     *<ListaParametros> ::= "("<Parametro>  [“,” <ListaParametros> ] ")"
     */
    fun esListaParametros(): ArrayList<Parametro>{

        var listaParametros : ArrayList<Parametro> = ArrayList()
        var parametro = esParametro() //Agregar sperador
            while (parametro != null) {
                listaParametros.add(parametro)
                obtenerSiguienteToken()
                if (tokenActual.lexema == ",") { //Hay que hacerlo con categoria.coma
                    obtenerSiguienteToken()
                    parametro = esParametro()
                } else {
                    parametro = null
                     return listaParametros
                }

            }



        return listaParametros
    }

    /**
     * <Parametro> ::= <TipoDato> “::” identificador
     */
    fun esParametro(): Parametro?{
        var tipoDato = esTipoDato()
        if(tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.lexema == ":") { //Hay que hacerlo con categoria.dosPuntos
                obtenerSiguienteToken()
                if (tokenActual.lexema == ":") { //Hay que hacerlo con categoria.dosPuntos
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                        var identificador = tokenActual;
                        return Parametro(tipoDato, identificador)
                    }
                }
            }
        }
        return null
    }

    /**
     * <TipoRetorno>  ::=  int | double | float | boolean | void | identificador
     */
    fun esTipoDato(): Token ?{
        if(tokenActual.lexema == "int" || tokenActual.lexema == "double" || tokenActual.lexema == "boolean"
            || tokenActual.categoria == Categoria.IDENTIFICADOR){
            return tokenActual
        }
        return null
    }
    /**
     * <TipoRetorno>  ::=  int | double | float | boolean | void | identificador
     */
    fun esTipoRetorno(): Token ?{
        if(tokenActual.lexema == "int" || tokenActual.lexema == "double" || tokenActual.lexema == "boolean"
            || tokenActual.lexema == "void" || tokenActual.categoria == Categoria.IDENTIFICADOR){
          return tokenActual
        }
        return null
    }
}