package com.example.app_mov_crud

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class ActivityListarPedidos : AppCompatActivity() {
    private lateinit var listViewPedidos: ListView
    private lateinit var btnAgregarPedido: Button
    private lateinit var controlador: EntityController
    private var pedido_seleccionado: Pedido? = null
    private var cliente_seleccionado: Int? = null
    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun actualizarLista() {
        val pedidos = controlador.listarPedidosPorCliente(cliente_seleccionado!!)
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, pedidos.map { it.descripcion })
        listViewPedidos.adapter = adapter
        listViewPedidos.setOnItemClickListener { _, _, position, _ ->
            pedido_seleccionado = pedidos[position]
        }
    }
        override fun onCreate(savedInstanceState: Bundle?){
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_listar_pedidos)
            controlador = EntityController(this)
            listViewPedidos = findViewById(R.id.listViewPedidos)
            btnAgregarPedido = findViewById(R.id.btn_agregar_pedido)
            cliente_seleccionado = intent.getStringExtra("clienteId")?.toIntOrNull()
            btnAgregarPedido.setOnClickListener {
                val intent = Intent(this, ActivityAgregarEditarPedidos::class.java)
                intent.putExtra("clienteId", cliente_seleccionado.toString())
                startActivity(intent)
            }
            registerForContextMenu(listViewPedidos)
            actualizarLista()
        }
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            super.onCreateContextMenu(menu, v, menuInfo)
            menuInflater.inflate(R.menu.context_menu_pedido, menu)
        }
        override fun onContextItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.context_menu_editar_pedido -> {
                    val intent = Intent(this, ActivityAgregarEditarPedidos::class.java)
                    intent.putExtra("clienteId", cliente_seleccionado.toString())
                    intent.putExtra("pedidoId", pedido_seleccionado?.id.toString())
                    startActivity(intent)
                    true
                }
                R.id.context_menu_eliminar_pedido -> {
                    controlador.eliminarPedido(pedido_seleccionado!!.id)
                    mostrarSnackbar("Pedido eliminado")
                    actualizarLista()
                    true
                }
                else -> super.onContextItemSelected(item)
            }
        }
    }

