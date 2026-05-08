package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Chocolate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChocolateDAO {

    public void insertarChocolate(Chocolate chocolate) {
        String sql = "INSERT INTO producto (producto_id, origen, porcentaje_cacao, precio, stock) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, chocolate.getIdProducto());
            ps.setString(2, chocolate.getOrigen());
            ps.setInt(3, chocolate.getPorcentajeCacao());
            ps.setDouble(4, chocolate.getPrecio());
            ps.setInt(5, chocolate.getStock());

            ps.executeUpdate();
            System.out.println("Chocolate insertado correctamente");

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar chocolate", e);
        }
    }

    public List<Chocolate> consultarChocolate() {
        List<Chocolate> chocolates = new ArrayList<>();
        String sql = "SELECT * FROM producto ORDER BY producto_id";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Chocolate chocolate = new Chocolate(
                        rs.getString("producto_id"),
                        rs.getString("origen"),
                        rs.getInt("porcentaje_cacao"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );
                chocolates.add(chocolate);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar chocolates", e);
        }

        return chocolates;
    }

    public Chocolate buscarPorId(String idProducto) {
        String sql = "SELECT * FROM producto WHERE producto_id = ?";
        Chocolate chocolate = null;

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idProducto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    chocolate = new Chocolate(
                            rs.getString("producto_id"),
                            rs.getString("origen"),
                            rs.getInt("porcentaje_cacao"),
                            rs.getDouble("precio"),
                            rs.getInt("stock")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar chocolate por ID", e);
        }

        return chocolate;
    }

    public void modificarChocolate(Chocolate chocolate) {
        String sql = "UPDATE producto SET origen = ?, porcentaje_cacao = ?, precio = ?, stock = ? WHERE producto_id = ?";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, chocolate.getOrigen());
            ps.setInt(2, chocolate.getPorcentajeCacao());
            ps.setDouble(3, chocolate.getPrecio());
            ps.setInt(4, chocolate.getStock());
            ps.setString(5, chocolate.getIdProducto());

            ps.executeUpdate();
            System.out.println("Chocolate modificado/añadido correctamente");

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar chocolate", e);
        }
    }

    public List<Chocolate> buscarPorOrigenYCacao(String origen, int porcentajeCacao) {
        List<Chocolate> chocolates = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE origen ILIKE ? AND porcentaje_cacao = ?";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + origen + "%");
            ps.setInt(2, porcentajeCacao);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Chocolate chocolate = new Chocolate(
                            rs.getString("producto_id"),
                            rs.getString("origen"),
                            rs.getInt("porcentaje_cacao"),
                            rs.getDouble("precio"),
                            rs.getInt("stock")
                    );
                    chocolates.add(chocolate);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar chocolates por origen y cacao", e);
        }

        return chocolates;
    }
}