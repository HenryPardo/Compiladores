package co.edu.uniquindio.compiladores.lexico

enum class Categoria {
    DESCONOCIDO, ENTERO, DECIMAL, IDENTIFICADOR, CADENA, CARACTER, COMENTARIO_LINEA,COMENTARIO_BLOQUE, PARENTESIS_IZQ, PARENTESIS_DER, LLAVE_IZQ, LLAVE_DER, CORCHETE_IZQ, CORCHETE_DER, PUNTO, COMA, DOS_PUNTOS, OPERADOR_ARITMETICO_SUMA, OPERADOR_ARITMETICO_RESTA,OPERADOR_ARITMETICO_DIVISION,OPERADOR_ARITMETICO_MULTIPLICACION,OPERADOR_ARITMETICO_MODULO,OPERADOR_RELACIONAL_IGUAL,
    OPERADOR_RELACIONAL_MENOR,OPERADOR_RELACIONAL_MAYOR,OPERADOR_LOGICO_Y,OPERADOR_LOGICO_O,OPERADOR_LOGICO_NEGACION,OPERADOR_RELACIONAL_MAYOR_IGUAL,OPERADOR_RELACIONAL_MENOR_IGUAL,FINAL,PALABRA_RESERVADA_VARIABLE,PALABRA_RESERVADA_METODO,PALABRA_RESERVADA_CLASE, OPERADOR_RELACIONAL_DIFERENTE, PALABRA_RESERVADA_CICLO_FOR, PALABRA_RESERVADA_CICLO_WHILE, PALABRA_RESERVADA_CONDICION_IF, PALABRA_RESERVADA_CONDICION_ELSE, PALABRA_RESERVADA_IMPRIMIR, PALABRA_RESERVADA_LECTURA, PALABRA_RESERVADA_RETORNO, PALABRA_RESERVADA_TRY, PALABRA_RESERVADA_CATCH
}