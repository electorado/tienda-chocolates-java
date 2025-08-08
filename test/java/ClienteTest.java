import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase test para la clase Cliente.
 * Comprueba tanto el comportamiento de un objeto "cliente" como las funciones.
 */
class ClienteTest {

    private Cliente clientePrueba;
    private ArrayList<Cliente> listaClientesPrueba;

    /**
     * Esta función se ejecuta antes de cada test.
     * Preparamos un cliente y una lista de clientes para usarlos en cada prueba.
     */
    @BeforeEach
    void setUp() {
        // Creamos un cliente que usaremos para probar los métodos del objeto
        clientePrueba = new Cliente("12345678A", "Carla", "Angulo", "666111222", "carla@suemail.com");

        // Creamos una lista con algunos clientes para probar el método de búsqueda
        listaClientesPrueba = new ArrayList<>();
        listaClientesPrueba.add(clientePrueba); // Añadimos el cliente anterior
        listaClientesPrueba.add(new Cliente("87654321Z", "Luis", "Andrade", "654321012", "luis@suemail.com"));
    }

    /**
     * Test para el constructor y los getters.
     * Comprueba que un objeto Cliente se crea con los datos correctos.
     */
    @Test
    void constructorAsignaValoresCorrectamente() {
        assertEquals("12345678A", clientePrueba.getDni());
        assertEquals("Carla", clientePrueba.getNombre());
        assertEquals("carla@suemail.com", clientePrueba.getEmail());
    }

    /**
     * Test para un setter.
     * Comprueba que podemos modificar un atributo del cliente.
     */
    @Test
    void setTelefonoCambiaElTelefono() {
        // 1. Acción
        clientePrueba.setTelefono("998877665");
        // 2. Verificación
        assertEquals("998877665", clientePrueba.getTelefono());
    }

    /**
     * Test para equals() - CASO POSITIVO.
     * Dos clientes con el mismo DNI (incluso con mayúsculas/minúsculas distintas)
     * deberían considerarse iguales.
     */
    @Test
    void equalsConMismoDni() {
        // 1. Preparación
        Cliente otroClienteMismoDni = new Cliente("12345678a", "Carla", "García", "666111222", "carla@suemail.com");
        // 2. Verificación
        assertTrue(clientePrueba.equals(otroClienteMismoDni));
    }

    /**
     * Test para equals() - CASO NEGATIVO.
     * Dos clientes con DNI diferente no deberían ser iguales.
     */
    @Test
    void equalsConDiferenteDni() {
        //1. Preparación
        Cliente otroCliente = new Cliente("11112222C", "Pedro", "Cevallos", "611223344", "pedro@suemail.com");
        // 2. Verificación
        assertFalse(clientePrueba.equals(otroCliente));
    }

    /**
     * Test para encontrarClientePorDni() - Cuando existe.
     * Comprueba que encuentra un cliente que sí está en la lista,
     * debe devolver cliente si es que existe o de lo contrario null.
     */
    @Test
    void encontrarClientePorDniCuandoExiste() {
       //1. Preparación
        Cliente encontrado = Cliente.encontrarClientePorDni(listaClientesPrueba, "87654321Z");
        //2. Verificación
        assertNotNull(encontrado, "El cliente debería haber sido encontrado (no ser null).");
        assertEquals("87654321Z", encontrado.getDni(), "El DNI del cliente encontrado no es el correcto.");
        assertEquals("Luis", encontrado.getNombre());
    }

    /**
     * Test para encontrarClientePorDni() - Cuando no existe.
     * Comprueba que si el cliente no está en la lista se devuelve null.
     */
    @Test
    void encontrarClientePorDniCuandoNoExiste() {
        //1. Preparación
        Cliente encontrado = Cliente.encontrarClientePorDni(listaClientesPrueba, "00000000X");
        //2. Verificación
        assertNull(encontrado, "No debería encontrar el cliente y debería devolver null.");
    }
}