package co.edu.uniquindio.compiladores.lexico

class Token (var lexema:String, var categoria: Categoria, var fila:Int, var columna:Int) {

    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }

    fun getJavaCode():String {

        if (categoria == Categoria.ENTERO) {
            if (lexema == "#") {
                return "int"
            }
        } else if (categoria == Categoria.DECIMAL) {
            if (lexema == ".") {
                return "double"
            }
        }
        if (categoria == Categoria.CADENA) {
            if (lexema == "_")
                return "String"
        }
        if (categoria == Categoria.CARACTER) {
            if (lexema == "-") {
                return "char"
            }
        }
        if (categoria == Categoria.COMENTARIO_LINEA || categoria == Categoria.COMENTARIO_BLOQUE){
            if (lexema == "<!>" ){
                return lexema.replace("<!>", "//")
            }else if (lexema == "<¡!>" ){
                return lexema.replace("<¡!>","/* */")
            }
        }
        if (categoria == Categoria.PALABRA_RESERVADA_CICLO_FOR){
            if (lexema == "HAST")
            {
                return "for"
            }
        }
        if (categoria == Categoria.PALABRA_RESERVADA_CICLO_WHILE){
            if (lexema == "MIEN"){
                return "while"
            }
        }
        if (categoria == Categoria.PALABRA_RESERVADA_CONDICION_IF){
            if( lexema == "COND"){
                return "if"
            }
        }
        if (categoria == Categoria.PALABRA_RESERVADA_CONDICION_ELSE){
            if (lexema == "DELO"){
                return "else"
            }
        }
        if( categoria == Categoria.PALABRA_RESERVADA_TRY){
            if (lexema == "VER"){
                return "try"
            }
        }
        if (categoria == Categoria.PALABRA_RESERVADA_CATCH){
            if (lexema == "KAT"){
                return "catch"
            }
        }
        if (categoria == Categoria.PALABRA_RESERVADA_RETORNO){
            if (lexema == "EPI"){
                return "return"
            }
        }
        if (categoria == Categoria.PALABRA_RESERVADA_IMPRIMIR){
            if (lexema == "TYP")
            {
                return "system.out.println()"
            }
        }
        if (categoria == Categoria.OPERADOR_ARITMETICO_SUMA)
        {
            if (lexema == "^"){
                return "+"
            }
        }
        if (categoria == Categoria.OPERADOR_ARITMETICO_RESTA){
            if (lexema == "~"){
                return "-"
            }
        }
        if (categoria == Categoria.OPERADOR_ARITMETICO_MULTIPLICACION){
            if (lexema == "°"){
                return "*"
            }
        }
        if (categoria == Categoria.OPERADOR_ARITMETICO_DIVISION)
        {
            if (lexema == "@"){
                return  "/"
            }
        }
        if (categoria == Categoria.OPERADOR_ARITMETICO_MODULO)
        {
            if ( lexema == "|"){
                return "%"
            }
        }
        if (categoria == Categoria.OPERADOR_LOGICO_O){
            if (lexema == "$"){
                return "||"
            }
        }
        if (categoria == Categoria.OPERADOR_LOGICO_NEGACION){
            if (lexema == "%" )
            {
                return "!"
            }
        }

        if( categoria == Categoria.OPERADOR_RELACIONAL_IGUAL)
        {
            if (lexema == "="){
                return "=="
            }
        }
        if (categoria == Categoria.OPERADOR_RELACIONAL_MENOR){
            if (lexema == "¿"){
                return "<"
            }
        }
        if (categoria == Categoria.OPERADOR_RELACIONAL_MAYOR){
            if (lexema == "?"){
                return ">"
            }
        }
        if (categoria == Categoria.OPERADOR_RELACIONAL_MENOR_IGUAL){
            if (lexema == "*"){
                return "<="
            }
        }
        if (categoria == Categoria.OPERADOR_RELACIONAL_MAYOR_IGUAL){
            if (lexema == "+"){
                return ">="
            }
        }
        if (categoria == Categoria.OPERADOR_RELACIONAL_DIFERENTE){
            if (lexema == "¬"){
                return "!="
            }
        }
        if (categoria == Categoria.FINAL){
            if (lexema == "/"){
                return lexema.replace("/",";")
            }
        }
        return lexema
    }
}