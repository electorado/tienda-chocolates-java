package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/tienda_chocolate";
    private static final String USER = "postgres";
    private static final String PASSWORD = "p4nKdr3s%1977";

    public static Connection conectar(){
        try{
            return DriverManager.getConnection(URL,USER,PASSWORD);
        }catch(SQLException e){
            throw new RuntimeException("Error de conexión", e);
        }
    }
}
