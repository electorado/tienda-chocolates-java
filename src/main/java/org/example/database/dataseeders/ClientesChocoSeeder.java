package org.example.database.dataseeders;

import org.example.dao.ChocolateDAO;
import org.example.dao.ClienteDAO;
import org.example.dao.VentaDAO;
import org.example.model.Chocolate;
import org.example.model.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientesChocoSeeder {

    public static void main(String[] args) {
        System.out.println("Iniciando la inserción de datos de prueba...");

        ChocolateDAO chocolateDAO = new ChocolateDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        VentaDAO ventaDAO = new VentaDAO();

        Random random = new Random();


        // 1. Crear 20 productos (Chocolates)
        List<Chocolate> chocolates = new ArrayList<>();
        String[] origenes = {"Ecuador", "Colombia", "Madagascar", "Perú", "Venezuela", "República Dominicana", "Ghana", "Costa Rica", "México", "Bolivia"};

        System.out.println("Insertando 20 chocolates...");
        for (int i = 1; i <= 20; i++) {
            String origen = origenes[random.nextInt(origenes.length)];
            int porcentaje = 50 + (i * 2); // 50% to 90%
            double precio = 2.50 + (i * 0.5);
            int stock = 100;

            Chocolate choco = new Chocolate(origen, porcentaje, precio, stock);
            // El ID se genera en el constructor
            try {
                chocolateDAO.insertarChocolate(choco);
                chocolates.add(choco);
            } catch (Exception e) {
                System.out.println("No se pudo insertar chocolate " + choco.getIdProducto() + ": " + e.getMessage());
            }
        }

        // Recuperar chocolates de la BD por si los IDs cambiaron
        chocolates = chocolateDAO.consultarChocolate();

        // 2. Crear 20 clientes
        System.out.println("Insertando 20 clientes...");
        for (int i = 1; i <= 20; i++) {
            Cliente cliente = new Cliente("Cliente " + i, "cliente" + i + "@email.com", "6000000" + String.format("%02d", i));
            try {
                clienteDAO.insertarCliente(cliente);
            } catch (Exception e) {
                System.out.println("No se pudo insertar cliente " + cliente.getEmail() + ": " + e.getMessage());
            }
        }
    }
}