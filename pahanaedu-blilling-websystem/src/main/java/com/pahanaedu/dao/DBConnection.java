package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/pahanaedudb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static Connection connection = null;

    // Load driver once
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found!", e);
        }
    }

    // Private constructor to prevent instantiation
    private DBConnection() {}

    // Singleton getConnection
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            System.out.println("Opening a new DB connection...");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }
}