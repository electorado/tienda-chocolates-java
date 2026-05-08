package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Chocolate;
import org.example.model.Cliente;
import org.example.model.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    public void insertarVenta(Venta venta) {
        String sqlVenta = "INSERT INTO venta (cliente_id, total_venta) VALUES (?, ?)";
        String sqlDetalle = "INSERT INTO detalle_venta (venta_id, producto_id, cantidad, subtotal) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.conectar()) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement psVenta = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)
            ) {
                psVenta.setInt(1, venta.getCliente().getClienteId());
                psVenta.setDouble(2, venta.getImporteTotal());
                psVenta.executeUpdate();

                int ventaId;

                try (ResultSet rs = psVenta.getGeneratedKeys()) {
                    if (rs.next()) {
                        ventaId = rs.getInt(1);
                    } else {
                        conn.rollback();
                        throw new SQLException("No se pudo obtener el ID de la venta generada");
                    }
                }

                try (PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle)) {
                    List<Chocolate> chocolates = venta.getChocolatesVendidos();
                    List<String> productosProcesados = new ArrayList<>();

                    for (Chocolate chocolate : chocolates) {
                        if (!productosProcesados.contains(chocolate.getIdProducto())) {
                            int cantidad = 0;

                            for (Chocolate c : chocolates) {
                                if (c.getIdProducto().equals(chocolate.getIdProducto())) {
                                    cantidad++;
                                }
                            }

                            double subtotal = cantidad * chocolate.getPrecio();

                            psDetalle.setInt(1, ventaId);
                            psDetalle.setString(2, chocolate.getIdProducto());
                            psDetalle.setInt(3, cantidad);
                            psDetalle.setDouble(4, subtotal);
                            psDetalle.executeUpdate();

                            productosProcesados.add(chocolate.getIdProducto());
                        }
                    }
                }

                conn.commit();
                System.out.println("Venta insertada correctamente");

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar venta", e);
        }
    }

    public List<Venta> consultarVentas() {
        List<Venta> ventas = new ArrayList<>();

        String sql = """
                SELECT v.venta_id, v.fecha_venta, v.total_venta,
                       c.cliente_id, c.nombre, c.email, c.telefono
                FROM venta v
                JOIN clientes c ON v.cliente_id = c.cliente_id
                ORDER BY v.fecha_venta
                """;

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

                Venta venta = new Venta();
                venta.setIdVenta(rs.getInt("venta_id"));
                venta.setCliente(cliente);

                Timestamp timestamp = rs.getTimestamp("fecha_venta");
                if (timestamp != null) {
                    venta.setFechaVenta(timestamp.toLocalDateTime().toLocalDate());
                }

                venta.setChocolatesVendidos(obtenerChocolatesPorVenta(rs.getInt("venta_id"), conn));
                ventas.add(venta);
            }
            ps.close();
            rs.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar ventas", e);
        }

        return ventas;
    }

    public List<Venta> buscarVentasPorCliente(Cliente cliente) {
        List<Venta> ventas = new ArrayList<>();

        String sql = """
                SELECT venta_id, fecha_venta
                FROM venta
                WHERE cliente_id = ?
                ORDER BY fecha_venta
                """;

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cliente.getClienteId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Venta venta = new Venta();
                    venta.setIdVenta(rs.getInt("venta_id"));
                    venta.setCliente(cliente);

                    Timestamp timestamp = rs.getTimestamp("fecha_venta");
                    if (timestamp != null) {
                        venta.setFechaVenta(timestamp.toLocalDateTime().toLocalDate());
                    }

                    venta.setChocolatesVendidos(obtenerChocolatesPorVenta(rs.getInt("venta_id"), conn));
                    ventas.add(venta);
                }
                ps.close();
                rs.close();
                conn.close();
            }


        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar ventas por cliente", e);
        }

        return ventas;
    }

    private List<Chocolate> obtenerChocolatesPorVenta(int ventaId, Connection conn) {
        List<Chocolate> chocolates = new ArrayList<>();

        String sql = """
                SELECT p.producto_id, p.origen, p.porcentaje_cacao, p.precio, p.stock, dv.cantidad
                FROM detalle_venta dv
                JOIN productos p ON dv.producto_id = p.producto_id
                WHERE dv.venta_id = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ventaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int cantidad = rs.getInt("cantidad");

                    for (int i = 0; i < cantidad; i++) {
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
                ps.close();
                rs.close();
                conn.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener chocolates de la venta", e);
        }

        return chocolates;
    }

    public void mostrarTop5ProductosMasVendidos() {
        String sql = """
            SELECT 
                p.producto_id,
                p.origen,
                p.porcentaje_cacao,
                SUM(dv.cantidad) AS total_unidades_vendidas,
                SUM(dv.subtotal) AS total_facturado
            FROM detalle_venta dv
            JOIN producto p ON dv.producto_id = p.producto_id
            GROUP BY p.producto_id, p.origen, p.porcentaje_cacao
            ORDER BY total_unidades_vendidas DESC, total_facturado DESC
            LIMIT 5
            """;

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- TOP 5 PRODUCTOS MÁS VENDIDOS ---");

            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getString("producto_id") +
                                " | Origen: " + rs.getString("origen") +
                                " | % Cacao: " + rs.getInt("porcentaje_cacao") +
                                " | Unidades vendidas: " + rs.getInt("total_unidades_vendidas") +
                                " | Facturación: " + String.format("%.2f", rs.getDouble("total_facturado")) + "€"
                );
            }
            ps.close();
            rs.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el top 5 de productos más vendidos", e);
        }
    }

    public void mostrarTop5ClientesDelMes() {
        String sql = """
            SELECT 
                c.cliente_id,
                c.nombre,
                c.email,
                COUNT(v.venta_id) AS numero_compras,
                SUM(v.total_venta) AS total_gastado
            FROM venta v
            JOIN cliente c ON v.cliente_id = c.cliente_id
            WHERE DATE_TRUNC('month', v.fecha_venta) = DATE_TRUNC('month', CURRENT_DATE)
            GROUP BY c.cliente_id, c.nombre, c.email
            ORDER BY total_gastado DESC, numero_compras DESC
            LIMIT 5
            """;

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- TOP 5 CLIENTES DEL MES ---");

            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("cliente_id") +
                                " | Nombre: " + rs.getString("nombre") +
                                " | Email: " + rs.getString("email") +
                                " | Compras: " + rs.getInt("numero_compras") +
                                " | Total gastado: " + String.format("%.2f", rs.getDouble("total_gastado")) + "€"
                );
            }
            ps.close();
            rs.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el top 5 de clientes del mes", e);
        }
    }
}