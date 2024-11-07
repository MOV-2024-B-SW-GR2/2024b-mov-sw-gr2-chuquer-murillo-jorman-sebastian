package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    //ejemplo de named parameter
    val sueldoTotal:Double = calcularSueldo(10.00, bonoEspecial = 20.00);
    val sumaA = Suma(1,1)
    val sumaB = Suma(null,1)
    val sumaC = Suma(1,null)
    val sumaD = Suma(null,null)
    sumaA.sumar()
    sumaB.sumar()
    sumaC.sumar()
    sumaD.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)
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
    //Clase 007
    // Cuerpo de la clase (puede contener métodos o propiedades adicionales)
    public val publicoExplicito:String = "publicas"
    val soypublicoImplicito:String = "publicas implicito"
   //bloque del constructor primario
    init {
        this.numeroUno
       this.numeroDos
       numeroUno //this opcional para propiedades y métodos
       numeroDos
       this.soypublicoImplicito
       soypublicoImplicito
    }

    constructor( //constructor secundario
        //this llama al constructor primario
    unoParametro: Int?, dosParametro: Int):this(
        //necesitamos especificar que el nullable se convierta en 0 en caso de cumplirse
        if(unoParametro == null) 0 else unoParametro,
        dosParametro
    ){
        //bloque para el constructor secundario
    }
    //otros dos constructores para aceptar los otros tipos de nullable y casos
    constructor(
        unoParametro: Int, dosParametro: Int?

    ):this(
        if(dosParametro == null) 0 else dosParametro,
    )
    constructor(
        unoParametro: Int?, dosParametro: Int?
    ):this(
        if(unoParametro == null) 0 else unoParametro,
        if(dosParametro == null) 0 else dosParametro
    )
    fun sumar():Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    //registro de las veces que el usuario invoca a una función
    //objeto que acompaña a todas las instancias de la clase (Singleton?) similar a static

    companion object {
        //compartir funciones y variables
        val pi = 3.14
        //Accesible: NombreClase.metodo,
        //Suma.pi
        fun elevarAlCuadrado(num:Int):Int{
            return num*num
        }
        //Suma.elevarAlCuadrado()
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){
            historialSumas.add(valorTotalSuma)
        }
    }
}
