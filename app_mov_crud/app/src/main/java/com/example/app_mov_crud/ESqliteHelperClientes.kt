package com.example.app_mov_crud

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperClientes(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaClientes = """
            CREATE TABLE CLIENTES(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                email VARCHAR(50),
                telefono VARCHAR(50),
                fecha_registro VARCHAR(50)
            );
        """
        db?.execSQL(crearTablaClientes)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun consultarClientePorNombre(nombre: String): Clientes? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM CLIENTES WHERE nombre = ?"
        val parametrosConsultaLectura = arrayOf(nombre)
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)
        if (resultadoConsultaLectura.moveToFirst()) {
            val cliente = Clientes(
                resultadoConsultaLectura.getInt(0),
                resultadoConsultaLectura.getString(1),
                resultadoConsultaLectura.getString(2),
                resultadoConsultaLectura.getString(3),
                resultadoConsultaLectura.getString(4)
            )
            resultadoConsultaLectura.close()
            baseDatosLectura.close()
            return cliente
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return null
    }

    fun crearCliente(nombre: String, email: String?, telefono: String?, fechaRegistro: String?): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresGuardar = ContentValues()
        valoresGuardar.put("nombre", nombre)
        valoresGuardar.put("email", email)
        valoresGuardar.put("telefono", telefono)
        valoresGuardar.put("fecha_registro", fechaRegistro)
        val resultadoGuardar = baseDatosEscritura.insert("CLIENTES", null, valoresGuardar)
        baseDatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarClientePorNombre(nombre: String): Boolean {
        val baseDatosEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(nombre)
        val resultadoEliminar = baseDatosEscritura.delete("CLIENTES", "nombre=?", parametrosConsultaDelete)
        baseDatosEscritura.close()
        return resultadoEliminar > 0
    }

    fun actualizarClientePorNombre(nombre: String, email: String?, telefono: String?, fechaRegistro: String?): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("email", email)
        valoresAActualizar.put("telefono", telefono)
        valoresAActualizar.put("fecha_registro", fechaRegistro)
        val parametrosConsultaActualizar = arrayOf(nombre)
        val resultadoActualizar = baseDatosEscritura.update("CLIENTES", valoresAActualizar, "nombre=?", parametrosConsultaActualizar)
        baseDatosEscritura.close()
        return resultadoActualizar > 0
    }
}
