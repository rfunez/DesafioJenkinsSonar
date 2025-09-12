package com.example.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "testuser";
    private static final String PASSWORD = "testpass";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Conexión a MySQL establecida!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
