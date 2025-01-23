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
            FOREIGN KEY(cliente_id) REFERENCES CLIENTES(id) ON DELETE CASCADE
        );
    """
        db?.execSQL(crearTablaPedidos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS CLIENTE")
            db?.execSQL("DROP TABLE IF EXISTS PEDIDO")
            onCreate(db)
        }
    }
    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.setForeignKeyConstraintsEnabled(true)
        //constraints necesarias para habilitar las claves foráneas
    }


}
