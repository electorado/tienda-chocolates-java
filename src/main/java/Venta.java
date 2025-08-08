import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que representa una venta en la tienda de chocolate.
 * Cada venta tiene un ID único, un cliente asociado, una lista de productos vendidos y una fecha.
 * Utiliza métodos de ayuda de la clase Main para la interacción con el usuario.
 *
 * @author Pablo Andrés Moncayo Vega
 * @version 1.0
 */
public class Venta {

    private static int proximoId = 1; // Contador para generar IDs únicos
    private int idVenta;
    private Cliente cliente;
    private ArrayList<Chocolate> chocolatesVendidos;
    private LocalDate fechaVenta;


    private static final String menuVentas = "*** GESTIÓN DE VENTAS ***\n" +
            "1. Realizar venta\n" +
            "2. Mostrar todas las ventas (General)\n" +
            "3. Gestionar ventas por cliente\n" +
            "4. Volver";

    private static final String menuVentasCliente = "*** VENTAS POR CLIENTE ***\n" +
            "1. Ver todas las ventas del cliente\n" +
            "2. Buscar venta por fecha\n" +
            "3. Mostrar importe total de ventas del cliente\n" +
            "4. Volver";

    /**
     * Constructor para una venta nueva.
     * Asigna un ID autoincremental y la fecha actual a la transacción.
     *
     * @param cliente El cliente que realiza la compra.
     * @param chocolatesVendidos La lista de chocolates que se han vendido.
     */
    public Venta(Cliente cliente, ArrayList<Chocolate> chocolatesVendidos) {
        this.idVenta = proximoId++;
        this.cliente = cliente;
        this.chocolatesVendidos = chocolatesVendidos;
        this.fechaVenta = LocalDate.now();
    }

    // --- Getters (no hay setters para mantener la inmutabilidad) ---
    public int getIdVenta() { return idVenta; }
    public Cliente getCliente() { return cliente; }
    public ArrayList<Chocolate> getChocolatesVendidos() { return chocolatesVendidos; }
    public LocalDate getFechaVenta() { return fechaVenta; }

    /**
     * Calcula y devuelve el importe total de la venta sumando el precio de todos los chocolates vendidos.
     *
     * @return El coste total de la venta.
     */
    public double getImporteTotal() {
        double total = 0;
        for (Chocolate choco : this.chocolatesVendidos) {
            total += choco.getPrecio();
        }
        return total;
    }

    /**
     * Devuelve una representación detallada de la venta en formato de texto.
     *
     * @return Una cadena formateada con todos los detalles de la venta.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String ticket = "--- Venta #" + idVenta + " --- [" + fechaVenta.format(formatter) + "]\n";
        ticket += "Cliente: " + cliente.getNombre() + " " + cliente.getApellidos() + " (DNI: " + cliente.getDni() + ")\n";
        ticket += "Productos:\n";
        for (Chocolate choco : chocolatesVendidos) {
            ticket += "  - ID: " + choco.getIdProducto() + " | Origen: " + choco.getOrigen() + " | Precio: " + String.format("%.2f", choco.getPrecio()) + "€\n";
        }
        ticket += "IMPORTE TOTAL: " + String.format("%.2f", getImporteTotal()) + "€\n";
        return ticket;
    }

    // --- Funciones estáticas para gestionar las ventas ---

    /**
     * Muestra el menú de gestión de ventas y dirige a la función correspondiente.
     *
     * @param listaVentas El historial completo de ventas.
     * @param listaClientes La lista de clientes registrados.
     * @param catalogoChocolates El inventario de productos.
     * @param scanner El objeto Scanner para la entrada del usuario.
     */
    public static void gestionarVentas(ArrayList<Venta> listaVentas, ArrayList<Cliente> listaClientes, ArrayList<Chocolate> catalogoChocolates, Scanner scanner) {
        int opcion;
        do {
            Main.imprimirMenu(menuVentas);
            opcion = Main.recibirOpcion(1, 4);
            switch (opcion) {
                case 1 -> crearNuevaVenta(listaVentas, listaClientes, catalogoChocolates, scanner);
                case 2 -> mostrarTodasLasVentas(listaVentas);
                case 3 -> gestionarVentasCliente(listaVentas, listaClientes, scanner);
                case 4 -> System.out.println("Volviendo al menú principal.");
            }
        } while (opcion != 4);
    }

    /**
     * Guía al usuario a través del proceso de creación de una nueva venta.
     */
    public static void crearNuevaVenta(ArrayList<Venta> listaVentas, ArrayList<Cliente> listaClientes, ArrayList<Chocolate> catalogoChocolates, Scanner scanner) {
        System.out.println("\n--- REALIZAR VENTA ---");

        if (listaClientes.isEmpty()) {
            System.out.println("Error: No hay clientes registrados. Cree un cliente primero.");
            return;
        }
        if (catalogoChocolates.isEmpty()) {
            System.out.println("Error: No hay productos en el catálogo. Añada productos primero.");
            return;
        }

        System.out.println("Seleccione un cliente:");
        for (int i = 0; i < listaClientes.size(); i++) {
            System.out.println((i + 1) + ". " + listaClientes.get(i).getNombre() + " " + listaClientes.get(i).getApellidos());
        }
        int opcionCliente = Main.recibirOpcion(1, listaClientes.size()) - 1;
        Cliente clienteSeleccionado = listaClientes.get(opcionCliente);

        ArrayList<Chocolate> carrito = new ArrayList<>();
        int opcionProducto;
        do {
            System.out.println("\nSeleccione un producto para añadir (0 para finalizar):");
            for (int i = 0; i < catalogoChocolates.size(); i++) {
                Chocolate choco = catalogoChocolates.get(i);
                System.out.println((i + 1) + ". " + choco.toString());
            }
            opcionProducto = Main.recibirOpcion(0, catalogoChocolates.size());

            if (opcionProducto != 0) {
                Chocolate productoSeleccionado = catalogoChocolates.get(opcionProducto - 1);
                if (productoSeleccionado.getStock() > 0) {
                    carrito.add(productoSeleccionado);
                    productoSeleccionado.setStock(productoSeleccionado.getStock() - 1); // Disminuir stock
                    System.out.println("Producto añadido al carrito. Stock restante: " + productoSeleccionado.getStock());
                } else {
                    System.out.println("Error: Producto sin stock.");
                }
            }
        } while (opcionProducto != 0);

        if (carrito.isEmpty()) {
            System.out.println("Venta cancelada. No se añadieron productos.");
        } else {
            Venta nuevaVenta = new Venta(clienteSeleccionado, carrito);
            listaVentas.add(nuevaVenta);
            System.out.println("\n¡Venta creada con éxito!");
            System.out.println(nuevaVenta);
        }
    }

    /**
     * Muestra un listado de todas las ventas realizadas y el importe total de todas ellas.
     */
    public static void mostrarTodasLasVentas(ArrayList<Venta> listaVentas) {
        System.out.println("\n--- HISTORIAL GENERAL DE VENTAS ---");
        if (listaVentas.isEmpty()) {
            System.out.println("No se ha realizado ninguna venta.");
            return;
        }

        double granTotal = 0;
        for (Venta venta : listaVentas) {
            System.out.println(venta);
            granTotal += venta.getImporteTotal();
        }
        System.out.println("-----------------------------------------");
        System.out.println("IMPORTE TOTAL DE TODAS LAS VENTAS: " + String.format("%.2f", granTotal) + "€");
        System.out.println("-----------------------------------------");
    }

    /**
     * Gestiona el submenú de operaciones para un cliente específico.
     */
    private static void gestionarVentasCliente(ArrayList<Venta> listaVentas, ArrayList<Cliente> listaClientes, Scanner scanner) {
        System.out.println("\n--- GESTIONAR VENTAS POR CLIENTE ---");
        if (listaClientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("Seleccione un cliente:");
        for (int i = 0; i < listaClientes.size(); i++) {
            System.out.println((i + 1) + ". " + listaClientes.get(i).getNombre() + " " + listaClientes.get(i).getApellidos());
        }
        int opcionCliente = Main.recibirOpcion(1, listaClientes.size()) - 1;
        Cliente clienteSeleccionado = listaClientes.get(opcionCliente);

        int opcion;
        do {
            System.out.println("\nOperaciones para: " + clienteSeleccionado.getNombre());
            Main.imprimirMenu(menuVentasCliente);
            opcion = Main.recibirOpcion(1, 4);
            switch (opcion) {
                case 1 -> listarVentasDeCliente(listaVentas, clienteSeleccionado);
                case 2 -> buscarVentaPorFecha(listaVentas, clienteSeleccionado, scanner);
                case 3 -> calcularYMostrarTotalCliente(listaVentas, clienteSeleccionado);
                case 4 -> System.out.println("Volviendo al menú de ventas.");
            }
        } while (opcion != 4);
    }

    /**
     * Lista todas las ventas de un cliente específico.
     */
    private static void listarVentasDeCliente(ArrayList<Venta> listaVentas, Cliente clienteSeleccionado) {
        System.out.println("\n--- Ventas para el cliente: " + clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellidos() + " ---");
        boolean encontradas = false;
        for (Venta venta : listaVentas) {
            if (venta.getCliente().equals(clienteSeleccionado)) {
                System.out.println(venta);
                encontradas = true;
            }
        }
        if (!encontradas) {
            System.out.println("Este cliente no tiene ventas registradas.");
        }
    }

    /**
     * Busca ventas de un cliente en una fecha específica.
     */
    private static void buscarVentaPorFecha(ArrayList<Venta> listaVentas, Cliente clienteSeleccionado, Scanner scanner) {
        System.out.println("\n--- Buscar venta por fecha ---");
        System.out.print("Introduce la fecha a buscar (formato dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaBuscada = LocalDate.parse(fechaStr, formatter);

            System.out.println("\nVentas encontradas en la fecha " + fechaStr + ":");
            boolean encontradas = false;
            for (Venta venta : listaVentas) {
                if (venta.getCliente().equals(clienteSeleccionado) && venta.getFechaVenta().equals(fechaBuscada)) {
                    System.out.println(venta);
                    encontradas = true;
                }
            }
            if (!encontradas) {
                System.out.println("No se encontraron ventas para este cliente en la fecha especificada.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha incorrecto. Use dd/MM/yyyy.");
        }
    }

    /**
     * Calcula y muestra el gasto total de un cliente.
     */
    private static void calcularYMostrarTotalCliente(ArrayList<Venta> listaVentas, Cliente clienteSeleccionado) {
        double totalGastado = 0;
        for (Venta venta : listaVentas) {
            if (venta.getCliente().equals(clienteSeleccionado)) {
                totalGastado += venta.getImporteTotal();
            }
        }
        System.out.println("\nEl cliente " + clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellidos() + " ha gastado un total de: " + String.format("%.2f", totalGastado) + "€");
    }
}

