package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pahanaedu.model.Customer;

public class CustomerDAO {

    public void addCustomer(Customer customer, Connection conn) throws SQLException {
        String query = "INSERT INTO customer (name, email, phone, address, active) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getAddress());
            ps.setBoolean(5, customer.isActive());
            ps.executeUpdate();
        }
    }

    public List<Customer> getAllCustomers(Connection conn) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customer";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getBoolean("active")
                ));
            }
        }

        return customers;
    }

    public Customer getCustomerById(int customerId, Connection conn) throws SQLException {
        String query = "SELECT * FROM customer WHERE customer_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getBoolean("active")
                    );
                }
            }
        }
        return null;
    }

    public void updateCustomer(Customer customer, Connection conn) throws SQLException {
        String query = "UPDATE customer SET name=?, email=?, phone=?, address=?, active=? WHERE customer_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getAddress());
            ps.setBoolean(5, customer.isActive());
            ps.setInt(6, customer.getCustomerId());
            ps.executeUpdate();
        }
    }

    public void deleteCustomer(int customerId, Connection conn) throws SQLException {
        String query = "DELETE FROM customer WHERE customer_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
        }
    }

    public List<Customer> searchCustomersByPhone(String phone, Connection conn) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customer WHERE phone LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + phone + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    customers.add(new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getBoolean("active")
                    ));
                }
            }
        }

        return customers;
    }

    public int countCustomers() throws SQLException {
        String query = "SELECT COUNT(*) FROM customer";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}