import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal del programa de gestión de una tienda de chocolates.
 * Contiene funciones reutilizables como imprimirMenu y recibirOpcion,
 * y dirige el flujo principal de la aplicación.
 *
 * @author Pablo Andrés Moncayo Vega
 * @version 2.0(los menús de gestión de Ventas, Clientes y Productos
 * van a su Clase respectiva.
 */
public class Main {

    // --- Listas principales de la aplicación ---
    private static ArrayList<Cliente> listaClientes = new ArrayList<>();
    private static ArrayList<Chocolate> catalogoChocolates = new ArrayList<>();
    private static ArrayList<Venta> listaVentas = new ArrayList<>();

    // --- Objeto Scanner global ---
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
     * Muestra el menú principal y gestiona la navegación a los submenús.
     *
     * @param args no se utilizan.
     */
    public static void main(String[] args) {
        int opcion;
        do {
            imprimirMenu(menuMain);
            opcion = recibirOpcion(1, 4);

            switch (opcion) {
                case 1 -> {
                    // Venta.gestionarVentas(listaVentas, listaClientes, catalogoChocolates, scanner);
                    System.out.println("Gestión de ventas no implementada todavía.");
                }
                case 2 -> Cliente.gestionarClientes(listaClientes, scanner);
                case 3 -> {
                    // Chocolate.gestionarProductos(catalogoChocolates, scanner);
                    System.out.println("Gestión de productos no implementada todavía.");
                }
                case 4 -> System.out.println("¡Saliendo de la aplicación! Hasta pronto.");
            }
        } while (opcion != 4);

        scanner.close();
    }

    // --- Métodos de ayuda reutilizables ---

    /**
     * Muestra un menú en consola y pide una opción al usuario.
     * Función pública y estática para que pueda ser reutilizada por otras clases.
     *
     * @param menu La cadena de texto del menú a imprimir.
     */
    public static void imprimirMenu(String menu) {
        System.out.println("\n" + menu);
        System.out.print("Selecciona una opción: ");
    }

    /**
     * Pide al usuario un número de opción dentro de un rango válido.
     * Función pública y estática para que pueda ser reutilizada por otras clases.
     *
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
