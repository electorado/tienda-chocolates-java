package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/tienda_chocolate";
    // Leemos las variables del sistema en lugar de exponerlas aquí
    private static final String USER = System.getenv("DB_TIENDA_USER");
    private static final String PASSWORD = System.getenv("DB_TIENDA_PASS");

    public static Connection conectar(){
        try{
            // Verificamos que no sean nulas para evitar errores confusos
            if (USER == null || PASSWORD == null) {
                throw new RuntimeException("Error: Las variables de entorno DB_TIENDA_USER o DB_TIENDA_PASS no están configuradas.");
            }
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexión", e);
        }
    }
}
