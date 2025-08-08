import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que representa a una venta en la tienda.
 * Cada venta tiene un ID único, un cliente asociado, una lista de productos vendidos y una fecha.
 * Utiliza funciones de la clase Main para la interacción con el usuario.
 *
 * @author Pablo Andrés Moncayo Vega
 * @version 2.0 (Mejorado el flujo de ventas por cliente: no se selecciona por lista, sino por DNI)
 */
public class Venta {

    private static int proximoId = 1;
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

    // --- Getters ---
    //No pongo setters: por principio los datos de una venta no deberían poder modificarse
    public int getIdVenta() { return idVenta; }
    public Cliente getCliente() { return cliente; }
    public ArrayList<Chocolate> getChocolatesVendidos() { return chocolatesVendidos; }
    public LocalDate getFechaVenta() { return fechaVenta; }

    /**
     * Calcula y devuelve el importe total de la venta.
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

    // --- Funciones para la gestion de ventas ---

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
     * Permite identificar al cliente por DNI o crear uno nuevo.
     */
    public static void crearNuevaVenta(ArrayList<Venta> listaVentas, ArrayList<Cliente> listaClientes, ArrayList<Chocolate> catalogoChocolates, Scanner scanner) {
        System.out.println("\n--- REALIZAR VENTA ---");

        if (catalogoChocolates.isEmpty()) {
            System.out.println("Error: No hay productos en el catálogo. Añada productos primero.");
            return;
        }

        String menuSeleccionCliente = "Seleccione una opción:\n1. Introducir DNI de cliente existente\n2. Registrar cliente nuevo\n3. Volver";

        boolean salir = false;
        while (!salir) {
            Main.imprimirMenu(menuSeleccionCliente);
            int opcion = Main.recibirOpcion(1, 3);

            switch (opcion) {
                case 1 -> {
                    System.out.print("Introduce el DNI del cliente: ");
                    String dni = scanner.nextLine();
                    Cliente clienteParaVenta = Cliente.encontrarClientePorDni(listaClientes, dni);

                    if (clienteParaVenta != null) {
                        // Si se encuentra el cliente, se inicia el proceso de compra
                        procesoDeCompra(listaVentas, clienteParaVenta, catalogoChocolates, scanner);
                        salir = true; // La venta ha terminado (o se ha cancelado), salimos del bucle
                    } else {
                        System.out.println("Error: No se encontró ningún cliente con ese DNI.");
                    }
                }
                case 2 -> {
                    // Llama al alta de cliente y luego informa al usuario
                    Cliente.altaCliente(listaClientes, scanner);
                    System.out.println("\nCliente registrado. Ahora puede iniciar la venta seleccionando la opción 1.");
                }
                case 3 -> {
                    System.out.println("Volviendo al menú de ventas.");
                    salir = true; // Salimos del bucle y del método
                }
            }
        }
    }

    /**
     * Función que gestiona la selección de productos y finaliza la venta
     * una vez que el cliente ha sido identificado.
     */
    private static void procesoDeCompra(ArrayList<Venta> listaVentas, Cliente cliente, ArrayList<Chocolate> catalogoChocolates, Scanner scanner) {
        System.out.println("\nIniciando venta para el cliente: " + cliente.getNombre() + " " + cliente.getApellidos());

        ArrayList<Chocolate> carrito = new ArrayList<>();
        int opcionProducto;
        do {
            System.out.println("\nSeleccione un producto para añadir al carrito (0 para finalizar):");
            for (int i = 0; i < catalogoChocolates.size(); i++) {
                Chocolate choco = catalogoChocolates.get(i);
                System.out.println((i + 1) + ". " + choco.toString());
            }
            opcionProducto = Main.recibirOpcion(0, catalogoChocolates.size());

            if (opcionProducto != 0) {
                Chocolate productoSeleccionado = catalogoChocolates.get(opcionProducto - 1);

                System.out.print("Introduce la cantidad (Stock disponible: " + productoSeleccionado.getStock() + "): ");
                int cantidad = Main.recibirOpcion(1, Integer.MAX_VALUE); // Pide una cantidad (mínimo 1)

                if (cantidad <= productoSeleccionado.getStock()) {
                    for (int i = 0; i < cantidad; i++) {
                        carrito.add(productoSeleccionado);
                    }
                    productoSeleccionado.setStock(productoSeleccionado.getStock() - cantidad);
                    System.out.println(cantidad + " unidad(es) del producto añadidas. Stock restante: " + productoSeleccionado.getStock());
                } else {
                    System.out.println("Error: No hay suficiente stock. Solo quedan " + productoSeleccionado.getStock() + " unidades.");
                }
            }
        } while (opcionProducto != 0);

        if (carrito.isEmpty()) {
            System.out.println("Venta cancelada. No se añadieron productos.");
        } else {
            Venta nuevaVenta = new Venta(cliente, carrito);
            listaVentas.add(nuevaVenta);
            System.out.println("\n¡Venta creada con éxito!");
            System.out.println(nuevaVenta);
        }
    }

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
        System.out.println("-------------------------------------");
        System.out.println("IMPORTE TOTAL DE TODAS LAS VENTAS: " + String.format("%.2f", granTotal) + "€");
        System.out.println("-------------------------------------");
    }

    /**
     * Gestiona las operaciones de ventas para un cliente específico, buscado por DNI.
     */
    private static void gestionarVentasCliente(ArrayList<Venta> listaVentas, ArrayList<Cliente> listaClientes, Scanner scanner) {
        System.out.println("\n--- GESTIONAR VENTAS POR CLIENTE ---");
        if (listaClientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        // CAMBIO: Pide el DNI en lugar de listar los clientes
        System.out.print("Introduce el DNI del cliente a consultar: ");
        String dni = scanner.nextLine();
        Cliente clienteSeleccionado = Cliente.encontrarClientePorDni(listaClientes, dni);

        // Si el cliente no se encuentra, muestra un error y vuelve.
        if (clienteSeleccionado == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        // Si el cliente se encuentra, muestra el submenú de opciones.
        int opcion;
        do {
            System.out.println("\nOperaciones para: " + clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellidos());
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

    private static void calcularYMostrarTotalCliente(ArrayList<Venta> listaVentas, Cliente clienteSeleccionado) {
        double totalGastado = 0;
        for (Venta venta : listaVentas) {
            if (venta.getCliente().equals(clienteSeleccionado)) {
                totalGastado += venta.getImporteTotal();
            }
        }
        System.out.println("\nEl cliente " + clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellidos() + " ha hecho compras por un total de: " + String.format("%.2f", totalGastado) + "€");
    }
}
