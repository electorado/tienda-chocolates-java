package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void insertarCliente(Cliente cliente) {

        String sql = "INSERT INTO clientes (nombre, telefono, email) VALUES (?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getEmail());

            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("Cliente insertado correctamente");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cliente> consultarCliente() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
                clientes.add(cliente);
            }
        } catch(SQLException e) {
        throw new RuntimeException(e);
    }
        return clientes;
    }

    public Cliente buscarPorEmail(String email) {
        String sql = "SELECT * FROM clientes WHERE email = ?";
        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getInt("cliente_id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void modificarCliente(String nombre, String email) {
        String sql = "UPDATE clientes SET nombre = ? WHERE email = ?";
        try{
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, email);

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void eliminarCliente(String nombre, String email) {
        String sql = "DELETE FROM clientes WHERE cliente_id = ? AND email = ?";
        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}