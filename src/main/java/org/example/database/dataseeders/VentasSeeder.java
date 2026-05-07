package org.example.database.dataseeders;

import org.example.dao.ChocolateDAO;
import org.example.dao.ClienteDAO;
import org.example.dao.VentaDAO;
import org.example.model.Chocolate;
import org.example.model.Cliente;
import org.example.model.Venta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VentasSeeder {
    public static void main(String[] args) {
        ChocolateDAO chocolateDAO = new ChocolateDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        VentaDAO ventaDAO = new VentaDAO();
        Random random = new Random();

        // Recuperamos datos reales de BD
        List<Chocolate> chocolates = chocolateDAO.consultarChocolate();
        List<Cliente> clientes = clienteDAO.consultarCliente();

        System.out.println("Insertando 20 ventas...");
        for (int i = 0; i < 20; i++) {
            if (clientes.isEmpty() || chocolates.isEmpty()) {
                System.out.println("Faltan clientes o chocolates para crear ventas.");
                break;
            }

            Cliente cliente = clientes.get(random.nextInt(clientes.size()));

            int numChocolates = random.nextInt(5) + 1;
            List<Chocolate> chocolatesVenta = new ArrayList<>();

            for (int j = 0; j < numChocolates; j++) {
                Chocolate prod = chocolates.get(random.nextInt(chocolates.size()));

                // comprobamos stock
                if (prod.getStock() <= 0) {
                    continue;
                }

                // restamos una unidad al stock
                prod.setStock(prod.getStock() - 1);
                chocolateDAO.modificarChocolate(prod);

                // añadimos una unidad de ese producto a la venta
                chocolatesVenta.add(prod);
            }

            if (!chocolatesVenta.isEmpty()) {
                Venta venta = new Venta(cliente, chocolatesVenta);
                try {
                    ventaDAO.insertarVenta(venta);
                } catch (Exception e) {
                    System.out.println("No se pudo insertar venta para cliente " + cliente.getEmail() + ": " + e.getMessage());
                }
            }
        }

        System.out.println("¡Generación de datos finalizada!");
    }
}
