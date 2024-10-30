package org.example

import java.util.Date

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    //esta es una variable mutable
    var edad = 26;
    println(edad); //se observa el valor de 26
    edad = 23;
    println(edad); //se observa el valor de 23

    //esta es una variable inmutable
    val nombre:String = "Jorman";
    println(nombre);
    //nombre = "Andrea"; no podemos asignarle un valor como tal nuevamente
    //Duck Typing
    val duck = 1;
    val numero:Int = 1;
    //uso de las clases de Java
    val fecha: Date = Date();
}
fun verificarEstadoCivil(estadoCivil:String):Unit{
    when(estadoCivil){
        ("C") ->  {println("Casado");}
        ("S") ->  {println("Soltero");}
        else ->  {println("No se sabe");}
    }
}
fun coquetear(esSoltero:Boolean):Unit{
    val coqueteo:String = if(esSoltero) "Si" else "No"
}
fun imprimirNombre(nombre:String):Unit{
    println(nombre);
    fun imprimirMismo(nombre:String):Unit{
        println(nombre);
    }
}
fun templateStrings(nombre: String):Unit{
    print("Nombre: $nombre");
    val segundoNombre = "Hola";
    print("Nombre: ${nombre.plus(segundoNombre)}");
}