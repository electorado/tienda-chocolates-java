package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VentaTest {

    private Venta venta;
    private Cliente cliente;
    private List<Chocolate> chocolates;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1, "Juan Perez", "juan@email.com", "600123456");
        chocolates = new ArrayList<>();
        
        chocolates.add(new Chocolate("Ecuador", 60, 2.50, 10)); // ECU60
        chocolates.add(new Chocolate("Colombia", 70, 3.50, 10)); // COL70

        venta = new Venta(cliente, chocolates);
    }

    @Test
    void testCalculoImporteTotal() {
        // 2.50 + 3.50 = 6.00
        assertEquals(6.00, venta.getImporteTotal(), 0.001, "El importe total debería ser la suma de los precios de los productos.");
    }

    @Test
    void testImporteTotalVentaVacia() {
        Venta ventaVacia = new Venta(cliente, new ArrayList<>());
        assertEquals(0.0, ventaVacia.getImporteTotal(), 0.001, "El importe de una venta sin productos debería ser 0.");
    }

    @Test
    void testImporteTotalVentaNula() {
        Venta ventaNula = new Venta(cliente, null);
        assertEquals(0.0, ventaNula.getImporteTotal(), 0.001, "El importe de una venta con lista de productos nula debería ser 0.");
        
        // Verifica que el constructor protege de listas nulas
        assertNotNull(ventaNula.getChocolatesVendidos(), "La lista de chocolates no debería ser null si se inicializó con null.");
    }

    @Test
    void testInicializacionFechaVenta() {
        assertNotNull(venta.getFechaVenta(), "La fecha de venta no debería ser null por defecto.");
    }

    @Test
    void testSetChocolatesVendidosProtegeNull() {
        venta.setChocolatesVendidos(null);
        assertNotNull(venta.getChocolatesVendidos(), "El setter debería convertir un null en una lista vacía.");
        assertTrue(venta.getChocolatesVendidos().isEmpty(), "La lista debería estar vacía.");
    }
}