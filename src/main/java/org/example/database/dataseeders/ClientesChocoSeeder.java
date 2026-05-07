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

        // Recuperar clientes de la BD para tener sus IDs reales
       // List<Cliente> clientes = clienteDAO.consultarCliente();

//        // 3. Crear 20 ventas
//        System.out.println("Insertando 20 ventas...");
//
//        for (int i = 0; i < 20; i++) {
//            if (clientes.isEmpty() || chocolates.isEmpty()) {
//                System.out.println("Faltan clientes o chocolates para crear ventas.");
//                break;
//            }
//
//            // Seleccionar un cliente al azar
//            Cliente cliente = clientes.get(random.nextInt(clientes.size()));
//
//            // Seleccionar entre 1 y 5 chocolates al azar para esta venta
//            int numChocolates = random.nextInt(5) + 1;
//            List<Chocolate> chocolatesVenta = new ArrayList<>();
//            for (int j = 0; j < numChocolates; j++) {
//                chocolatesVenta.add(chocolates.get(random.nextInt(chocolates.size())));
//            }
//
//            Venta venta = new Venta(cliente, chocolatesVenta);
//            try {
//                ventaDAO.insertarVenta(venta);
//            } catch (Exception e) {
//                System.out.println("No se pudo insertar venta para cliente " + cliente.getEmail() + ": " + e.getMessage());
//            }
//        }
//
//        System.out.println("¡Generación de datos finalizada!");
    }
}