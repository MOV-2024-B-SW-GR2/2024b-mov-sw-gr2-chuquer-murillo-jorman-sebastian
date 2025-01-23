package com.example.app_mov_crud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class ActivityAgregarEditarPedidos : AppCompatActivity() {
    private lateinit var btnAgregarPedido: Button
    private lateinit var controlador: EntityController
    private var pedido_seleccionado: Int? = null
    private var cliente_seleccionado: Int? = null

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editar_pedidos)
        controlador = EntityController(this)
        btnAgregarPedido = findViewById(R.id.btn_guardar_pedido)
        val descripcion = findViewById<EditText>(R.id.descripcion_in)
        val cantidad = findViewById<EditText>(R.id.cantidad_in)
        val monto = findViewById<EditText>(R.id.monto_in)
        cliente_seleccionado = intent.getStringExtra("clienteId")?.toIntOrNull()
        pedido_seleccionado = intent.getStringExtra("pedidoId")?.toIntOrNull()
        pedido_seleccionado?.let{ id ->
            val pedido = controlador.listarPedidosPorCliente(cliente_seleccionado!!).find { it.id == pedido_seleccionado }
            pedido?.let{
                descripcion.setText(it.descripcion.toString())
                cantidad.setText(it.cantidad.toString())
                monto.setText(it.monto.toString())
            }

        }
        btnAgregarPedido.setOnClickListener {
            val descripcion = descripcion.text.toString()
            val cantidad = cantidad.text.toString().toIntOrNull()
            val monto = monto.text.toString().toDoubleOrNull()
            if(descripcion.isNotBlank() && cantidad != null && monto != null){
                if(pedido_seleccionado != null){
                    controlador.actualizarPedido(
                        Pedido(pedido_seleccionado!!,descripcion, monto, cantidad, cliente_seleccionado!!)
                    )
                    mostrarSnackbar("Pedido actualizado")
                }else{

                    controlador.crearPedido(
                        Pedido(descripcion, monto, cantidad, cliente_seleccionado!!)
                    )
                    mostrarSnackbar("Pedido creado")
                }
                finish()
            }
        }
    }
}
