import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de tests para verificar la gestión de la clase Chocolate.
 * Se enfoca en los aspectos más importantes y únicos de la clase, como la
 * generación automática de ID y el método equals.
 *
 * @author Pablo Andrés Moncayo Vega (Adaptado por IA)
 * @version 1.0
 */
class ChocolateTest {

    /**
     * Test para verificar que el ID del producto se genera correctamente.
     * Comprueba que se usen las 3 primeras letras del origen en mayúsculas
     * y el porcentaje de cacao.
     */
    @Test
    void testGeneracionIdCorrecta() {
        // 1. Preparación
        Chocolate chocoEcuador = new Chocolate("Ecuador", 85, 4.50, 100);
        String idEsperado = "ECU85";
        // 2. Acción
        String idGenerado = chocoEcuador.getIdProducto();
        // 3. Verificación
        assertEquals(idEsperado, idGenerado, "El ID no se generó correctamente para un origen largo.");
    }

    /**
     * Test para el método equals().
     * Dos objetos Chocolate distintos, pero con los mismos datos para generar el ID,
     * deberían ser considerados iguales.
     */
    @Test
    void testEqualsConMismoId() {
        // 1. Preparación
        Chocolate choco1 = new Chocolate("Peru", 90, 6.0, 20);
        Chocolate choco2 = new Chocolate("Peru", 90, 6.5, 30); // Mismo origen y cacao, pero diferente precio/stock
        // 2. Verificación
        assertTrue(choco1.equals(choco2), "Dos chocolates con el mismo ID deberían ser iguales.");
        assertEquals(choco1.getIdProducto(), choco2.getIdProducto(), "Los IDs deberían ser idénticos.");
    }

    /**
     * Test para el método equals().
     * Dos chocolates con IDs diferentes no deberían ser iguales.
     */
    @Test
    void testEqualsConDiferenteId() {
        // 1. Preparación
        Chocolate chocoPeru = new Chocolate("Peru", 90, 6.0, 20);
        Chocolate chocoGhana = new Chocolate("Ghana", 90, 6.0, 20);
        // 2. Verificación
        assertFalse(chocoPeru.equals(chocoGhana), "Dos chocolates con diferente ID no deberían ser iguales.");
    }

}