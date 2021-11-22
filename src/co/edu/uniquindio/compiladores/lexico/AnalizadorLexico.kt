package co.edu.uniquindio.compiladores.lexico

class


AnalizadorLexico(var codigoFuente: String) {

    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0

    fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int) =
        listaTokens.add(Token(lexema, categoria, fila, columna))

    fun analizar() {

        while (caracterActual != finCodigo) {

            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n') {
                obtenerSiguienteCaracter()
                continue
            }

            if (esEntero()) continue
            if (esDecimal()) continue
            if (esPalabraReservada()) continue
            if (esIdentificador()) continue
            if (esCadena()) continue
            if (esCaracter()) continue
            if (esComentario()) continue
            if (esAgrupador()) continue
            if (esSeparador()) continue
            if (esFinSentencia()) continue
            if (esOperadorAritmetico()) continue
            if (esOperadorLogico()) continue
            if (esOperadorRelacional()) continue
            if (esPalabraReservadaCiclo()) continue
            if (esPalabraReservadaCondicion()) continue

            almacenarToken("" + caracterActual, Categoria.DESCONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
        }

    }

    fun hacerBT(posicionInicial: Int, filaInicial: Int, columnaInicial: Int) {
        posicionActual = posicionInicial
        filaActual = filaInicial
        columnaActual = columnaInicial

        caracterActual = codigoFuente[posicionActual]
    }

    fun obtenerSiguienteCaracter() {
        if (posicionActual == codigoFuente.length - 1) {
            caracterActual = finCodigo
        } else {
            if (caracterActual == '\n') {
                filaActual++
                columnaActual = 0
            } else {
                columnaActual++
            }
            posicionActual++
            caracterActual = codigoFuente[posicionActual]

        }
    }

    fun esEntero(): Boolean {
        if (caracterActual == '#') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual.isDigit()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                if (caracterActual == '.') {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else if (caracterActual == '.') {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            } else {
                return false
            }
            almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esDecimal(): Boolean {
        if (caracterActual == '#') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '.' || caracterActual.isDigit()) {

                if (caracterActual == '.') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                } else {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    while (caracterActual.isDigit()) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }

                    if (caracterActual == '.') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                }
            }
            if (caracterActual.isDigit()) {
                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
            } else {
                return false
            }

            almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esIdentificador(): Boolean {
        if (caracterActual.isLetter()) {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual


            lexema += caracterActual
            obtenerSiguienteCaracter()
            var contador = 0;
            while ((caracterActual.isDigit() || caracterActual.isLetter() || caracterActual == '_') && contador <= 10) {
                contador++;
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }

            almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esCadena(): Boolean {
        if (caracterActual == '_') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isDefined()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '_') {
                    break
                }

            }

            if (caracterActual == '_') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.CADENA, filaInicial, columnaInicial)
                return true
            }

        }
        return false
    }

    fun esCaracter(): Boolean {
        if (caracterActual == '-') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual.isDefined()) {

                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '-') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.CARACTER, filaInicial, columnaInicial)
                    return true
                }
            }

        }
        return false
    }

    fun esComentario(): Boolean {
        if (caracterActual == '<') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isDefined()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '!') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual == '>') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        almacenarToken(lexema, Categoria.COMENTARIO_LINEA, filaInicial, columnaInicial)
                        return true
                    }
                } else if (caracterActual == '¡') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    while (caracterActual.isDefined()) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        if (caracterActual == '!') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()

                            if (caracterActual == '>') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()

                                almacenarToken(lexema, Categoria.COMENTARIO_BLOQUE, filaInicial, columnaInicial)
                                return true
                            }
                        }

                    }
                }
            }
        }
        return false
    }

    fun esAgrupador(): Boolean {

        if (caracterActual == '(' || caracterActual == ')' || caracterActual == '[' || caracterActual == ']' || caracterActual == '{' || caracterActual == '}') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            when (caracterActual) {
                '(' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.PARENTESIS_IZQ, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                ')' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.PARENTESIS_DER, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '[' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.LLAVE_IZQ, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                ']' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.LLAVE_DER, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '{' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.CORCHETE_IZQ, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '}' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.CORCHETE_DER, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
            }
        }
        return false
    }

    fun esSeparador(): Boolean {
        if (caracterActual == ',' || caracterActual == '.' || caracterActual == ':') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            when (caracterActual) {
                ',' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.COMA, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '.' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.PUNTO, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                ':' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.DOS_PUNTOS, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
            }

        }
        return false
    }

    fun esPalabraReservada(): Boolean {

        if (caracterActual == 'A' || caracterActual == 'F' || caracterActual == 'P') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            when (caracterActual) {
                'A' -> {
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'L') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'T') {
                            lexema += caracterActual
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA_VARIABLE, filaInicial, columnaInicial)
                            obtenerSiguienteCaracter()
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
                'F' -> {
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'U') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'N') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'C') {
                                lexema += caracterActual
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_METODO, filaInicial, columnaInicial)
                                obtenerSiguienteCaracter()
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
                'P' -> {
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'A') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'G') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'E') {
                                lexema += caracterActual
                                almacenarToken(lexema,Categoria.PALABRA_RESERVADA_VARIABLE,filaInicial,columnaInicial)
                                obtenerSiguienteCaracter()
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        return false
    }

    /**
     *  ^ Suma (+)
     *  ~ Resta (-)
     *  ° Multiplicacion (*)
     *  @ Division (/)
     *  | Modulo (%)
     */
    fun esOperadorAritmetico(): Boolean {

        if (caracterActual == '^' || caracterActual == '~' || caracterActual == '°' || caracterActual == '@' || caracterActual == '|') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            when (caracterActual) {
                '^' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO_SUMA, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '~' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO_RESTA, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '°' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO_MULTIPLICACION, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '@' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO_DIVISION, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '|' ->{
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO_MODULO,filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
            }
        }
        return false
    }

    /**
     * & And
     * $ Or
     * % negacion
     */
    fun esOperadorLogico(): Boolean {

        if (caracterActual == '&' || caracterActual == '$' || caracterActual == '%')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            when(caracterActual){
                '&' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.OPERADOR_LOGICO_Y, filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '$' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.OPERADOR_LOGICO_O, filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '%' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.OPERADOR_LOGICO_NEGACION, filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
            }
        }
        return false

    }

    /**
     * = Igual a
     * ¿ Menor que
     * ? Mayor que
     * * Menor Igual que
     * + Mayor Igual que
     * ¬ Diferente
     */
    fun esOperadorRelacional(): Boolean {

        if (caracterActual == '=' || caracterActual == '¿' || caracterActual == '?' || caracterActual == '¬' || caracterActual == '*' || caracterActual == '+') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            when (caracterActual){
                '=' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL_IGUAL,filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '¿' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL_MENOR,filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '?' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL_MAYOR, filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '¬' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL_DIFERENTE,filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '*' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL_MENOR_IGUAL,filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '+' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL_MAYOR_IGUAL,filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
            }
        }
        return false
    }

    /**
     * MIE (While)
     * PARA (For)
     */
    fun esPalabraReservadaCiclo(): Boolean {

        if (caracterActual == 'M' || caracterActual == 'P') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            when (caracterActual) {
                'M' -> {
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'I') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'E') {
                            lexema += caracterActual
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA_CICLO_FOR, filaInicial, columnaInicial)
                            obtenerSiguienteCaracter()
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
                'P' -> {
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'A') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'R') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'A') {
                                lexema += caracterActual
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_CICLO_WHILE, filaInicial, columnaInicial)
                                obtenerSiguienteCaracter()
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        return false
    }

    /**
     * CON (if)
     * DELO (else)
     */
    fun esPalabraReservadaCondicion(): Boolean{

        if (caracterActual == 'C' || caracterActual == 'D' ) {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            when (caracterActual) {
                'C' -> {
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'O') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'N') {
                            lexema += caracterActual
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA_CONDICION_IF, filaInicial, columnaInicial)
                            obtenerSiguienteCaracter()
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
                'D' -> {
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'E') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'L') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'O') {
                                lexema += caracterActual
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_CONDICION_ELSE, filaInicial, columnaInicial)
                                obtenerSiguienteCaracter()
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        return false
    }

    fun esFinSentencia(): Boolean {

        if (caracterActual == '/') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            lexema += caracterActual
            almacenarToken(lexema, Categoria.FINAL, filaInicial, columnaInicial)
            obtenerSiguienteCaracter()
            return true
        }
        return false
    }
}
