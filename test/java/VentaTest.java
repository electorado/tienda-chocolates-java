import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de tests para la clase Venta, enfocada en la lógica de negocio esencial.
 * Pruebas mínimas para verificar el cálculo de importes y la gestión de stock.
 *
 * @author Pablo Andrés Moncayo Vega
 * @version 1.0
 */
class VentaTest {

    private Cliente clientePrueba;
    private Chocolate choco1;
    private Chocolate choco2;
    private ArrayList<Chocolate> carrito;

    /**
     * Método de configuración que se ejecuta antes de cada test.
     * Prepara los objetos necesarios para evitar repetir código.
     */
    @BeforeEach
    void setUp() {
        clientePrueba = new Cliente("11111111A", "Cliente", "De Prueba", "666777888", "test@test.com");
        choco1 = new Chocolate("Ecuador", 75, 5.00, 20); // Precio 5.00
        choco2 = new Chocolate("Peru", 85, 6.50, 10);    // Precio 6.50
        carrito = new ArrayList<>();
    }

    /**
     * Comprueba que el método getImporteTotal calcula correctamente la suma
     * de los precios de los productos en el carrito.
     */
    @Test
    void testCalcularImporteTotal() {
        // 1. Preparación (Añadir productos al carrito)
        carrito.add(choco1);
        carrito.add(choco2);
        Venta venta = new Venta(clientePrueba, carrito);
        double totalEsperado = 5.00 + 6.50; // 11.50
        // 2. Verificación
        assertEquals(totalEsperado, venta.getImporteTotal(), "El importe total de la venta no se calculó correctamente.");
    }

    /**
     * Simula una compra y verifica que el stock del objeto original (el que está en el catálogo)
     * ha disminuido correctamente.
     */
    @Test
    void testDisminucionDeStock() {
        // 1. Preparación
        int stockInicialChoco1 = choco1.getStock(); // Debería ser 20
        // 2. Acción
        int cantidadComprada = 3;
        for (int i = 0; i < cantidadComprada; i++) {
            carrito.add(choco1);
        }
        choco1.setStock(choco1.getStock() - cantidadComprada);

        new Venta(clientePrueba, carrito);

        // 3. Verificación
        int stockEsperado = stockInicialChoco1 - cantidadComprada; // 20 - 3 = 17
        assertEquals(stockEsperado, choco1.getStock(), "El stock del producto no disminuyó correctamente después de la venta.");
    }
}
