package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChocolateTest {

    private Chocolate chocolate;

    @BeforeEach
    void setUp() {
        // Inicializamos un objeto de prueba antes de cada test
        chocolate = new Chocolate("Colombia", 75, 4.50, 100);
    }

    @Test
    void testIdGeneradoCorrectamente() {
        // "Colombia" -> "Col" + 75 -> "COL75"
        assertEquals("COL75", chocolate.getIdProducto(), "El ID debería generarse a partir de las 3 primeras letras del origen y el porcentaje de cacao en mayúsculas.");
    }

    @Test
    void testIdGeneradoConOrigenCorto() {
        // Si el origen tiene menos de 3 letras, usa el origen completo
        Chocolate corto = new Chocolate("UK", 80, 2.50, 50);
        assertEquals("UK80", corto.getIdProducto(), "El ID debería manejar orígenes de menos de 3 letras.");
    }

    @Test
    void testEqualsConIdDiferente() {
        Chocolate diferente = new Chocolate("Ecuador", 75, 4.50, 100); // ID: ECU75
        assertNotEquals(chocolate, diferente, "Chocolates con IDs diferentes no deberían ser iguales.");
    }

    @Test
    void testEqualsConIdIgual() {
        Chocolate igual = new Chocolate("COL75", "Otro Origen", 90, 10.0, 5);
        assertEquals(chocolate, igual, "Chocolates con el mismo ID deberían ser considerados iguales.");
    }

    @Test
    void testSettersYGetters() {
        chocolate.setStock(50);
        assertEquals(50, chocolate.getStock(), "El stock debería actualizarse a 50.");

        chocolate.setPrecio(5.00);
        assertEquals(5.00, chocolate.getPrecio(), "El precio debería actualizarse a 5.00.");
    }
}