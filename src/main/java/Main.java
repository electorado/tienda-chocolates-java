import org.example.service.ChocolateService;
import org.example.service.ClienteService;
import org.example.service.VentaService;
import org.example.util.Opcion;
import java.util.Scanner;

/**
 * Clase principal del programa de gestión de una tienda de chocolates.
 * Adaptado para usar el patrón DAO con la nueva base de datos.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final ClienteService clienteService = new ClienteService(scanner);
    private static final ChocolateService chocolateService = new ChocolateService(scanner);
    private static final VentaService ventaService = new VentaService(scanner);

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("\n*** MENÚ PRINCIPAL ***");
            System.out.println("1. Gestión de Ventas");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Gestión de Productos");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");

            opcion = Opcion.recibirOpcion(scanner, 1, 4);

            switch (opcion) {
                case 1 -> ventaService.gestionarVentas();
                case 2 -> clienteService.gestionarClientes();
                case 3 -> chocolateService.gestionarProductos();
                case 4 -> System.out.println("¡Gracias por usar la aplicación! Hasta pronto.");
            }

        } while (opcion != 4);

        scanner.close();
    }
}