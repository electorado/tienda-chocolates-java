package org.example.service;

import org.example.dao.ChocolateDAO;
import org.example.dao.ClienteDAO;
import org.example.dao.VentaDAO;
import org.example.model.Chocolate;
import org.example.model.Cliente;
import org.example.model.Venta;
import org.example.util.Opcion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VentaService {

    private final Scanner scanner;
    private final ClienteDAO clienteDAO;
    private final ChocolateDAO chocolateDAO;
    private final VentaDAO ventaDAO;

    public VentaService(Scanner scanner) {
        this.scanner = scanner;
        this.clienteDAO = new ClienteDAO();
        this.chocolateDAO = new ChocolateDAO();
        this.ventaDAO = new VentaDAO();
    }

    public void gestionarVentas() {
        int opcion;

        do {
            System.out.println("\n*** GESTIÓN DE VENTAS ***");
            System.out.println("1. Realizar venta");
            System.out.println("2. Mostrar todas las ventas");
            System.out.println("3. Gestionar ventas por cliente");
            System.out.println("4. Top 5 productos más vendidos");
            System.out.println("5. Top 5 clientes del mes");
            System.out.println("6. Volver");

            opcion = Opcion.recibirOpcion(scanner, 1, 6);

            switch (opcion) {
                case 1 -> realizarVenta();
                case 2 -> mostrarVentas();
                case 3 -> mostrarVentasPorCliente();
                case 4 -> ventaDAO.mostrarTop5ProductosMasVendidos();
                case 5 -> ventaDAO.mostrarTop5ClientesDelMes();
                case 6 -> System.out.println("Volviendo al menú principal...");
            }

        } while (opcion != 6);
    }

    private void realizarVenta() {
        System.out.print("Introduce el Email del cliente: ");
        String email = scanner.nextLine();

        Cliente cliente = clienteDAO.buscarPorEmail(email);

        if (cliente != null) {
            List<Chocolate> catalogo = chocolateDAO.consultarChocolate();

            if (catalogo.isEmpty()) {
                System.out.println("No hay productos disponibles.");
                return;
            }

            List<Chocolate> carrito = new ArrayList<>();
            int opProd;

            do {
                System.out.println("\nSeleccione un producto (0 para finalizar):");

                for (int i = 0; i < catalogo.size(); i++) {
                    System.out.println((i + 1) + ". " + catalogo.get(i));
                }

                opProd = Opcion.recibirOpcion(scanner, 0, catalogo.size());

                if (opProd != 0) {
                    Chocolate prod = catalogo.get(opProd - 1);

                    System.out.print("Cantidad (Stock: " + prod.getStock() + "): ");
                    int cant = Integer.parseInt(scanner.nextLine());

                    if (cant > 0 && cant <= prod.getStock()) {
                        for (int i = 0; i < cant; i++) {
                            carrito.add(prod);
                        }

                        prod.setStock(prod.getStock() - cant);
                        chocolateDAO.modificarChocolate(prod);
                        System.out.println("Añadido.");
                    } else {
                        System.out.println("Cantidad no válida o stock insuficiente.");
                    }
                }

            } while (opProd != 0);

            if (!carrito.isEmpty()) {
                Venta venta = new Venta(cliente, carrito);
                ventaDAO.insertarVenta(venta);
                System.out.println("Venta realizada con éxito.");
            } else {
                System.out.println("No se ha realizado ninguna venta.");
            }

        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void mostrarVentas() {
        List<Venta> ventas = ventaDAO.consultarVentas();

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas.");
        } else {
            ventas.forEach(System.out::println);
        }
    }

    private void mostrarVentasPorCliente() {
        System.out.print("Introduce el Email del cliente: ");
        String email = scanner.nextLine();

        Cliente cliente = clienteDAO.buscarPorEmail(email);

        if (cliente != null) {
            List<Venta> ventasCli = ventaDAO.buscarVentasPorCliente(cliente);

            if (ventasCli.isEmpty()) {
                System.out.println("El cliente no tiene ventas.");
            } else {
                ventasCli.forEach(System.out::println);
            }
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }
}