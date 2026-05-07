package org.example.service;

import org.example.dao.ClienteDAO;
import org.example.model.Cliente;
import org.example.util.Opcion;

import java.util.List;
import java.util.Scanner;

public class ClienteService {

    private final Scanner scanner;
    private final ClienteDAO clienteDAO;

    public ClienteService(Scanner scanner) {
        this.scanner = scanner;
        this.clienteDAO = new ClienteDAO();
    }

    public void gestionarClientes() {
        int opcion;

        do {
            System.out.println("\n*** MENÚ DE GESTIÓN CLIENTES ***");
            System.out.println("1. Alta de clientes");
            System.out.println("2. Baja de clientes");
            System.out.println("3. Modificación");
            System.out.println("4. Buscar por Email");
            System.out.println("5. Lista de clientes");
            System.out.println("6. Volver");
            System.out.print("Selecciona una opción: ");

            opcion = Opcion.recibirOpcion(scanner, 1, 6);

            switch (opcion) {
                case 1 -> altaCliente();
                case 2 -> bajaCliente();
                case 3 -> modificarCliente();
                case 4 -> buscarPorEmail();
                case 5 -> listarClientes();
                case 6 -> System.out.println("Volviendo al menú principal...");
            }

        } while (opcion != 6);
    }

    private void altaCliente() {
        System.out.print("Introduce el Email: ");
        String email = scanner.nextLine();

        if (clienteDAO.buscarPorEmail(email) == null) {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();

            Cliente cliente = new Cliente(nombre, email, telefono);
            clienteDAO.insertarCliente(cliente);
            System.out.println("Cliente añadido.");
        } else {
            System.out.println("Ya existe un cliente con ese Email.");
        }
    }

    private void bajaCliente() {
        System.out.print("Introduce el email del cliente a eliminar: ");
        String email = scanner.nextLine();

        Cliente cliente = clienteDAO.buscarPorEmail(email);

        if (cliente != null) {
            clienteDAO.eliminarCliente(cliente.getNombre(), cliente.getEmail());
            System.out.println("Cliente eliminado.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void modificarCliente() {
        System.out.print("Introduce el Email del cliente a modificar: ");
        String emailOriginal = scanner.nextLine();

        Cliente cli = clienteDAO.buscarPorEmail(emailOriginal);

        if (cli != null) {
            System.out.print("Nuevo nombre (" + cli.getNombre() + "): ");
            String nombreNuevo = scanner.nextLine();
            if (!nombreNuevo.isEmpty()) {
                cli.setNombre(nombreNuevo);
            }

            System.out.print("Nuevo teléfono (" + cli.getTelefono() + "): ");
            String telefonoNuevo = scanner.nextLine();
            if (!telefonoNuevo.isEmpty()) {
                cli.setTelefono(telefonoNuevo);
            }

            System.out.print("Nuevo email (" + cli.getEmail() + "): ");
            String emailNuevo = scanner.nextLine();
            if (!emailNuevo.isEmpty()) {
                cli.setEmail(emailNuevo);
            }

            clienteDAO.modificarCliente(cli);
            System.out.println("Cliente actualizado.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void buscarPorEmail() {
        System.out.print("Introduce el Email del cliente a buscar: ");
        String emailBuscado = scanner.nextLine();

        Cliente cliente = clienteDAO.buscarPorEmail(emailBuscado);

        if (cliente != null) {
            System.out.println(cliente);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteDAO.consultarCliente();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes.");
        } else {
            clientes.forEach(System.out::println);
        }
    }
}