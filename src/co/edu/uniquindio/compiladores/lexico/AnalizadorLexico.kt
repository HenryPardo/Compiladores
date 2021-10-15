package co.edu.uniquindio.compiladores.lexico

class AnalizadorLexico (var codigoFuente:String) {

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
            if (esIdentificador()) continue
            if (esCadena()) continue
            if (esCaracter()) continue
            if (esComentario()) continue
            if (esAgrupador()) continue
            if (esSeparador()) continue
            if (esPalabraReservada()) continue
            if (esFinSentencia()) continue
            if (esOperadorAritmetico()) continue
            if (esOperadorLogico()) continue

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

    fun esPalabraReservada(): Boolean{

        if( caracterActual.equals("MIEN") || caracterActual.equals("PAR") || caracterActual.equals("CONS") )
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            when (caracterActual) {
                // me falta cuadrar esta parte, pero no se me ocurre como by: el Brayan

            }
        }

        return false
    }

    fun esOperadorAritmetico(): Boolean{

        if(caracterActual == '^' || caracterActual == '~' || caracterActual == '°' || caracterActual == '@')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            when(caracterActual) {
                '^' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '~' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '°' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial,columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '@' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
            }
        }
        return false
    }

    fun esOperadorLogico(): Boolean{

        if(caracterActual == '?' || caracterActual == '*' || caracterActual == '+' || caracterActual == '&' || caracterActual == '¬' )
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            when (caracterActual){
                '?' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '*' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '&' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                '¬' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
            }
        }
        return false
    }
    fun esFinSentencia() : Boolean{

        if( caracterActual == '/'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            when(caracterActual){
                '/' -> {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.FINAL, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
            }
        }
        return false

    }
}

