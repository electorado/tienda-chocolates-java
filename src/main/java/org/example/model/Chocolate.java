package org.example.model;

/**
 * Representa un producto de tipo chocolate en el inventario de la tienda.
 * Cada chocolate se identifica de forma única por un ID generado a partir de su origen y porcentaje de cacao.
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class Chocolate {

    private String idProducto;
    private String origen;
    private int porcentajeCacao;
    private double precio;
    private int stock;

    /**
     * Constructor por defecto.
     */
    public Chocolate() {
    }

    /**
     * Constructor para crear un nuevo chocolate.
     * El ID del producto se genera automáticamente en base al origen y el porcentaje de cacao.
     *
     * @param origen          País o región de origen del cacao.
     * @param porcentajeCacao Porcentaje de cacao del chocolate.
     * @param precio          Precio unitario del producto.
     * @param stock           Cantidad de unidades disponibles en el inventario.
     */
    public Chocolate(String origen, int porcentajeCacao, double precio, int stock) {
        this.origen = origen;
        this.porcentajeCacao = porcentajeCacao;
        this.precio = precio;
        this.stock = stock;

        String origenCorto = origen.length() < 3 ? origen : origen.substring(0, 3);
        this.idProducto = (origenCorto + porcentajeCacao).toUpperCase();
    }

    /**
     * Constructor para crear un objeto Chocolate a partir de datos existentes (por ejemplo, desde la base de datos).
     *
     * @param idProducto      El ID único del producto.
     * @param origen          País o región de origen del cacao.
     * @param porcentajeCacao Porcentaje de cacao del chocolate.
     * @param precio          Precio unitario del producto.
     * @param stock           Cantidad de unidades disponibles en el inventario.
     */
    public Chocolate(String idProducto, String origen, int porcentajeCacao, double precio, int stock) {
        this.idProducto = idProducto;
        this.origen = origen;
        this.porcentajeCacao = porcentajeCacao;
        this.precio = precio;
        this.stock = stock;
    }

    /**
     * Devuelve el ID único del producto.
     * @return El ID del producto.
     */
    public String getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el ID del producto.
     * @param idProducto El nuevo ID del producto.
     */
    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Devuelve el país de origen del chocolate.
     * @return El origen del chocolate.
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Establece el país de origen del chocolate.
     * @param origen El nuevo origen del chocolate.
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * Devuelve el porcentaje de cacao.
     * @return El porcentaje de cacao.
     */
    public int getPorcentajeCacao() {
        return porcentajeCacao;
    }

    /**
     * Establece el porcentaje de cacao.
     * @param porcentajeCacao El nuevo porcentaje de cacao.
     */
    public void setPorcentajeCacao(int porcentajeCacao) {
        this.porcentajeCacao = porcentajeCacao;
    }

    /**
     * Devuelve el precio unitario del chocolate.
     * @return El precio del chocolate.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio unitario del chocolate.
     * @param precio El nuevo precio del chocolate.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Devuelve la cantidad de stock disponible.
     * @return El stock disponible.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece la cantidad de stock disponible.
     * @param stock El nuevo stock disponible.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Devuelve una representación en formato de texto del objeto Chocolate.
     * @return Una cadena con los detalles del chocolate.
     */
    @Override
    public String toString() {
        return "Chocolate: ID: " + idProducto +
                " | Origen: " + origen +
                " | Cacao: " + porcentajeCacao +
                "% | Precio: " + String.format("%.2f", precio) +
                "€ | Stock: " + stock;
    }

    /**
     * Compara este chocolate con otro objeto para ver si son iguales.
     * La comparación se basa en el ID del producto (ignorando mayúsculas y minúsculas).
     *
     * @param obj El objeto a comparar.
     * @return {@code true} si los chocolates son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chocolate otro) {
            return this.idProducto.equalsIgnoreCase(otro.idProducto);
        }
        return false;
    }
}