package com.example.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    private static final String URL = "jdbc:mysql://test:3306/test_db";
    private static final String USER = "test";
    private static final String PASSWORD = "test";

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
