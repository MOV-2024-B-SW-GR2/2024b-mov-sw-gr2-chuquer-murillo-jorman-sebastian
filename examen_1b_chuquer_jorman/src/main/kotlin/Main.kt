import java.io.*
import java.time.LocalDate
import java.util.*

// Clase Cliente
class Cliente(
    val id: Int,
    var nombre: String,
    var email: String,
    var telefono: String,
    var fechaRegistro: LocalDate,
    var pedidos: ArrayList<Pedido> = ArrayList()
) {
    override fun toString(): String {
        return "$id|$nombre|$email|$telefono|$fechaRegistro"
    }

    fun mostrarPedidosDeCliente() {
        println("Cliente: $nombre tiene ${pedidos.size} pedidos:")
        pedidos.forEach { println(it) }
    }
}

// Clase Pedido
class Pedido(
    val id: Int,
    var descripcion: String,
    var monto: Double,
    var cantidad: Int
) {
    override fun toString(): String {
        return "$id|$descripcion|$monto|$cantidad"
    }
}

class Sistema {
    private val clientes = ArrayList<Cliente>()
    private val pedidos = ArrayList<Pedido>()
    private var ultimoIdCliente = 0
    private var ultimoIdPedido = 0
    private val archivoClientes = "clientes.txt"
    private val archivoPedidos = "pedidos.txt"
    private val archivoRelaciones = "relaciones.txt"

    init {
        inicializarSistema()
    }

    private fun inicializarSistema() {
        if (File(archivoClientes).readText().isBlank() || File(archivoPedidos).readText().isBlank()) {
            println("Archivos vacíos. Generando datos por defecto...")
            generarDatosPorDefecto()
        } else {
            println("Archivos con datos existentes. Cargando...")
            cargarDatos()
        }
    }
    //Por si los archivos están vacíos al inicio
    private fun generarDatosPorDefecto() {
        repeat(5) { i ->
            val cliente = Cliente(
                id = ++ultimoIdCliente,
                nombre = "Cliente $i",
                email = "cliente$i@email.com",
                telefono = "123456789$i",
                fechaRegistro = LocalDate.now()
            )
            clientes.add(cliente)

            repeat(2) { j ->
                val pedido = Pedido(
                    id = ++ultimoIdPedido,
                    descripcion = "Pedido ${i}-${j}",
                    monto = 100.0 * (j + 1),
                    cantidad = j + 1
                )
                cliente.pedidos.add(pedido)
                pedidos.add(pedido)
            }
        }
        guardarDatos()
    }

    private fun cargarDatos() {
        // Cargar pedidos primero
        if (File(archivoPedidos).exists()) {
            File(archivoPedidos).forEachLine { linea ->
                val datos = linea.split("|")
                val pedido = Pedido(
                    datos[0].toInt(),
                    datos[1],
                    datos[2].toDouble(),
                    datos[3].toInt(),
                )
                pedidos.add(pedido)
                if (pedido.id > ultimoIdPedido) ultimoIdPedido = pedido.id
            }
        }

        // Cargar clientes
        if (File(archivoClientes).exists()) {
            File(archivoClientes).forEachLine { linea ->
                val datos = linea.split("|")
                val cliente = Cliente(
                    datos[0].toInt(),
                    datos[1],
                    datos[2],
                    datos[3],
                    LocalDate.parse(datos[4])
                )
                clientes.add(cliente)
                if (cliente.id > ultimoIdCliente) ultimoIdCliente = cliente.id
            }
        }

        // Cargar relaciones
        if (File(archivoRelaciones).exists()) {
            File(archivoRelaciones).forEachLine { linea ->
                val datos = linea.split("|")
                val clienteId = datos[0].toInt()
                val pedidoId = datos[1].toInt()
                val cliente = clientes.find { it.id == clienteId }
                val pedido = pedidos.find { it.id == pedidoId }
                if (cliente != null && pedido != null) {
                    cliente.pedidos.add(pedido)
                }
            }
        }
    }

    private fun guardarDatos() {
        // Guardar pedidos en el archivo de texto con lo que guarda el sistema
        File(archivoPedidos).printWriter().use { out ->
            pedidos.forEach { out.println(it.toString()) }
        }

        // Guardar clientes en el archivo de texto con lo que guarda el sistema
        File(archivoClientes).printWriter().use { out ->
            clientes.forEach { out.println(it.toString()) }
        }

        // Guardar relaciones en el archivo de texto con lo que guarda el sistema
        File(archivoRelaciones).printWriter().use { out ->
            clientes.forEach { cliente ->
                cliente.pedidos.forEach { pedido ->
                    out.println("${cliente.id}|${pedido.id}")
                }
            }
        }
    }

    //Mostrar todos los clientes
    fun mostrarTodosLosClientes() {
        println("Lista de todos los clientes:")
        clientes.forEach { println(it) }
    }

    //Mostrar todos los pedidos
    fun mostrarTodosLosPedidos() {
        println("Lista de todos los pedidos:")
        pedidos.forEach { println(it) }
    }

    // CRUD Cliente
    fun crearCliente(nombre: String, email: String, telefono: String): Int {
        val id = ++ultimoIdCliente
        val cliente = Cliente(id, nombre, email, telefono, LocalDate.now())
        clientes.add(cliente)
        guardarDatos()
        return id
    }

    fun obtenerCliente(id: Int): Cliente? {
        return clientes.find { it.id == id }
    }

    fun actualizarCliente(id: Int, nombre: String, email: String, telefono: String): Boolean {
        val cliente = obtenerCliente(id)
        if (cliente != null) {
            cliente.nombre = nombre
            cliente.email = email
            cliente.telefono = telefono
            guardarDatos()
            return true
        }
        return false
    }

    fun eliminarCliente(id: Int): Boolean {
        val resultado = clientes.removeIf { it.id == id }
        if (resultado) guardarDatos()
        return resultado
    }

    // CRUD Pedido
    fun crearPedido(descripcion: String, monto: Double, cantidad: Int): Int {
        val id = ++ultimoIdPedido
        val pedido = Pedido(id, descripcion, monto, cantidad)
        pedidos.add(pedido)
        guardarDatos()
        return id
    }

    fun obtenerPedido(id: Int): Pedido? {
        return pedidos.find { it.id == id }
    }

    fun actualizarPedido(id: Int, descripcion: String, monto: Double, cantidad: Int): Boolean {
        val pedido = obtenerPedido(id)
        if (pedido != null) {
            pedido.descripcion = descripcion
            pedido.monto = monto
            pedido.cantidad = cantidad

            guardarDatos()
            return true
        }
        return false
    }

    fun eliminarPedido(id: Int): Boolean {
        val resultado = pedidos.removeIf { it.id == id }
        if (resultado) {
            // Eliminar el pedido de todos los clientes
            clientes.forEach { cliente ->
                cliente.pedidos.removeIf { it.id == id }
            }
            guardarDatos()
        }
        return resultado
    }

    // Relación Cliente-Pedido
    fun agregarPedidoACliente(clienteId: Int, pedidoId: Int): Boolean {
        val cliente = obtenerCliente(clienteId)
        val pedido = obtenerPedido(pedidoId)
        if (cliente != null && pedido != null) {
            cliente.pedidos.add(pedido)
            guardarDatos()
            return true
        }
        return false
    }
}

fun main() {
    val sistema = Sistema()
    val scanner = Scanner(System.`in`)

    while (true) {
        println("\n=== SISTEMA CRUD ===")
        println("1. Gestión de Clientes")
        println("2. Gestión de Pedidos")
        println("3. Agregar Pedido a Cliente")
        println("0. Salir")
        print("Seleccione una opción: ")

        when (scanner.nextLine()) {
            "1" -> {
                println("\n=== GESTIÓN DE CLIENTES ===")
                println("1. Crear Cliente")
                println("2. Ver Cliente")
                println("3. Actualizar Cliente")
                println("4. Eliminar Cliente")
                println("5. Ver todos los Clientes")
                print("Seleccione una opción: ")

                when (scanner.nextLine()) {
                    "1" -> {
                        print("Nombre: ")
                        val nombre = scanner.nextLine()
                        print("Email: ")
                        val email = scanner.nextLine()
                        print("Teléfono: ")
                        val telefono = scanner.nextLine()
                        val id = sistema.crearCliente(nombre, email, telefono)
                        println("Cliente creado con ID: $id")
                    }

                    "2" -> {
                        print("ID del cliente: ")
                        val cliente = sistema.obtenerCliente(scanner.nextLine().toInt())
                        if (cliente != null) {
                            cliente.mostrarPedidosDeCliente()
                        } else {
                            println("Cliente no encontrado")
                        }
                    }

                    "3" -> {
                        print("ID del cliente: ")
                        val id = scanner.nextLine().toInt()
                        print("Nuevo nombre: ")
                        val nombre = scanner.nextLine()
                        print("Nuevo email: ")
                        val email = scanner.nextLine()
                        print("Nuevo teléfono: ")
                        val telefono = scanner.nextLine()
                        if (sistema.actualizarCliente(id, nombre, email, telefono)) {
                            println("Cliente actualizado")
                        } else {
                            println("Cliente no encontrado")
                        }
                    }

                    "4" -> {
                        print("ID del cliente: ")
                        if (sistema.eliminarCliente(scanner.nextLine().toInt())) {
                            println("Cliente eliminado")
                        } else {
                            println("Cliente no encontrado")
                        }
                    }

                    "5" -> {
                        sistema.mostrarTodosLosClientes()
                    }
                }
            }

            "2" -> {
                println("\n=== GESTIÓN DE PEDIDOS ===")
                println("1. Crear Pedido")
                println("2. Ver Pedido")
                println("3. Actualizar Pedido")
                println("4. Eliminar Pedido")
                println("5. Ver todos los Pedidos")
                print("Seleccione una opción: ")

                when (scanner.nextLine()) {
                    "1" -> {
                        print("Descripción: ")
                        val descripcion = scanner.nextLine()
                        print("Monto: ")
                        val monto = scanner.nextLine().toDouble()
                        print("Cantidad: ")
                        val cantidad = scanner.nextLine().toInt()
                        val id = sistema.crearPedido(descripcion, monto, cantidad)
                        println("Pedido creado con ID: $id")
                    }

                    "2" -> {
                        print("ID del pedido: ")
                        val pedido = sistema.obtenerPedido(scanner.nextLine().toInt())
                        if (pedido != null) {
                            println("Descripción: ${pedido.descripcion}")
                            println("Monto: ${pedido.monto}")
                            println("Cantidad: ${pedido.cantidad}")
                        } else {
                            println("Pedido no encontrado")
                        }
                    }

                    "3" -> {
                        print("ID del pedido: ")
                        val id = scanner.nextLine().toInt()
                        print("Nueva descripción: ")
                        val descripcion = scanner.nextLine()
                        print("Nuevo monto: ")
                        val monto = scanner.nextLine().toDouble()
                        print("Nueva cantidad: ")
                        val cantidad = scanner.nextLine().toInt()
                        if (sistema.actualizarPedido(id, descripcion, monto, cantidad)) {
                            println("Pedido actualizado")
                        } else {
                            println("Pedido no encontrado")
                        }
                    }

                    "4" -> {
                        print("ID del pedido: ")
                        if (sistema.eliminarPedido(scanner.nextLine().toInt())) {
                            println("Pedido eliminado")
                        } else {
                            println("Pedido no encontrado")
                        }
                    }

                    "5" -> {
                        sistema.mostrarTodosLosPedidos()
                    }
                }
            }

            "3" -> {
                print("ID del cliente: ")
                val clienteId = scanner.nextLine().toInt()
                print("ID del pedido: ")
                val pedidoId = scanner.nextLine().toInt()
                if (sistema.agregarPedidoACliente(clienteId, pedidoId)) {
                    println("Pedido agregado al cliente")

                } else {
                    println("Cliente o pedido no encontrado")
                }
            }

            "0" -> break
            else -> println("Opción no válida")
        }
    }
}