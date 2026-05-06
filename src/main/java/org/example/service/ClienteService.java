package org.example.service;

import org.example.dao.ClienteDAO;
import org.example.model.Cliente;
import java.util.List;
import java.util.Scanner;

public class ClienteService {
    private final Scanner scanner = new Scanner(System.in);
    private final ClienteDAO clienteDAO = new ClienteDAO();

    /**
     * Este método encapsula toda la sección de "Gestión de Clientes" que estaba en el Main.
     */
    public void menuGestionClientes() {
        int opcion;
        do {
            System.out.println("\n*** MENÚ DE GESTIÓN CLIENTES ***");
            System.out.println("1. Alta de clientes");
            System.out.println("2. Baja de clientes");
            System.out.println("3. Modificación");
            System.out.println("4. Buscar por Email");
            System.out.println("5. Lista de clientes");
            System.out.println("0. Volver");
            System.out.print("Selecciona una opción: ");

            // Usamos la lógica de validación que tenías en el Main
            opcion = recibirOpcion(1, 6);

            switch (opcion) {
                case 1 -> altaCliente();
                case 2 -> bajaCliente();
                case 3 -> modificacionCliente();
                case 4 -> buscarPorEmail();
                case 5 -> listarClientes();
            }
        } while (opcion != 0);
    }

    private void altaCliente() {
        System.out.print("Introduce el Email: ");
        String email = scanner.nextLine();
        // Usamos el método de ClienteDAO que busca por email
        if (clienteDAO.buscarPorEmail(email) == null) {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();

            // Llamamos a tu método original insertarCliente[cite: 3]
            clienteDAO.insertarCliente(new Cliente(nombre, email, telefono));
        } else {
            System.out.println("Ya existe un cliente con ese Email.");
        }
    }

    private void bajaCliente() {
        System.out.print("Introduce el ID del cliente a eliminar: ");
        try {
            int idBorrar = Integer.parseInt(scanner.nextLine());
            // No existe búsqueda por ID en tu DAO original, así que se usa el delete directo
            clienteDAO.eliminarCliente(idBorrar);
            System.out.println("Operación de eliminación realizada.");
        } catch (NumberFormatException e) {
            System.out.println("ID no válido.");
        }
    }

    private void modificacionCliente() {
        System.out.print("Introduce el Email del cliente a modificar: ");
        String email = scanner.nextLine();
        Cliente cli = clienteDAO.getClienteByEmail(email);

        if (cli != null) {
            System.out.print("Nuevo nombre (" + cli.getNombre() + "): ");
            String n = scanner.nextLine();
            if(!n.isEmpty()) cli.setNombre(n);

            System.out.print("Nuevo teléfono (" + cli.getTelefono() + "): ");
            String t = scanner.nextLine();
            if(!t.isEmpty()) cli.setTelefono(t);

            // Nota: Aquí se llamaría a un método update en el DAO si existiera
            System.out.println("Datos preparados para actualizar (Requiere método update en DAO).");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void buscarPorEmail() {
        System.out.print("Introduce el Email del cliente a buscar: ");
        String emailBuscado = scanner.nextLine();
        Cliente cliente = clienteDAO.buscarPorEmail(emailBuscado);
        if (cliente != null) System.out.println(cliente);
        else System.out.println("Cliente no encontrado.");
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteDAO.getAllClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    private int recibirOpcion(int min, int max) {
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion < min || opcion > max) {
                System.out.println("Opción fuera de rango.");
                return -1;
            }
            return opcion;
        } catch (NumberFormatException e) {
            System.out.println("Introduce un número válido.");
            return -1;
        }
    }
}