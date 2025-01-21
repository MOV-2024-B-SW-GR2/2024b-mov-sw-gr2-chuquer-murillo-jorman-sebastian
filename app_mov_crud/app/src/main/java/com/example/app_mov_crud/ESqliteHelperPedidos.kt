package com.example.app_mov_crud

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperPedidos(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaPedidos = """
            CREATE TABLE PEDIDOS(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                descripcion VARCHAR(50),
                monto REAL,
                cantidad INTEGER
            );
        """
        db?.execSQL(crearTablaPedidos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun consultarPedidoPorDescripcion(descripcion: String): Pedidos? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM PEDIDOS WHERE descripcion = ?"
        val parametrosConsultaLectura = arrayOf(descripcion)
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)
        if (resultadoConsultaLectura.moveToFirst()) {
            val pedido = Pedidos(
                resultadoConsultaLectura.getInt(0),
                resultadoConsultaLectura.getString(1),
                resultadoConsultaLectura.getDouble(2),
                resultadoConsultaLectura.getInt(3)
            )
            resultadoConsultaLectura.close()
            baseDatosLectura.close()
            return pedido
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return null
    }

    fun crearPedido(descripcion: String, monto: Double, cantidad: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresGuardar = ContentValues()
        valoresGuardar.put("descripcion", descripcion)
        valoresGuardar.put("monto", monto)
        valoresGuardar.put("cantidad", cantidad)
        val resultadoGuardar = baseDatosEscritura.insert("PEDIDOS", null, valoresGuardar)
        baseDatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarPedidoPorDescripcion(descripcion: String): Boolean {
        val baseDatosEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(descripcion)
        val resultadoEliminar = baseDatosEscritura.delete("PEDIDOS", "descripcion=?", parametrosConsultaDelete)
        baseDatosEscritura.close()
        return resultadoEliminar > 0
    }

    fun actualizarPedidoPorDescripcion(descripcion: String, monto: Double, cantidad: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("monto", monto)
        valoresAActualizar.put("cantidad", cantidad)
        val parametrosConsultaActualizar = arrayOf(descripcion)
        val resultadoActualizar = baseDatosEscritura.update("PEDIDOS", valoresAActualizar, "descripcion=?", parametrosConsultaActualizar)
        baseDatosEscritura.close()
        return resultadoActualizar > 0
    }
}
