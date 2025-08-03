import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Clase que representa a un cliente de la tienda.
 * Cada cliente tiene un DNI único, nombre, apellidos, teléfono y dirección de correo electrónico.
 *
 * Incluye además, métodos estáticos para gestionar la lista de clientes:
 * alta, baja, modificación, búsqueda y listado.
 *
 * @author Pablo Andrés moncayo Vega
 * @version 1.0
 */
public class Cliente {
    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;

    // Menús específicos para la gestión de clientes, accesibles solo desde esta clase
    private static final String menuClientes = "***MENÚ DE GESTIÓN CLIENTES***\n1.Alta de clientes\n2.Baja de clientes\n3.Modificación\n4.Buscar por DNI\n5.Lista de clientes\n6.Volver";
    private static final String menuModificarCliente = "***MODIFICAR CLIENTE***\n1.Modificar nombre\n2.Modificar apellidos\n3.Modificar teléfono\n4.Modificar email\n5.Volver";

    /**
     * Constructor de la clase Cliente.
     *
     * @param dni DNI del cliente (debe ser único).
     * @param nombre Nombre del cliente.
     * @param apellidos Apellidos del cliente.
     * @param telefono Teléfono del cliente.
     * @param email La dirección de correo electrónico del cliente.
     */

    public Cliente(String dni, String nombre, String apellidos, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }

    //Getters y Setters de la clase Cliente
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente: DNI: " + dni + " | Nombre: " + nombre + " " + apellidos + " | Tel: " + telefono + " | Email: " + email;
    }

    /**
     * Compara este objeto Cliente con otro basado en su DNI.
     *
     * @param obj El objeto a comparar.
     * @return true si los DNI son iguales (ignorando mayúsculas/minúsculas), false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cliente otro) {
            return this.dni.equalsIgnoreCase(otro.dni);
        }
        return false;
    }


    // --Funciones estáticas para gestionar la lista de clientes--

    /**
     * Muestra el menú de gestión de clientes y maneja las opciones.
     *
     * @param listaClientes El ArrayList donde se almacenan los clientes.
     * @param scanner El objeto Scanner para la entrada del usuario.
     */
    public static void gestionarClientes(ArrayList<Cliente> listaClientes, Scanner scanner) {
        int opcionClientes;
        do {
            imprimirMenu(menuClientes);
            opcionClientes = recibirOpcion(scanner, 1, 6);

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
     * Muestra un menú en consola.
     *
     * @param menu Nombre de la variable con el texto del menú que a imprimir.
     */
    private static void imprimirMenu(String menu) {
        System.out.println(menu);
        System.out.print("Selecciona una opción: ");
    }

    /**
     * Pide número de opción al usuario dentro de un rango válido.
     * Gestiona errores de entrada y repite hasta que reciba un valor válido.
     *
     * @param scanner Objeto Scanner para leer la entrada del usuario.
     * @param min Número mínimo aceptado.
     * @param max Número máximo aceptado.
     * @return Número de opción elegido por el usuario.
     */
    private static int recibirOpcion(Scanner scanner, int min, int max) {
        int opcion = 0;
        boolean valida = false;
        while (!valida) {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                if (opcion < min || opcion > max) {
                    System.out.println("Opción fuera de rango. Intenta de nuevo.");
                    System.out.print("Selecciona una opción: ");
                } else {
                    valida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, introduce un número.");
                scanner.nextLine(); // Limpiar buffer
                System.out.print("Selecciona una opción: ");
            }
        }
        return opcion;
    }

    /**
     * Da de alta a un nuevo cliente solicitando sus datos al usuario.
     *
     * @param listaClientes El ArrayList donde se guardarán los clientes.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    public static void altaCliente(ArrayList<Cliente> listaClientes, Scanner scanner) {
        System.out.println("\n--- ALTA DE CLIENTE ---");
        System.out.print("Introduce el DNI: ");
        String dni = scanner.nextLine();

        for (Cliente c : listaClientes) {
            if (c.getDni().equalsIgnoreCase(dni)) {
                System.out.println("Ya existe un cliente con ese DNI.");
                return;
            }
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
     * @param listaClientes El ArrayList donde se buscan los clientes.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    public static void bajaCliente(ArrayList<Cliente> listaClientes, Scanner scanner) {
        System.out.println("\n--- BAJA DE CLIENTE ---");
        System.out.print("Introduce el DNI del cliente a eliminar: ");
        String dni = scanner.nextLine();

        Cliente clienteEncontrado = null;
        for(Cliente c : listaClientes) {
            if(c.getDni().equalsIgnoreCase(dni)) {
                clienteEncontrado = c;
                break;
            }
        }

        if (clienteEncontrado != null) {
            listaClientes.remove(clienteEncontrado);
            System.out.println("Cliente eliminado correctamente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    /**
     * Modifica los datos de un cliente existente buscando por su DNI.
     *
     * @param listaClientes El ArrayList donde se buscan los clientes.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    public static void modificarCliente(ArrayList<Cliente> listaClientes, Scanner scanner) {
        String menuModificarCliente = "***MODIFICAR CLIENTE***\n1.Modificar nombre\n2.Modificar apellidos\n3.Modificar teléfono\n4.Modificar email\n5.Volver";
        System.out.println("\n--- MODIFICAR CLIENTE ---");
        System.out.print("Introduce el DNI del cliente a modificar: ");
        String dniModificar = scanner.nextLine();

        Cliente clienteAModificar = null;
        for (Cliente cliente : listaClientes) {
            if (cliente.getDni().equals(dniModificar)) {
                clienteAModificar = cliente;
                break;
            }
        }

        if (clienteAModificar != null) {
            int opcionModificacion;
            do {
                imprimirMenu(menuModificarCliente);
                opcionModificacion = recibirOpcion(scanner, 1, 5);

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
                    case 5 -> {
                        System.out.println("Volviendo al menú de gestión de clientes.");
                    }
                }
            } while (opcionModificacion != 5);
        } else {
            System.out.println("No se encontró ningún cliente con ese DNI.");
        }
    }

    /**
     * Busca y muestra los datos de un cliente por DNI.
     *
     * @param listaClientes El ArrayList donde se buscan los clientes.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    public static void buscarCliente(ArrayList<Cliente> listaClientes, Scanner scanner) {
        System.out.println("\n--- BUSCAR CLIENTE POR DNI ---");
        System.out.print("Introduce el DNI del cliente a buscar: ");
        String dniBuscar = scanner.nextLine();

        Cliente clienteEncontrado = null;
        for (Cliente cliente : listaClientes) {
            if (cliente.getDni().equalsIgnoreCase(dniBuscar)) {
                clienteEncontrado = cliente;
                break;
            }
        }

        if (clienteEncontrado != null) {
            System.out.println("Cliente encontrado:");
            System.out.println(clienteEncontrado.toString());
        } else {
            System.out.println("No se encontró ningún cliente con ese DNI.");
        }
    }

    /**
     * Muestra en consola la lista completa de clientes.
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

