import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase principal del programa de gestión de una tienda de chocolates.
 * Muestra un menú principal y submenús para gestionar clientes, productos y ventas.
 * Usa funciones reutilizables para imprimir menús y recibir opciones validadas.
 *
 * @author
 * @version 1.0
 */
public class Main {

    /**
     * Punto de entrada del programa.
     * Muestra el menú principal y luego muestra un submenú según la opción seleccionada.
     *
     * @param args Argumentos pasados por línea de comandos (no se usan en este programa).
     */
    public static void main(String[] args) {
        // Listas principales para almacenar datos
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        ArrayList<Chocolate> catalogoChocolates = new ArrayList<>();
        ArrayList<Venta> listaVentas = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        // Definición de los menús
        String menuMain = "***MENÚ PRINCIPAL***\n1.Ventas\n2.Gestión de clientes\n3.Gestión de producto\n4.Salir";
        String menuClientes = "***MENÚ DE GESTIÓN CLIENTES***\n1.Alta de clientes\n2.Baja de clientes\n3.Modificación\n4.Buscar por DNI\n5.Lista de clientes\n6.Volver";
        String menuProducto = "***MENÚ DE GESTIÓN DE PRODUCTOS***\n1.Alta de producto\n2.Ver inventario \n3.Buscar por % de cacao\n4.Buscar por origen\n5.Volver";
        String menuVentas = "***MENÚ DE GESTIÓN DE VENTAS***\n1.Nueva venta\n2.Mostrar todas las ventas\n3.Mostrar ventas por cliente\n4.Mostrar una venta\n5.Volver";

        //Variable para recibir opción del usuario
        int opcion;

        // Muestra menú principal
        imprimirMenu(menuMain);

        do {
            opcion = recibirOpcion(scanner, 1, 4);

            switch (opcion) {
                case 1 -> {
                    int opcionVentas;
                    do {
                        imprimirMenu(menuVentas);
                        opcionVentas = recibirOpcion(scanner, 1, 5);
                        switch (opcionVentas) {
                            case 1 -> {
                                // TODO: Implementar nuevaVenta()
                                System.out.println("Has seleccionado Nueva venta.");
                            }
                            case 2 -> {
                                // TODO: Implementar mostrarTodasLasVentas()
                                System.out.println("Has seleccionado Mostrar ventas.");
                            }
                            case 3 -> {
                                // TODO: Implementar mostrarVentasPorCliente()
                                System.out.println("Has seleccionado Mostrar ventas por cliente.");
                            }
                            case 4 -> {
                                // TODO: Implementar mostrarUnaVenta()
                                System.out.println("Has seleccionado Mostrar una venta.");
                            }
                            case 5 -> {
                                System.out.println("Volviendo al menú principal.");
                            }
                        }
                    } while (opcionVentas != 5);
                }
                case 2 -> {
                    int opcionClientes;
                    do {
                        imprimirMenu(menuClientes);
                        opcionClientes = recibirOpcion(scanner, 1, 6);
                        switch (opcionClientes) {
                            case 1 -> {
                                // TODO: Implementar altaCliente()
                                System.out.println("Has seleccionado Dar de alta cliente.");
                            }
                            case 2 -> {
                                // TODO: Implementar bajaCliente()
                                System.out.println("Has seleccionado Dar de baja cliente.");
                            }
                            case 3 -> {
                                // TODO: Implementar modificarCliente()
                                System.out.println("Has seleccionado Modificar cliente.");
                            }
                            case 4 -> {
                                // TODO: Implementar buscarCliente()
                                System.out.println("Has seleccionado Buscar cliente por DNI.");
                            }
                            case 5 -> {
                                // TODO: Implementar listarClientes()
                                System.out.println("Has seleccionado Lista de clientes.");
                            }
                            case 6 -> {
                                System.out.println("Volviendo al menú principal.");
                            }
                        }
                    } while (opcionClientes != 6);
                }
                case 3 -> {
                    int opcionProductos;
                    do {
                        imprimirMenu(menuProducto);
                        opcionProductos = recibirOpcion(scanner, 1, 5);
                        switch (opcionProductos) {
                            case 1 -> {
                                // TODO: Implementar altaProducto()
                                System.out.println("Has seleccionado Dar de alta producto.");
                            }
                            case 2 -> {
                                // TODO: Implementar listarInventario()
                                System.out.println("Has seleccionado Ver inventario.");
                            }
                            case 3 -> {
                                // TODO: Implementar buscarPorPorcentaje()
                                System.out.println("Has seleccionado Buscar por % de cacao.");
                            }
                            case 4 -> {
                                // TODO: Implementar buscarPorOrigen()
                                System.out.println("Has seleccionado Buscar por origen.");
                            }
                            case 5 -> {
                                System.out.println("Volviendo al menú principal.");
                            }
                        }
                    } while (opcionProductos != 5);
                }

                case 4 -> {
                    System.out.println("Saliendo del programa. ¡Hasta pronto!");
                }
            }

            // Mostrar el menú principal nuevamente si no se eligió salir
            if (opcion != 5) {
                imprimirMenu(menuMain);
            }

        } while (opcion != 5);

        scanner.close();
    }

    /**
     * Muestra un menú en consola.
     *
     * @param menu Nombre de la variable con el texto del menú que a imprimir.
     */
    public static void imprimirMenu(String menu) {
        System.out.println(menu);
        System.out.print("Selecciona una opción: ");
    }

    /**
     * Pide número de opción al usuario dentro de un rango válido.
     * Gestiona errores de entrada y repite hasta que reciba un valor válido.
     *
     * @param scanner Objeto Scanner para leer la entrada del usuario.
     * @param min Número mínimo aceptado.
     * @param max Número máximo aceptado.
     * @return Número de opción elegido por el usuario.
     */
    public static int recibirOpcion(Scanner scanner, int min, int max) {
        int opcion = 0;
        boolean valida = false;

        while (!valida) {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                if (opcion < min || opcion > max) {
                    System.out.println("Opción fuera de rango. Intenta de nuevo.");
                    System.out.print("Selecciona una opción: ");
                } else {
                    valida = true;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, introduce un número.");
                scanner.nextLine(); // Limpiar buffer
                System.out.print("Selecciona una opción: ");
            }
        }

        return opcion;
    }
}
