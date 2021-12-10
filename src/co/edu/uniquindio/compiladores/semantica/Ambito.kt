package co.edu.uniquindio.compiladores.semantica

class Ambito() {
    var nombre: String = ""
    var num : Int = 0

    constructor(nombre :String , num: Int):this () {
        this.nombre = nombre
        this.num = num
    }

    override fun toString(): String {
        return "Ambito(nombre='$nombre', num=$num)"
    }

}