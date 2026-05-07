package org.example.model;

/**
 * Representa a un cliente de la tienda de chocolates.
 * Contiene información de contacto y un identificador único.
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class Cliente {

    private int id;
    private String nombre;
    private String email;
    private String telefono;

    /**
     * Constructor por defecto.
     */
    public Cliente() {
    }

    /**
     * Constructor para crear un nuevo cliente antes de ser insertado en la base de datos
     * (sin ID, ya que es auto-incremental).
     *
     * @param nombre   El nombre completo del cliente.
     * @param email    El correo electrónico del cliente (debe ser único).
     * @param telefono El número de teléfono del cliente.
     */
    public Cliente(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Constructor para reconstruir un cliente existente a partir de datos recuperados (por ejemplo, base de datos).
     *
     * @param id       El identificador único del cliente.
     * @param nombre   El nombre completo del cliente.
     * @param email    El correo electrónico del cliente.
     * @param telefono El número de teléfono del cliente.
     */
    public Cliente(int id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Devuelve el identificador único del cliente.
     * @return El ID del cliente.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del cliente.
     * @param clienteId El nuevo ID del cliente.
     */
    public void setId(int clienteId) {
        this.id = clienteId;
    }

    /**
     * Devuelve el identificador único del cliente (alias de {@link #getId()}).
     * @return El ID del cliente.
     */
    public int getClienteId() {
        return id;
    }

    /**
     * Establece el identificador único del cliente (alias de {@link #setId(int)}).
     * @param clienteId El nuevo ID del cliente.
     */
    public void setClienteId(int clienteId) {
        this.id = clienteId;
    }

    /**
     * Devuelve el nombre del cliente.
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     * @param nombre El nuevo nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el correo electrónico del cliente.
     * @return El email del cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del cliente.
     * @param email El nuevo email del cliente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve el número de teléfono del cliente.
     * @return El teléfono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del cliente.
     * @param telefono El nuevo teléfono del cliente.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve una representación en formato de texto del cliente.
     * @return Una cadena de texto con los detalles del cliente.
     */
    @Override
    public String toString() {
        return "Cliente: ID: " + id +
                " | Nombre: " + nombre +
                " | Tel: " + telefono +
                " | Email: " + email;
    }

    /**
     * Compara este cliente con otro para ver si son idénticos basándose en su ID.
     *
     * @param obj El objeto a comparar.
     * @return {@code true} si tienen el mismo ID, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cliente otro) {
            return this.id == otro.id;
        }
        return false;
    }
}