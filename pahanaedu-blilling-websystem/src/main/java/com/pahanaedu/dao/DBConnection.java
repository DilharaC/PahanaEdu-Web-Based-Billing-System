package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/pahanaedudb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // load driver once
            System.out.println("Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found!", e);
        }
    }

    // This method returns a **new connection each time**
    public static Connection getConnection() throws SQLException {
        System.out.println("Opening a new DB connection...");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}