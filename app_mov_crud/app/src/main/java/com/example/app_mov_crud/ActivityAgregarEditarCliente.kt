package com.example.app_mov_crud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class ActivityAgregarEditarCliente : AppCompatActivity() {
    private lateinit var controlador: EntityController
    private var cliente_seleccionado: Int? = null

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editar_cliente)
        controlador = EntityController(this)

        val nombre = findViewById<EditText>(R.id.nombre_in)
        val email = findViewById<EditText>(R.id.email_in)
        val telefono = findViewById<EditText>(R.id.telefono_in)
        val fecha_registro = findViewById<EditText>(R.id.fecha_registro_in)
        val btnguardarCliente = findViewById<Button>(R.id.btn_guardar_cliente)
        val latitud = findViewById<EditText>(R.id.etLatitud)
        val longitud = findViewById<EditText>(R.id.etLongitud)

        cliente_seleccionado = intent.getStringExtra("clienteId")?.toIntOrNull()
        cliente_seleccionado?.let { id ->
            val cliente = controlador.listarCliente().find { it.id == id }
            cliente?.let {
                nombre.setText(it.nombre)
                email.setText(it.email)
                telefono.setText(it.telefono)
                fecha_registro.setText(it.fecha_registro)
                latitud.setText(it.latitud.toString())
                longitud.setText(it.longitud.toString())
            }
        }

        btnguardarCliente.setOnClickListener {
            val nombreText = nombre.text.toString()
            val emailText = email.text.toString()
            val telefonoText = telefono.text.toString()
            val fechaRegistroText = fecha_registro.text.toString()
            val latitudText = latitud.text.toString().toDoubleOrNull() ?: 0.0
            val longitudText = longitud.text.toString().toDoubleOrNull() ?: 0.0

            if (nombreText.isNotBlank() && emailText.isNotBlank() && telefonoText.isNotBlank() && fechaRegistroText.isNotBlank()) {
                if (cliente_seleccionado != null) {
                    controlador.actualizarCliente(
                        Cliente(cliente_seleccionado!!, nombreText, emailText, telefonoText, fechaRegistroText, latitudText, longitudText)
                    )
                    mostrarSnackbar("Cliente actualizado")
                } else {
                    controlador.crearCliente(
                        Cliente(nombreText, emailText, telefonoText, fechaRegistroText, latitudText, longitudText)
                    )
                    mostrarSnackbar("Cliente creado")
                }
                finish()
            } else {
                mostrarSnackbar("Todos los campos son obligatorios")
            }
        }
    }
}
