import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que representa a un cliente de la tienda.
 * Llama a funciones de la clase Main para evitar redundancia de código.
 *
 * @author Pablo Andrés Moncayo Vega
 * @version 2.0 (Menús de gestión de cliente autocontenidos)
 */
public class Cliente {
    // --- Atributos de la instancia ---
    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;

    /**
     * Declaración de los menús necesarios para la gestión de clientes
     */
    private static final String menuClientes = "***MENÚ DE GESTIÓN CLIENTES***\n1.Alta de clientes\n2.Baja de clientes" +
            "\n3.Modificación\n4.Buscar por DNI\n5.Lista de clientes\n6.Volver";

    private static final String menuModificarCliente = "***MODIFICAR CLIENTE***\n1.Modificar nombre\n" +
            "2.Modificar apellidos\n3.Modificar teléfono\n4.Modificar email\n5.Volver";

    /**
     * Constructor para crear un nuevo objeto Cliente.
     * Se llama para instanciar un cliente con todos sus datos.
     *
     * @param dni El DNI: debe ser único, ya que servirá como id de cliente.
     * @param nombre El nombre o nombres del cliente.
     * @param apellidos Los apellidos del cliente.
     * @param telefono El número de teléfono de contacto.
     * @param email La dirección de correo electrónico del cliente.
     */
    public Cliente(String dni, String nombre, String apellidos, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }

    // --- Getters y Setters ---

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    /**
     * Devuelve una cadena de texto con los datos del objeto Cliente.
     * @return Cadena en formato String con los datos del cliente.
     */
    @Override
    public String toString() {
        return "Cliente: DNI: " + dni + " | Nombre: " + nombre + " " + apellidos + " | Tel: " + telefono + " | Email: " + email;
    }

    /**
     * Compara el DNI de este cliente con otro objeto para ver si son iguales.
     * Se ignora mayúsculas y minúsculas para mayor flexibilidad.
     * @param obj El objeto a comparar.
     * @return true si los DNI son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cliente otro) {
            return this.dni.equalsIgnoreCase(otro.dni);
        }
        return false;
    }

    // --- Funciones para gestionar la gestión de clientes ---

    /**
     * Muestra el menú de gestión de clientes y maneja la lógica de las opciones.
     * Es el punto de entrada para todas las operaciones relacionadas con clientes.
     *
     * @param listaClientes El ArrayList donde se almacenan los clientes.
     * @param scanner El objeto Scanner que recibe la entrada del usuario.
     */
    public static void gestionarClientes(ArrayList<Cliente> listaClientes, Scanner scanner) {
        int opcionClientes;
        do {
            // CAMBIO: Llama a los métodos de ayuda de la clase Main
            Main.imprimirMenu(menuClientes);
            opcionClientes = Main.recibirOpcion(1, 6);

            switch (opcionClientes) {
                case 1 -> altaCliente(listaClientes, scanner);
                case 2 -> bajaCliente(listaClientes, scanner);
                case 3 -> modificarCliente(listaClientes, scanner);
                case 4 -> buscarCliente(listaClientes, scanner);
                case 5 -> listarClientes(listaClientes);
                case 6 -> System.out.println("Volviendo al menú principal.");
            }
        } while (opcionClientes != 6);
    }

    /**
     * Busca un cliente en la lista por su DNI.
     * Esta función se reutiliza por otros métodos para evitar redundancia de código.
     *
     * @param listaClientes La lista de clientes donde buscar.
     * @param dni El DNI del cliente a encontrar.
     * @return El objeto Cliente si se encuentra, de lo contrario devuelve null.
     */
    static Cliente encontrarClientePorDni(ArrayList<Cliente> listaClientes, String dni) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getDni().equalsIgnoreCase(dni)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Da de alta a un cliente nuevo solicitando sus datos al usuario.
     * Verifica que el DNI no esté registrado previamente antes de crear el cliente.
     *
     * @param listaClientes El ArrayList donde se guardará el nuevo cliente.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    public static void altaCliente(ArrayList<Cliente> listaClientes, Scanner scanner) {
        System.out.println("\n--- ALTA DE CLIENTE ---");
        System.out.print("Introduce el DNI: ");
        String dni = scanner.nextLine();

        if (encontrarClientePorDni(listaClientes, dni) != null) {
            System.out.println("Error: Ya existe un cliente con ese DNI.");
            return;
        }

        System.out.print("Introduce el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Introduce los apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Introduce el teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Introduce el email: ");
        String email = scanner.nextLine();

        Cliente nuevoCliente = new Cliente(dni, nombre, apellidos, telefono, email);
        listaClientes.add(nuevoCliente);

        System.out.println("Cliente " + nombre + " " + apellidos + " dado de alta correctamente.");
    }

    /**
     * Elimina un cliente de la lista buscando su DNI.
     *
     * @param listaClientes El ArrayList de donde se eliminará el cliente.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    public static void bajaCliente(ArrayList<Cliente> listaClientes, Scanner scanner) {
        System.out.println("\n--- BAJA DE CLIENTE ---");
        System.out.print("Introduce el DNI del cliente a eliminar: ");
        String dni = scanner.nextLine();

        Cliente clienteEncontrado = encontrarClientePorDni(listaClientes, dni);

        if (clienteEncontrado != null) {
            listaClientes.remove(clienteEncontrado);
            System.out.println("Cliente eliminado correctamente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    /**
     * Busca cliente por su DNI y modifica sus datos.
     * Muestra un submenú para elegir el dato a modificar.
     *
     * @param listaClientes El ArrayList donde se busca el cliente a modificar.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    public static void modificarCliente(ArrayList<Cliente> listaClientes, Scanner scanner) {
        System.out.println("\n--- MODIFICAR CLIENTE ---");
        System.out.print("Introduce el DNI del cliente a modificar: ");
        String dniModificar = scanner.nextLine();

        Cliente clienteAModificar = encontrarClientePorDni(listaClientes, dniModificar);

        if (clienteAModificar != null) {
            int opcionModificacion;
            do {
                // CAMBIO: Llama a los métodos de ayuda de la clase Main
                Main.imprimirMenu(menuModificarCliente);
                opcionModificacion = Main.recibirOpcion(1, 5);

                switch (opcionModificacion) {
                    case 1 -> {
                        System.out.print("Introduce el nuevo nombre: ");
                        clienteAModificar.setNombre(scanner.nextLine());
                        System.out.println("Nombre modificado correctamente.");
                    }
                    case 2 -> {
                        System.out.print("Introduce los nuevos apellidos: ");
                        clienteAModificar.setApellidos(scanner.nextLine());
                        System.out.println("Apellidos modificados correctamente.");
                    }
                    case 3 -> {
                        System.out.print("Introduce el nuevo teléfono: ");
                        clienteAModificar.setTelefono(scanner.nextLine());
                        System.out.println("Teléfono modificado correctamente.");
                    }
                    case 4 -> {
                        System.out.print("Introduce el nuevo email: ");
                        clienteAModificar.setEmail(scanner.nextLine());
                        System.out.println("Email modificado correctamente.");
                    }
                    case 5 -> System.out.println("Volviendo al menú de gestión de clientes.");
                }
            } while (opcionModificacion != 5);
        } else {
            System.out.println("No se encontró ningún cliente con ese DNI.");
        }
    }

    /**
     * Busca y muestra los datos de un cliente por su DNI.
     *
     * @param listaClientes El ArrayList donde se buscan los clientes.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    public static void buscarCliente(ArrayList<Cliente> listaClientes, Scanner scanner) {
        System.out.println("\n--- BUSCAR CLIENTE POR DNI ---");
        System.out.print("Introduce el DNI del cliente a buscar: ");
        String dniBuscar = scanner.nextLine();

        Cliente clienteEncontrado = encontrarClientePorDni(listaClientes, dniBuscar);

        if (clienteEncontrado != null) {
            System.out.println("Cliente encontrado:");
            System.out.println(clienteEncontrado.toString());
        } else {
            System.out.println("No se encontró ningún cliente con ese DNI.");
        }
    }

    /**
     * Muestra en consola la lista completa de clientes registrados con todos sus datos.
     *
     * @param listaClientes El ArrayList que se va a listar.
     */
    public static void listarClientes(ArrayList<Cliente> listaClientes) {
        System.out.println("\n--- LISTADO DE CLIENTES ---");
        if (listaClientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Cliente cliente : listaClientes) {
                System.out.println(cliente.toString());
            }
        }
    }

}
