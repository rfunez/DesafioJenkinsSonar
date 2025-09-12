package com.example.bank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100))";
        try (Connection conn = App.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void insertUser(String name) throws SQLException {
        String sql = "INSERT INTO users(name) VALUES (?)";
        try (Connection conn = App.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    public List<String> getAllUsers() throws SQLException {
        List<String> users = new ArrayList<>();
        String sql = "SELECT name FROM users";
        try (Connection conn = App.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(rs.getString("name"));
            }
        }
        return users;
    }
}
