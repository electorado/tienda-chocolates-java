package org.example.model;

/**
 * Clase que representa a un cliente de la tienda.
 * Adaptado a la estructura de la base de datos.
 */
public class Cliente {
    private int id;
    private String nombre;
    private String email;
    private String telefono;

    public Cliente() {}

    public Cliente(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public Cliente(int id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId() { return id; }
    public void setId(int clienteId) { this.id = clienteId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return "Cliente: " +
                "ID: " + id +
                " | Nombre: " + nombre +
                " | Tel: " + telefono +
                " | Email: " + email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cliente otro) {
            return this.id == otro.id;
        }
        return false;
    }
}