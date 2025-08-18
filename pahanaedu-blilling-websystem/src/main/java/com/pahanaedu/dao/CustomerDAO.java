package com.pahanaedu.dao;

import com.pahanaedu.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // ✅ No Singleton instance anymore
    public CustomerDAO() {}

    public int addCustomer(Customer customer, Connection conn) throws SQLException {
        String query = "INSERT INTO customer (name, email, phone, address, active, created_at) " +
                       "VALUES (?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getAddress());
            ps.setBoolean(5, customer.isActive());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
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
        String query = "DELETE FROM customer WHERE customer_id=?";
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

    public int countCustomers(Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM customer";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public List<Integer> getMonthlyNewCustomers(Connection conn, int months) throws SQLException {
        List<Integer> counts = new ArrayList<>();
        String sql = "SELECT COUNT(*) AS total " +
                     "FROM customer " +
                     "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL ? MONTH) " +
                     "GROUP BY YEAR(created_at), MONTH(created_at) " +
                     "ORDER BY YEAR(created_at), MONTH(created_at)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, months);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) counts.add(rs.getInt("total"));
            }
        }
        return counts;
    }

    public List<String> getLastMonthsLabels(Connection conn, int months) throws SQLException {
        List<String> labels = new ArrayList<>();
        String sql = "SELECT DATE_FORMAT(created_at, '%b %Y') AS month_label " +
                     "FROM customer " +
                     "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL ? MONTH) " +
                     "GROUP BY YEAR(created_at), MONTH(created_at) " +
                     "ORDER BY YEAR(created_at), MONTH(created_at)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, months);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) labels.add(rs.getString("month_label"));
            }
        }
        return labels;
    }
}
