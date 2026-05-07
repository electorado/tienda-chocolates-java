package org.example.service;

import org.example.dao.ChocolateDAO;
import org.example.model.Chocolate;
import org.example.util.Opcion;

import java.util.List;
import java.util.Scanner;

public class ChocolateService {

    private final Scanner scanner;
    private final ChocolateDAO chocolateDAO;

    public ChocolateService(Scanner scanner) {
        this.scanner = scanner;
        this.chocolateDAO = new ChocolateDAO();
    }

    public void gestionarProductos() {
        int opcion;

        do {
            System.out.println("\n*** MENÚ DE GESTIÓN DE PRODUCTOS ***");
            System.out.println("1. Alta de nuevo producto");
            System.out.println("2. Recepción de producto (Añadir stock)");
            System.out.println("3. Ver inventario");
            System.out.println("4. Buscar por origen y % de cacao");
            System.out.println("5. Volver");
            System.out.print("Selecciona una opción: ");

            opcion = Opcion.recibirOpcion(scanner, 1, 5);

            switch (opcion) {
                case 1 -> altaChocolate();
                case 2 -> recibirStock();
                case 3 -> verInventario();
                case 4 -> buscarPorOrigenYCacao();
                case 5 -> System.out.println("Volviendo al menú principal...");
            }

        } while (opcion != 5);
    }

    private void altaChocolate() {
        System.out.println("\n--- ALTA DE NUEVO PRODUCTO ---");
        System.out.print("Introduce el país de origen: ");
        String origen = scanner.nextLine();

        System.out.print("Introduce el porcentaje de cacao: ");
        int porcentaje = Integer.parseInt(scanner.nextLine());

        System.out.print("Introduce el precio: ");
        double precio = Double.parseDouble(scanner.nextLine().replace(',', '.'));

        System.out.print("Introduce la cantidad de unidades a ingresar: ");
        int unidades = Integer.parseInt(scanner.nextLine());

        Chocolate nuevo = new Chocolate(origen, porcentaje, precio, unidades);

        if (chocolateDAO.buscarPorId(nuevo.getIdProducto()) == null) {
            chocolateDAO.insertarChocolate(nuevo);
            System.out.println("Producto dado de alta.");
        } else {
            System.out.println("Error: El producto ya existe.");
        }
    }

    private void recibirStock() {
        System.out.print("Introduce el ID del producto para añadir stock: ");
        String id = scanner.nextLine().toUpperCase();

        Chocolate chocolate = chocolateDAO.buscarPorId(id);

        if (chocolate != null) {
            System.out.print("Introduce la cantidad de unidades a añadir: ");
            int unidades = Integer.parseInt(scanner.nextLine());

            chocolate.setStock(chocolate.getStock() + unidades);
            chocolateDAO.modificarChocolate(chocolate);

            System.out.println("Stock actualizado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private void verInventario() {
        List<Chocolate> chocolates = chocolateDAO.consultarChocolate();

        if (chocolates.isEmpty()) {
            System.out.println("No hay productos en inventario.");
        } else {
            chocolates.forEach(System.out::println);
        }
    }

    private void buscarPorOrigenYCacao() {
        System.out.print("Introduce el país de origen a buscar: ");
        String origen = scanner.nextLine();

        System.out.print("Introduce el porcentaje de cacao a buscar: ");
        int porcentajeCacao = Integer.parseInt(scanner.nextLine());

        List<Chocolate> chocolates = chocolateDAO.buscarPorOrigenYCacao(origen, porcentajeCacao);

        if (chocolates.isEmpty()) {
            System.out.println("No se encontraron productos.");
        } else {
            chocolates.forEach(System.out::println);
        }
    }
}