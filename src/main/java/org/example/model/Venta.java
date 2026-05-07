package org.example.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una transacción de venta en la tienda de chocolates.
 * Asocia un cliente con una lista de productos (chocolates) adquiridos y la fecha en que se realizó.
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class Venta {

    private int idVenta;
    private Cliente cliente;
    private List<Chocolate> chocolatesVendidos;
    private LocalDate fechaVenta;

    /**
     * Constructor por defecto.
     * Inicializa la lista de chocolates vendidos vacía y la fecha de venta al día actual.
     */
    public Venta() {
        this.chocolatesVendidos = new ArrayList<>();
        this.fechaVenta = LocalDate.now();
    }

    /**
     * Constructor para crear una nueva venta.
     * La fecha de la venta se establece automáticamente al día actual.
     *
     * @param cliente            El cliente que realiza la compra.
     * @param chocolatesVendidos La lista de productos adquiridos.
     */
    public Venta(Cliente cliente, List<Chocolate> chocolatesVendidos) {
        this.cliente = cliente;
        this.chocolatesVendidos = chocolatesVendidos != null ? chocolatesVendidos : new ArrayList<>();
        this.fechaVenta = LocalDate.now();
    }

    /**
     * Constructor para reconstruir una venta existente desde un origen de datos.
     *
     * @param idVenta            El identificador único de la venta.
     * @param cliente            El cliente asociado a la venta.
     * @param fechaVenta         La fecha en la que se realizó la venta.
     * @param chocolatesVendidos La lista de productos adquiridos.
     */
    public Venta(int idVenta, Cliente cliente, LocalDate fechaVenta, List<Chocolate> chocolatesVendidos) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.fechaVenta = fechaVenta;
        this.chocolatesVendidos = chocolatesVendidos != null ? chocolatesVendidos : new ArrayList<>();
    }

    /**
     * Devuelve el identificador único de la venta.
     * @return El ID de la venta.
     */
    public int getIdVenta() {
        return idVenta;
    }

    /**
     * Establece el identificador único de la venta.
     * @param idVenta El nuevo ID de la venta.
     */
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    /**
     * Devuelve el cliente asociado a esta venta.
     * @return El cliente.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente que realizó la compra.
     * @param cliente El cliente asociado.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Devuelve la lista de chocolates adquiridos en esta venta.
     * @return Una lista de productos.
     */
    public List<Chocolate> getChocolatesVendidos() {
        return chocolatesVendidos;
    }

    /**
     * Establece la lista de productos adquiridos.
     * @param chocolatesVendidos La nueva lista de productos.
     */
    public void setChocolatesVendidos(List<Chocolate> chocolatesVendidos) {
        this.chocolatesVendidos = chocolatesVendidos != null ? chocolatesVendidos : new ArrayList<>();
    }

    /**
     * Devuelve la fecha en que se realizó la venta.
     * @return La fecha de la venta.
     */
    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    /**
     * Establece la fecha de la venta.
     * @param fechaVenta La nueva fecha de la venta.
     */
    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    /**
     * Calcula el importe total de la venta sumando los precios de todos los productos en la lista.
     *
     * @return El coste total de la venta.
     */
    public double getImporteTotal() {
        double total = 0;

        if (chocolatesVendidos != null) {
            for (Chocolate chocolate : chocolatesVendidos) {
                total += chocolate.getPrecio();
            }
        }

        return total;
    }

    /**
     * Devuelve una representación en formato texto (ticket) de la venta.
     *
     * @return Un resumen estructurado con detalles de la venta, cliente, productos y total.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder ticket = new StringBuilder();

        ticket.append("--- Venta #").append(idVenta).append(" --- [");

        if (fechaVenta != null) {
            ticket.append(fechaVenta.format(formatter));
        } else {
            ticket.append("Sin fecha");
        }

        ticket.append("]\n");

        if (cliente != null) {
            ticket.append("Cliente: ")
                    .append(cliente.getNombre())
                    .append(" (Email: ")
                    .append(cliente.getEmail())
                    .append(")\n");
        }

        ticket.append("Productos:\n");

        if (chocolatesVendidos != null && !chocolatesVendidos.isEmpty()) {
            for (Chocolate chocolate : chocolatesVendidos) {
                ticket.append(" - ID: ")
                        .append(chocolate.getIdProducto())
                        .append(" | Origen: ")
                        .append(chocolate.getOrigen())
                        .append(" | Precio: ")
                        .append(String.format("%.2f", chocolate.getPrecio()))
                        .append("€\n");
            }
        } else {
            ticket.append(" No hay productos en la venta.\n");
        }

        ticket.append("IMPORTE TOTAL: ")
                .append(String.format("%.2f", getImporteTotal()))
                .append("€\n");

        return ticket.toString();
    }
}