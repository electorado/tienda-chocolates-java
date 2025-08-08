import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal del programa de gestión de una tienda de chocolates.
 * Contiene funciones reutilizables como imprimirMenu y recibirOpcion,
 * Incluye una función que precarga datos para probar la aplicación más fácilmente.
 *
 * @author Pablo Andrés Moncayo Vega
 * @version 3.0 (Añadida función para cargar datos de prueba)
 */
public class Main {

    private static ArrayList<Cliente> listaClientes = new ArrayList<>();
    private static ArrayList<Chocolate> catalogoChocolates = new ArrayList<>();
    private static ArrayList<Venta> listaVentas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Menú principal de la aplicación.
     */
    private static final String menuMain = "*** MENÚ PRINCIPAL ***\n" +
            "1. Gestión de Ventas\n" +
            "2. Gestión de Clientes\n" +
            "3. Gestión de Productos\n" +
            "4. Salir";

    /**
     * Punto de inicio del programa.
     * Carga los datos de prueba y muestra el menú principal.
     */
    public static void main(String[] args) {
        cargarDatosDePrueba();

        int opcion;
        do {
            imprimirMenu(menuMain);
            opcion = recibirOpcion(1, 4);

            switch (opcion) {
                case 1 -> Venta.gestionarVentas(listaVentas, listaClientes, catalogoChocolates, scanner);
                case 2 -> Cliente.gestionarClientes(listaClientes, scanner);
                case 3 -> Chocolate.gestionarProductos(catalogoChocolates, scanner);
                case 4 -> System.out.println("¡Gracias por usar la aplicación! Hasta pronto.");
            }
        } while (opcion != 4);

        scanner.close();
    }

    /**
     * Crea y añade 3 clientes y 3 productos a las listas
     * para que la aplicación no empiece vacía.
     */
    public static void cargarDatosDePrueba() {
        listaClientes.add(new Cliente("1111111A", "Claudia", "García", "611111111", "cg@suemail.com"));
        listaClientes.add(new Cliente("2222222B", "Santiago", "Sanchez", "622222222", "ss@suemail.com"));
        listaClientes.add(new Cliente("3333333C", "Sofía", "Terán", "633333333", "st@suemail.com"));

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
                scanner.nextLine(); // Limpia el buffer del scanner
                if (opcion < min || opcion > max) {
                    System.out.println("Opción fuera de rango. Intenta de nuevo.");
                    System.out.print("Selecciona una opción: ");
                } else {
                    valida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, introduce un número.");
                scanner.nextLine(); // Limpia el buffer en caso de error
                System.out.print("Selecciona una opción: ");
            }
        }
        return opcion;
    }
}
