import org.example.dao.ClienteDAO;
import org.example.model.Chocolate;
import org.example.model.Cliente;
import org.example.model.Venta;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal del programa de gestión de una tienda de chocolates.
 * Adaptado para usar el patrón DAO con la nueva base de datos.
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ClienteDAO clienteDAO = new ClienteDAOImpl();
    private static ChocolateDAO chocolateDAO = new ChocolateDAOImpl();
    private static VentaDAO ventaDAO = new VentaDAOImpl();

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n*** MENÚ PRINCIPAL ***");
            System.out.println("1. Gestión de Ventas");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Gestión de Productos");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = recibirOpcion(1, 4);

            switch (opcion) {
                case 1 -> gestionarVentas();
                case 2 -> gestionarClientes();
                case 3 -> gestionarProductos();
                case 4 -> System.out.println("¡Gracias por usar la aplicación! Hasta pronto.");
            }
        } while (opcion != 4);
    }

    // --- Gestión de Productos ---
    private static void gestionarProductos() {
        int opcion;
        do {
            System.out.println("\n*** MENÚ DE GESTIÓN DE PRODUCTOS ***");
            System.out.println("1. Alta de nuevo producto");
            System.out.println("2. Recepción de producto (Añadir stock)");
            System.out.println("3. Ver inventario");
            System.out.println("4. Buscar por % de cacao");
            System.out.println("5. Buscar por origen");
            System.out.println("6. Volver");
            System.out.print("Selecciona una opción: ");
            opcion = recibirOpcion(1, 6);

            switch (opcion) {
                case 1 -> {
                    System.out.println("\n--- ALTA DE NUEVO PRODUCTO ---");
                    System.out.print("Introduce el país de origen: ");
                    String origen = scanner.nextLine();
                    System.out.print("Introduce el porcentaje de cacao: ");
                    int porcentaje = Integer.parseInt(scanner.nextLine());
                    System.out.print("Introduce el precio: ");
                    double precio = Double.parseDouble(scanner.nextLine().replace(',', '.'));
                    System.out.print("Introduce la cantidad de unidades a ingresar: ");
                    int unidades = Integer.parseInt(scanner.nextLine());
                    
                    Chocolate nuevo = new Chocolate(origen, porcentaje, precio, unidades);
                    if (chocolateDAO.getChocolateById(nuevo.getIdProducto()) == null) {
                        chocolateDAO.addChocolate(nuevo);
                        System.out.println("Producto dado de alta.");
                    } else {
                        System.out.println("Error: El producto ya existe.");
                    }
                }
                case 2 -> {
                    System.out.print("Introduce el ID del producto para añadir stock: ");
                    String id = scanner.nextLine().toUpperCase();
                    Chocolate choco = chocolateDAO.getChocolateById(id);
                    if (choco != null) {
                        System.out.print("Introduce la cantidad de unidades a añadir: ");
                        int unidades = Integer.parseInt(scanner.nextLine());
                        choco.setStock(choco.getStock() + unidades);
                        chocolateDAO.updateChocolate(choco);
                        System.out.println("Stock actualizado.");
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                }
                case 3 -> {
                    List<Chocolate> lista = chocolateDAO.getAllChocolates();
                    lista.forEach(System.out::println);
                }
                case 4 -> {
                    System.out.print("Introduce el porcentaje mínimo de cacao a buscar: ");
                    int cacao = Integer.parseInt(scanner.nextLine());
                    List<Chocolate> encontrados = chocolateDAO.findByCacaoPercentage(cacao);
                    if(encontrados.isEmpty()) System.out.println("No se encontraron productos.");
                    else encontrados.forEach(System.out::println);
                }
                case 5 -> {
                    System.out.print("Introduce el país de origen a buscar: ");
                    String origenBuscado = scanner.nextLine();
                    List<Chocolate> encontrados = chocolateDAO.findByOrigin(origenBuscado);
                    if(encontrados.isEmpty()) System.out.println("No se encontraron productos.");
                    else encontrados.forEach(System.out::println);
                }
            }
        } while (opcion != 6);
    }

    // --- Gestión de Clientes ---
    private static void gestionarClientes() {
        int opcion;
        do {
            System.out.println("\n*** MENÚ DE GESTIÓN CLIENTES ***");
            System.out.println("1. Alta de clientes");
            System.out.println("2. Baja de clientes");
            System.out.println("3. Modificación");
            System.out.println("4. Buscar por Email");
            System.out.println("5. Lista de clientes");
            System.out.println("6. Volver");
            System.out.print("Selecciona una opción: ");
            opcion = recibirOpcion(1, 6);

            switch (opcion) {
                case 1 -> {
                    System.out.print("Introduce el Email: ");
                    String email = scanner.nextLine();
                    if (clienteDAO.getClienteByEmail(email) == null) {
                        System.out.print("Nombre: "); String nombre = scanner.nextLine();
                        System.out.print("Teléfono: "); String telefono = scanner.nextLine();
                        clienteDAO.addCliente(new Cliente(nombre, email, telefono));
                        System.out.println("Cliente añadido.");
                    } else {
                        System.out.println("Ya existe un cliente con ese Email.");
                    }
                }
                case 2 -> {
                    System.out.print("Introduce el ID del cliente a eliminar: ");
                    int idDel = Integer.parseInt(scanner.nextLine());
                    if (clienteDAO.getClienteById(idDel) != null) {
                        clienteDAO.deleteCliente(idDel);
                        System.out.println("Cliente eliminado.");
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                }
                case 3 -> {
                    System.out.print("Introduce el ID del cliente a modificar: ");
                    int idMod = Integer.parseInt(scanner.nextLine());
                    Cliente cli = clienteDAO.getClienteById(idMod);
                    if (cli != null) {
                        System.out.print("Nuevo nombre (" + cli.getNombre() + "): ");
                        String n = scanner.nextLine(); if(!n.isEmpty()) cli.setNombre(n);
                        System.out.print("Nuevo teléfono (" + cli.getTelefono() + "): ");
                        String t = scanner.nextLine(); if(!t.isEmpty()) cli.setTelefono(t);
                        System.out.print("Nuevo email (" + cli.getEmail() + "): ");
                        String e = scanner.nextLine(); if(!e.isEmpty()) cli.setEmail(e);
                        clienteDAO.updateCliente(cli);
                        System.out.println("Cliente actualizado.");
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                }
                case 4 -> {
                    System.out.print("Introduce el Email del cliente a buscar: ");
                    String emailBuscado = scanner.nextLine();
                    Cliente cliente = clienteDAO.getClienteByEmail(emailBuscado);
                    if (cliente != null) System.out.println(cliente);
                    else System.out.println("Cliente no encontrado.");
                }
                case 5 -> {
                    List<Cliente> clientes = clienteDAO.getAllClientes();
                    if (clientes.isEmpty()) System.out.println("No hay clientes.");
                    else clientes.forEach(System.out::println);
                }
            }
        } while (opcion != 6);
    }

    // --- Gestión de Ventas ---
    private static void gestionarVentas() {
        int opcion;
        do {
            System.out.println("\n*** GESTIÓN DE VENTAS ***");
            System.out.println("1. Realizar venta");
            System.out.println("2. Mostrar todas las ventas");
            System.out.println("3. Gestionar ventas por cliente");
            System.out.println("0. Volver");
            System.out.print("Selecciona una opción: ");
            opcion = recibirOpcion(1, 4);

            switch (opcion) {
                case 1 -> {
                    System.out.print("Introduce el Email del cliente: ");
                    String email = scanner.nextLine();
                    Cliente cliente = clienteDAO.getClientePorEmail(email);
                    if (cliente != null) {
                        List<Chocolate> catalogo = chocolateDAO.getAllChocolates();
                        List<Chocolate> carrito = new ArrayList<>();
                        int opProd;
                        do {
                            System.out.println("\nSeleccione un producto (0 para finalizar):");
                            for (int i = 0; i < catalogo.size(); i++) {
                                System.out.println((i + 1) + ". " + catalogo.get(i));
                            }
                            opProd = recibirOpcion(0, catalogo.size());
                            if (opProd != 0) {
                                Chocolate prod = catalogo.get(opProd - 1);
                                System.out.print("Cantidad (Stock: " + prod.getStock() + "): ");
                                int cant = Integer.parseInt(scanner.nextLine());
                                if (cant <= prod.getStock()) {
                                    for(int i = 0; i < cant; i++) carrito.add(prod);
                                    prod.setStock(prod.getStock() - cant);
                                    chocolateDAO.updateChocolate(prod); // Actualizar stock
                                    System.out.println("Añadido.");
                                } else {
                                    System.out.println("Stock insuficiente.");
                                }
                            }
                        } while (opProd != 0);

                        if (!carrito.isEmpty()) {
                            Venta venta = new Venta(cliente, carrito);
                            ventaDAO.addVenta(venta);
                            System.out.println("Venta realizada con éxito.");
                        }
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                }
                case 2 -> {
                    List<Venta> ventas = ventaDAO.getAllVentas();
                    if (ventas.isEmpty()) System.out.println("No hay ventas.");
                    else ventas.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Introduce el Email del cliente: ");
                    String email = scanner.nextLine();
                    Cliente cliente = clienteDAO.getClienteByEmail(email);
                    if (cliente != null) {
                        List<Venta> ventasCli = ventaDAO.getVentasByCliente(cliente);
                        if(ventasCli.isEmpty()) System.out.println("El cliente no tiene ventas.");
                        else ventasCli.forEach(System.out::println);
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                }
            }
        } while (opcion != 4);
    }

    public static int recibirOpcion(int min, int max) {
        int opcion = 0;
        boolean valida = false;
        while (!valida) {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                if (opcion < min || opcion > max) {
                    System.out.println("Opción fuera de rango.");
                } else {
                    valida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Introduce un número válido.");
                scanner.nextLine();
            }
        }
        return opcion;
    }
}