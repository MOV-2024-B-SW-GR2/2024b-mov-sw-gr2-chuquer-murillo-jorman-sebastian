package com.example.app_mov_crud
import android.content.Context
import android.content.ContentValues
class EntityController (context: Context) {
    private val dbHelper = ESqliteHelper(context)


    //Crear un cliente
    //Crear un cliente
    fun crearCliente(cliente: Cliente) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", cliente.nombre)
            put("email", cliente.email)
            put("telefono", cliente.telefono)
            put("fecha_registro", cliente.fecha_registro)
            put("latitud", cliente.latitud)
            put("longitud", cliente.longitud)
        }

        var resultado = db.insert("CLIENTE", null, valores)
        db.close()
    }
    //Listar clientes
    fun listarCliente(): List<Cliente> {
        val db = dbHelper.writableDatabase
        val posicion = db.rawQuery("SELECT * FROM CLIENTE", null)
        val clientes = arrayListOf<Cliente>()
        while(posicion.moveToNext()){
            val id = posicion.getInt(0)
            val nombre = posicion.getString(1)
            val email = posicion.getString(2)
            val telefono = posicion.getString(3)
            val fecha_registro = posicion.getString(4)
            val latitud = posicion.getDouble(5)
            val longitud = posicion.getDouble(6)
            clientes.add(Cliente(id, nombre, email, telefono, fecha_registro, latitud, longitud))
        }
        posicion.close()
        db.close()
        return clientes
    }
    //Actualizar cliente
    fun actualizarCliente(cliente: Cliente): Boolean {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", cliente.nombre)
            put("email", cliente.email)
            put("telefono", cliente.telefono)
            put("fecha_registro", cliente.fecha_registro)
            put("latitud", cliente.latitud)
            put("longitud", cliente.longitud)
        }
        val rows = db.update(
            "CLIENTE",
            valores,
            "id = ?",
            arrayOf(cliente.id.toString())
        )
        db.close()
        return rows > 0
    }
    //Eliminar cliente
    fun eliminarCliente(clienteId: Int): Boolean {
        val db = dbHelper.writableDatabase
        val filas = db.delete(
            "CLIENTE",
            "id = ?",
               arrayOf(clienteId.toString())
        )
        db.close()
        return filas > 0
    }

    //Crear un pedido
    fun crearPedido(pedido: Pedido) :Boolean{
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("descripcion", pedido.descripcion)
            put("monto", pedido.monto)
            put("cantidad", pedido.cantidad)
            put("cliente_id", pedido.cliente_id)
        }
        val resultado = db.insert("PEDIDO", null, valores)
        db.close()
        return resultado != -1L
    }

    //Listar pedidos de un cliente
    fun listarPedidosPorCliente(clienteId: Int?): ArrayList<Pedido> {
        val db = dbHelper.writableDatabase
        val posicion = db.rawQuery("SELECT * FROM PEDIDO WHERE cliente_id = ?", arrayOf(clienteId.toString()))
        val pedidos = arrayListOf<Pedido>()
        while(posicion.moveToNext()){
            val id = posicion.getInt(0)
            val descripcion = posicion.getString(1)
            val monto = posicion.getDouble(2)
            val cantidad = posicion.getInt(3)
            val cliente_id = posicion.getInt(4)
            pedidos.add(Pedido(id, descripcion, monto, cantidad, cliente_id))
        }
        posicion.close()
        db.close()
        return pedidos
    }
    //Actualizar pedido
    fun actualizarPedido(pedido: Pedido): Boolean {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("descripcion", pedido.descripcion)
            put("monto", pedido.monto)
            put("cantidad", pedido.cantidad)
        }
        val query = db.update(
            "PEDIDO",
            valores,
            "id = ?",
            arrayOf(pedido.id.toString())
        )
        db.close()
        return query > 0
    }
    //Eliminar pedido
    fun eliminarPedido(pedidoId: Int): Boolean {
        val db = dbHelper.writableDatabase
        val filas = db.delete(
            "PEDIDO",
            "id = ?",
            arrayOf(pedidoId.toString())
        )
        db.close()
        return filas > 0
    }
    //Mostrar pedidos
    fun mostrarPedidos(){
        val db = dbHelper.readableDatabase
        val posicion =db.rawQuery("SELECT * FROM PEDIDO", null)
        while(posicion.moveToNext()){
            val id = posicion.getInt(posicion.getColumnIndexOrThrow("id"))
            val descripcion = posicion.getString(posicion.getColumnIndexOrThrow("descripcion"))
            val monto = posicion.getDouble(posicion.getColumnIndexOrThrow("monto"))
            val cantidad = posicion.getInt(posicion.getColumnIndexOrThrow("cantidad"))
            val cliente_id = posicion.getInt(posicion.getColumnIndexOrThrow("cliente_id"))
            println("ID: $id, Descripcion: $descripcion, Monto: $monto, Cantidad: $cantidad, Cliente ID: $cliente_id")
        }
    }

    fun obtenerUltimoPedido(clienteId: Int): Pedido? {
        val db = dbHelper.readableDatabase
        val posicion = db.rawQuery("SELECT * FROM PEDIDO WHERE cliente_id = ? ORDER BY id DESC LIMIT 1", arrayOf(clienteId.toString()))
        var pedido: Pedido? = null

        if (posicion.moveToFirst()) {
            val id = posicion.getInt(0)
            val descripcion = posicion.getString(1)
            val monto = posicion.getDouble(2)
            val cantidad = posicion.getInt(3)
            val clienteIdDesdeBD = posicion.getInt(4)
            pedido = Pedido(id, descripcion, monto, cantidad, clienteIdDesdeBD)
        }

        posicion.close()
        db.close()
        return pedido
    }

    fun obtenerPedido(pedidoSeleccionado: Int): Pedido{
        val db = dbHelper.readableDatabase
        val posicion = db.rawQuery("SELECT * FROM PEDIDO WHERE id = ?", arrayOf(pedidoSeleccionado.toString()))
        var pedido: Pedido? = null
        if(posicion.moveToFirst()){
            val id = posicion.getInt(0)
            val descripcion = posicion.getString(1)
            val monto = posicion.getDouble(2)
            val cantidad = posicion.getInt(3)
            val cliente_id = posicion.getInt(4)
            pedido = Pedido(id, descripcion, monto, cantidad, cliente_id)
        }
        posicion.close()
        db.close()
        return pedido!!
    }
}