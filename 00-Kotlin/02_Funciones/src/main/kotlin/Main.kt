package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    //ejemplo de named parameter
    val sueldoTotal:Double = calcularSueldo(10.00, bonoEspecial = 20.00);
}


fun calcularSueldo(
    sueldo: Double,           // Requerido
    tasa: Double = 12.00,     // Opcional (defecto)
    bonoEspecial: Double? = null // Opcional (nullable)
): Double {
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa);
    } else {
        return sueldo * (100 / tasa) * bonoEspecial;
    }
}

abstract class NumerosJava{
    protected val numeroUno: Int;
    protected val numeroDos: Int;

    constructor(uno: Int, dos: Int){
        this.numeroUno = uno;
        this.numeroDos = dos;
        println("Inicializando");
    }
}

abstract class Numeros
    ( //constructor primario en los parámetros de la clase
            //1. parámetro normal sin modificador de acceso
            //uno: Int (sin modificador de acceso
            //2. parámetro y modificador de acceso
            protected val numeroUno: Int,
            protected val numeroDos: Int,
            parametroNoUsadoNoPropiedadDeLaClase:Int?=null
            ){
        //bloque constructor primario opcional
        init {
            this.numeroUno;
            this.numeroDos;
            println("Inicializando");
        }
    }
class Suma(    // Constructor primario
    unoParametro: Int,    // Parámetro
    dosParametro: Int     // Parámetros
) : Numeros(    // Clase padre, Numeros (extendiendo)
    unoParametro,
    dosParametro
) {
    // Cuerpo de la clase (puede contener métodos o propiedades adicionales)
}
