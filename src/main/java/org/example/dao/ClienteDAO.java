package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, telefono, email) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getEmail());

            ps.executeUpdate();
            System.out.println("Cliente insertado correctamente");

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar cliente", e);
        }
    }

    public List<Cliente> consultarCliente() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY cliente_id";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("cliente_id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar clientes", e);
        }

        return clientes;
    }

    public Cliente buscarPorEmail(String email) {
        String sql = "SELECT * FROM cliente WHERE email = ?";
        Cliente cliente = null;

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                            rs.getInt("cliente_id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("telefono")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cliente por email", e);
        }

        return cliente;
    }

    public void modificarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre = ?, email = ?, telefono = ? WHERE cliente_id = ?";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getTelefono());
            ps.setInt(4, cliente.getClienteId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar cliente", e);
        }
    }

    public void eliminarCliente(String nombre, String email) {
        String sql = "DELETE FROM cliente WHERE nombre = ? AND email = ?";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, email);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar cliente", e);
        }
    }
}