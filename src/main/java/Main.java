import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Clase principal del programa de gestión de una tienda de chocolates.
 * Muestra un menú principal y submenús para gestionar clientes, productos y ventas.
 * Usa funciones reutilizables para imprimir menús y recibir opciones validadas.
 *
 * @author Pablo Andrés Moncayo Vega
 * @version 1.0
 */
public class Main {

    // Listas principales para almacenar datos
    private static ArrayList<Chocolate> catalogoChocolates = new ArrayList<>();
    private static ArrayList<Venta> listaVentas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Cliente> listaClientes = new ArrayList<>();

    // Definición de los menús
    private static final String menuMain = "***MENÚ PRINCIPAL***\n1.Ventas\n2.Gestión de clientes\n" +
            "3.Gestión de producto\n4.Salir";
    private static final String menuClientes = "***MENÚ DE GESTIÓN CLIENTES***\n1.Alta de clientes\n2.Baja de clientes\n" +
            "3.Modificación\n4.Buscar por DNI\n5.Lista de clientes\n6.Volver";
    private static final String menuProducto = "***MENÚ DE GESTIÓN DE PRODUCTOS***\n1.Alta de producto\n" +
            "2.Ver inventario\n3.Buscar por % de cacao\n4.Buscar por origen\n5.Volver";
    private static final String menuVentas = "***MENÚ DE GESTIÓN DE VENTAS***\n1.Nueva venta\n" +
            "2.Mostrar todas las ventas\n3.Mostrar ventas por cliente\n4.Mostrar una venta\n5.Volver";
    private static final String menuModificarCliente = "***MODIFICAR CLIENTE***\n1.Modificar nombre\n" +
            "2.Modificar apellidos\n3.Modificar teléfono\n4.Modificar email\n5.Volver";

    /**
     * Punto de inicio del programa.
     * Muestra el menú principal y luego muestra un submenú según la opción seleccionada por el usuario.
     *
     * @param args No necesita argumentos.
     */
    public static void main(String[] args) {
        int opcion;

        do {
            imprimirMenu(menuMain);
            opcion = recibirOpcion(1, 4);

            switch (opcion) {
                case 1 -> {
                    //Se gestionará desde Venta
                }
                case 2 -> {
                    Cliente.gestionarClientes(listaClientes, scanner);
                }
                case 3 -> {
                    //Se gestionará desde Producto
                }
            }
        } while (opcion != 4);

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
     * Pide el número de una opción al usuario dentro de un rango válido(el número de
     * opciones presentadas en el menú).
     * Gestiona errores de entrada y repite hasta que reciba un valor válido.
     *
     * @param min Número mínimo aceptado.
     * @param max Número máximo aceptado.
     * @return Número de opción elegido por el usuario.
     */
    public static int recibirOpcion(int min, int max) {
        int opcion = 0;
        boolean valida = false;

        while (!valida) {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpia el buffer
                if (opcion < min || opcion > max) {
                    System.out.println("Opción fuera de rango. Intenta de nuevo.");
                    System.out.print("Selecciona una opción: ");
                } else {
                    valida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, introduce un número.");
                scanner.nextLine(); // Limpia el buffer
                System.out.print("Selecciona una opción: ");
            }
        }
        return opcion;
    }
}
