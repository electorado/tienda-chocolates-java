package org.example.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a una venta en la tienda.
 * Adaptado a la estructura de la base de datos.
 */
public class Venta {
    private int idVenta;
    private Cliente cliente;
    private List<Chocolate> chocolatesVendidos;
    private LocalDate fechaVenta;

    public Venta() {
        this.chocolatesVendidos = new ArrayList<>();
    }

    public Venta(Cliente cliente, List<Chocolate> chocolatesVendidos) {
        this.cliente = cliente;
        this.chocolatesVendidos = chocolatesVendidos;
        this.fechaVenta = LocalDate.now();
    }

    public Venta(int idVenta, Cliente cliente, LocalDate fechaVenta, List<Chocolate> chocolatesVendidos) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.fechaVenta = fechaVenta;
        this.chocolatesVendidos = chocolatesVendidos;
    }

    // --- Getters y Setters ---
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<Chocolate> getChocolatesVendidos() { return chocolatesVendidos; }
    public void setChocolatesVendidos(List<Chocolate> chocolatesVendidos) { this.chocolatesVendidos = chocolatesVendidos; }

    public LocalDate getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDate fechaVenta) { this.fechaVenta = fechaVenta; }

    public double getImporteTotal() {
        double total = 0;
        if (chocolatesVendidos != null) {
            for (Chocolate choco : this.chocolatesVendidos) {
                total += choco.getPrecio();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String ticket = "--- Venta #" + idVenta + " --- [" + (fechaVenta != null ? fechaVenta.format(formatter) : "Sin fecha") + "]\n";
        if(cliente != null) {
            ticket += "Cliente: " + cliente.getNombre() + " (Email: " + cliente.getEmail() + ")\n";
        }
        ticket += "Productos:\n";
        if (chocolatesVendidos != null) {
            for (Chocolate choco : chocolatesVendidos) {
                ticket += "  - ID: " + choco.getIdProducto() + " | Origen: " + choco.getOrigen() + " | Precio: " + String.format("%.2f", choco.getPrecio()) + "€\n";
            }
        }
        ticket += "IMPORTE TOTAL: " + String.format("%.2f", getImporteTotal()) + "€\n";
        return ticket;
    }
}