import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal del programa de gestión de una tienda de chocolates.
 * Contiene funciones reutilizables y el punto de entrada de la aplicación.
 *
 * @author Pablo Andrés Moncayo Vega
 * @version 5.0 (Incluye función para ordenar listas de clientes, productos y ventas.)
 */
public class Main {

    // --- Listas principales de la aplicación ---
    private static ArrayList<Cliente> listaClientes = new ArrayList<>();
    private static ArrayList<Chocolate> catalogoChocolates = new ArrayList<>();
    private static ArrayList<Venta> listaVentas = new ArrayList<>();

    // --- Objeto Scanner global ---
    private static Scanner scanner = new Scanner(System.in);

    // --- Menús de la aplicación ---
    private static String menuMain = "*** MENÚ PRINCIPAL ***\n" +
            "1. Gestión de Ventas\n2. Gestión de Clientes\n3. Gestión de Productos\n4. Ordenar y Listar\n5. Salir";

    private static String menuOrdenar = "--- ORDENAR LISTAS ---\n1. Ordenar Clientes\n2. Ordenar Productos\n3. Volver";
    private static String menuOrdenarClientes = "Ordenar clientes por:\n1. DNI\n2. Nombre\n3. Apellidos";
    private static String menuOrdenarProductos = "Ordenar productos por:\n1. ID\n2. Origen\n3. Precio\n4. Stock";


    /**
     * Punto de inicio del programa.
     * Carga los datos de prueba y muestra el menú principal.
     */
    public static void main(String[] args) {
        cargarDatosDePrueba();

        int opcion;
        do {
            imprimirMenu(menuMain);
            opcion = recibirOpcion(1, 5);

            switch (opcion) {
                case 1 -> Venta.gestionarVentas(listaVentas, listaClientes, catalogoChocolates, scanner);
                case 2 -> Cliente.gestionarClientes(listaClientes, scanner);
                case 3 -> Chocolate.gestionarProductos(catalogoChocolates, scanner);
                case 4 -> ordenarListas();
                case 5 -> System.out.println("¡Gracias por usar la aplicación! Hasta pronto.");
            }
        } while (opcion != 5);

        scanner.close();
    }

    /**
     * Muestra un submenú para elegir qué lista ordenar y por qué campo.
     */
    public static void ordenarListas() {
        int opcion;
        do {
            imprimirMenu(menuOrdenar);
            opcion = recibirOpcion(1, 3);
            switch (opcion) {
                case 1 -> {
                    imprimirMenu(menuOrdenarClientes);
                    int opcionCliente = recibirOpcion(1, 3);
                    switch (opcionCliente) {
                        case 1 -> listaClientes.sort(Comparator.comparing(Cliente::getDni));
                        case 2 -> listaClientes.sort(Comparator.comparing(Cliente::getNombre));
                        case 3 -> listaClientes.sort(Comparator.comparing(Cliente::getApellidos));
                    }
                    System.out.println("--- Clientes Ordenados ---");
                    Cliente.listarClientes(listaClientes);
                }
                case 2 -> {
                    imprimirMenu(menuOrdenarProductos);
                    int opcionProducto = recibirOpcion(1, 4);
                    switch (opcionProducto) {
                        case 1 -> catalogoChocolates.sort(Comparator.comparing(Chocolate::getIdProducto));
                        case 2 -> catalogoChocolates.sort(Comparator.comparing(Chocolate::getOrigen));
                        case 3 -> catalogoChocolates.sort(Comparator.comparing(Chocolate::getPrecio));
                        case 4 -> catalogoChocolates.sort(Comparator.comparing(Chocolate::getStock).reversed());
                    }
                    System.out.println("--- Productos Ordenados ---");
                    Chocolate.verInventario(catalogoChocolates);
                }
                case 3 -> System.out.println("Volviendo al menú principal.");
            }
        } while (opcion != 3);
    }

    /**
     * Carga datos de prueba para que la aplicación no empiece vacía.
     */
    public static void cargarDatosDePrueba() {
        listaClientes.add(new Cliente("1111111A", "Claudia", "García", "611111111", "cg@suemail.com"));
        listaClientes.add(new Cliente("3333333C", "Sofía", "Terán", "633333333", "st@suemail.com"));
        listaClientes.add(new Cliente("2222222B", "Santiago", "Sanchez", "622222222", "ss@suemail.com"));

        catalogoChocolates.add(new Chocolate("Ecuador", 75, 5.50, 20));
        catalogoChocolates.add(new Chocolate("Costa Rica", 85, 6.20, 15));
        catalogoChocolates.add(new Chocolate("Ghana", 70, 5.00, 30));

        System.out.println(">>> Datos de prueba cargados: 3 clientes y 3 productos disponibles. <<<");
    }

    // --- Funciones ---

    /**
     * Muestra un menú en consola y pide una opción al usuario.
     * @param menu Nombre del menú a imprimir.
     */
    public static void imprimirMenu(String menu) {
        System.out.println("\n" + menu);
        System.out.print("Selecciona una opción: ");
    }

    /**
     * Pide al usuario un número de opción dentro de un rango válido.
     * @param min El número mínimo aceptado para la opción.
     * @param max El número máximo aceptado para la opción.
     * @return El número de opción validado.
     */
    public static int recibirOpcion(int min, int max) {
        int opcion = 0;
        boolean valida = false;
        while (!valida) {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                if (opcion < min || opcion > max) {
                    System.out.println("Opción fuera de rango. Intenta de nuevo.");
                    System.out.print("Selecciona una opción: ");
                } else {
                    valida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, introduce un número.");
                scanner.nextLine();
                System.out.print("Selecciona una opción: ");
            }
        }
        return opcion;
    }
}
