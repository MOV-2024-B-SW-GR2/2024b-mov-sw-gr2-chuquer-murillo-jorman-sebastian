package com.example.app_mov_crud

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelper(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaClientes = """
        CREATE TABLE CLIENTE(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre VARCHAR(50),
            email VARCHAR(50),
            telefono VARCHAR(50),
            fecha_registro VARCHAR(50)
        );
    """
        db?.execSQL(crearTablaClientes)

        val crearTablaPedidos = """
        CREATE TABLE PEDIDO(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            descripcion VARCHAR(50),
            monto REAL,
            cantidad INTEGER,
            cliente_id INTEGER,
            FOREIGN KEY(cliente_id) REFERENCES CLIENTE(id) ON DELETE CASCADE
        );
    """
        db?.execSQL(crearTablaPedidos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    override fun onConfigure(db: SQLiteDatabase?) {

    }


}
