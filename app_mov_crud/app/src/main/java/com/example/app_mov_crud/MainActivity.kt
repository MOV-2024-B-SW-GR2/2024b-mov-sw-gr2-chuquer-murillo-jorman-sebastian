package com.example.app_mov_crud

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var listViewClientes: ListView
    private lateinit var btnAgregarCliente: Button
    private lateinit var controlador: EntityController
    private var cliente_seleccionado: Cliente? = null

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun actualizarLista() {
        val clientes = controlador.listarCliente()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, clientes.map { it.nombre })
        listViewClientes.adapter = adapter
        listViewClientes.setOnItemClickListener { _, _, position, _ ->
            cliente_seleccionado = clientes[position]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controlador = EntityController(this)
        listViewClientes = findViewById(R.id.listViewClientes)
        btnAgregarCliente = findViewById(R.id.btn_agregar_cliente)

        btnAgregarCliente.setOnClickListener {
            val intent = Intent(this, ActivityAgregarEditarCliente::class.java)
            startActivity(intent)
        }

        registerForContextMenu(listViewClientes)
        actualizarLista()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_cliente, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as? AdapterView.AdapterContextMenuInfo
        val clienteIndex = info?.position
        cliente_seleccionado = clienteIndex?.let { controlador.listarCliente()[it] }

        when (item.itemId) {
            R.id.context_menu_ver_pedidos -> {
                cliente_seleccionado?.let {
                    val intent = Intent(this, ActivityListarPedidos::class.java)
                    intent.putExtra("clienteId", it.id.toString())
                    startActivity(intent)
                }
            }
            R.id.context_menu_editar_cliente -> {
                cliente_seleccionado?.let {
                    val intent = Intent(this, ActivityAgregarEditarCliente::class.java)
                    intent.putExtra("clienteId", it.id.toString())
                    startActivity(intent)
                }
            }
            R.id.context_menu_eliminar_cliente -> {
                cliente_seleccionado?.let {
                    controlador.eliminarCliente(it.id)
                    mostrarSnackbar("Cliente eliminado")
                    actualizarLista()
                } ?: mostrarSnackbar("Error al eliminar cliente")
            }
            R.id.context_menu_ver_ubicacion ->{
                cliente_seleccionado?.let {
                    val intent = Intent(this, MapsActivity::class.java)
                    intent.putExtra("latitud", it.latitud)
                    intent.putExtra("longitud", it.longitud)
                    intent.putExtra("nombre", it.nombre)
                    startActivity(intent)
                }
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        actualizarLista()
    }
}

