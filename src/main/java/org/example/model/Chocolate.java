package org.example.model;

/**
 * Clase que representa un producto(chocolate) de la tienda.
 * Adaptado para usarse con base de datos.
 */
public class Chocolate {

    private String idProducto;
    private String origen;
    private int porcentajeCacao;
    private double precio;
    private int stock;

    public Chocolate() {}

    public Chocolate(String origen, int porcentajeCacao, double precio, int stock) {
        this.origen = origen;
        this.porcentajeCacao = porcentajeCacao;
        this.precio = precio;
        this.stock = stock;

        String origenCorto = origen.length() < 3 ? origen : origen.substring(0, 3);
        this.idProducto = (origenCorto + porcentajeCacao).toUpperCase();
    }

    public Chocolate(Int id,, String origen, int porcentajeCacao, double precio, int stock) {
        this.idProducto = idProducto;
        this.origen = origen;
        this.porcentajeCacao = porcentajeCacao;
        this.precio = precio;
        this.stock = stock;
    }


    // --- Getters y Setters ---
    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public int getPorcentajeCacao() { return porcentajeCacao; }
    public void setPorcentajeCacao(int porcentajeCacao) { this.porcentajeCacao = porcentajeCacao; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "Chocolate: ID: " + idProducto + " | Origen: " + origen + " | Cacao: " + porcentajeCacao +
                "% | Precio: " + String.format("%.2f", precio) + "€ | Stock: " + stock;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chocolate otro) {
            return this.idProducto.equalsIgnoreCase(otro.idProducto);
        }
        return false;
    }
}