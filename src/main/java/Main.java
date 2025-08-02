import java.util.ArrayList;
import java.util.Scanner;

/**
 * La clase principal `Main` contiene el punto de entrada de la aplicación.
 * Desde aquí se gestionan los menús principales y secundarios para interactuar con
 * las funcionalidades de la tienda de chocolate.
 *
 * @author Pablo Andrés Moncayo Vega
 * @version 1.0
 */
public class Main {

    /**
     * Este es el método principal del programa. Desde aquí se ejecuta
     * toda la lógica de la aplicación.
     *
     * @param args Argumentos de la línea de comandos (no se usan en este programa).
     */
    public static void main(String[] args) {
        // Declaración de los ArrayLists para almacenar los objetos
        // Aquí es donde guardas todos los clientes, chocolates y ventas.
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        ArrayList<Chocolate> catalogoChocolates = new ArrayList<>();
        ArrayList<Venta> listaVentas = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        // Variables String para los menús.
        // Aquí se definen los textos de los menús que se mostrarán al usuario.
        String menuMain = "***MENÚ PRINCIPAL***\n1.Gestión de clientes\n2.Gestión de productos\n3.Realizar venta\n4.Mostrar ventas\n5.Salir";
        String menuClientes = "***MENÚ DE GESTIÓN CLIENTES***\n1.Alta de clientes\n2.Baja de clientes\n3.Modificación\n4.Buscar por DNI\n5.Lista de clientes\n6.Volver";
        String menuProducto = "***MENÚ DE GESTIÓN DE PRODUCTOS***\n1.Alta de producto\n2.Ver inventario \n3.Buscar por % de cacao\n4.Buscar por origen\n5.Volver";
        String menuVentas = "***MENÚ DE GESTIÓN DE VENTAS***\n1.Nueva venta\n2.Mostrar ventas\n3.Mostrar ventas por cliente\n4.Mostrar una venta\n5.Volver";

        int opcion;

        do {
            opcion = 0; // Se inicializa para que el bucle se ejecute al menos una vez
            System.out.println(menuMain);
            System.out.print("Selecciona una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer del scanner
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Opción fuera de rango. Por favor, intenta de nuevo.");
                    opcion = 0; // Se resetea para que el bucle se repita
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, introduce un número.");
                scanner.nextLine();
            }

            switch (opcion) {
                case 1 -> {
                    // Entra en el submenú de Gestión de clientes
                    int opcionClientes;
                    do {
                        opcionClientes = 0;
                        System.out.println(menuClientes);
                        System.out.print("Selecciona una opción: ");
                        try {
                            opcionClientes = scanner.nextInt();
                            scanner.nextLine();
                            if (opcionClientes < 1 || opcionClientes > 6) {
                                System.out.println("Opción fuera de rango. Por favor, intenta de nuevo.");
                                opcionClientes = 0;
                            }
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("Entrada inválida. Por favor, introduce un número.");
                            scanner.nextLine();
                        }

                        switch (opcionClientes) {
                            case 1 -> {
                                // TODO: llamar a la función altaCliente();
                                System.out.println("Has seleccionado Dar de alta cliente.");
                            }
                            case 2 -> {
                                // TODO: llamar a la función bajaCliente();
                                System.out.println("Has seleccionado Dar de baja cliente.");
                            }
                            case 3 -> {
                                // TODO: llamar a la función modificarCliente();
                                System.out.println("Has seleccionado Modificar cliente.");
                            }
                            case 4 -> {
                                // TODO: llamar a la función buscarCliente();
                                System.out.println("Has seleccionado Buscar cliente por DNI.");
                            }
                            case 5 -> {
                                // TODO: llamar a la función listarClientes();
                                System.out.println("Has seleccionado Lista de clientes.");
                            }
                            case 6 -> {
                                System.out.println("Volviendo al menú principal.");
                            }
                        }
                    } while (opcionClientes != 6);
                }
                case 2 -> {
                    // Aquí entras en el submenú de Gestión de chocolates
                    int opcionProductos;
                    do {
                        opcionProductos = 0;
                        System.out.println(menuProducto);
                        System.out.print("Selecciona una opción: ");
                        try {
                            opcionProductos = scanner.nextInt();
                            scanner.nextLine();
                            if (opcionProductos < 1 || opcionProductos > 5) {
                                System.out.println("Opción fuera de rango. Por favor, intenta de nuevo.");
                                opcionProductos = 0;
                            }
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("Entrada inválida. Por favor, introduce un número.");
                            scanner.nextLine();
                        }

                        switch (opcionProductos) {
                            case 1 -> {
                                // TODO: llamar a la función altaChocolate();
                                System.out.println("Has seleccionado Dar de alta chocolate.");
                            }
                            case 2 -> {
                                // TODO: llamar a la función listarCatalogo();
                                System.out.println("Has seleccionado Listar catálogo.");
                            }
                            case 3 -> {
                                // TODO: llamar a la función buscarChocolatePorPorcentaje();
                                System.out.println("Has seleccionado Buscar por % de cacao.");
                            }
                            case 4 -> {
                                // TODO: llamar a la función buscarChocolatePorOrigen();
                                System.out.println("Has seleccionado Buscar por origen.");
                            }
                            case 5 -> {
                                System.out.println("Volviendo al menú principal.");
                            }
                        }
                    } while (opcionProductos != 5);
                }
                case 3 -> {
                    // Lógica para realizar una venta (opción del menú principal)
                    System.out.println("\n--- REALIZAR VENTA ---");
                    // TODO: llamar a la función realizarVenta();
                }
                case 4 -> {
                    // Aquí entras en el submenú de Mostrar ventas
                    int opcionVentas;
                    do {
                        opcionVentas = 0;
                        System.out.println(menuVentas);
                        System.out.print("Selecciona una opción: ");
                        try {
                            opcionVentas = scanner.nextInt();
                            scanner.nextLine();
                            if (opcionVentas < 1 || opcionVentas > 5) {
                                System.out.println("Opción fuera de rango. Por favor, intenta de nuevo.");
                                opcionVentas = 0;
                            }
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("Entrada inválida. Por favor, introduce un número.");
                            scanner.nextLine();
                        }
                        switch (opcionVentas) {
                            case 1 -> {
                                // TODO: llamar a la función nuevaVenta();
                                System.out.println("Has seleccionado Nueva venta.");
                            }
                            case 2 -> {
                                // TODO: llamar a la función mostrarTodasLasVentas();
                                System.out.println("Has seleccionado Mostrar ventas.");
                            }
                            case 3 -> {
                                // TODO: llamar a la función mostrarVentasPorCliente();
                                System.out.println("Has seleccionado Mostrar ventas por cliente.");
                            }
                            case 4 -> {
                                // TODO: llamar a la función mostrarUnaVenta();
                                System.out.println("Has seleccionado Mostrar una venta.");
                            }
                            case 5 -> {
                                System.out.println("Volviendo al menú principal.");
                            }
                        }
                    } while (opcionVentas != 5);
                }
                case 5 -> {
                    System.out.println("Saliendo del programa. ¡Hasta pronto!");
                }
            }
        } while (opcion != 5);

        scanner.close();
    }
}
