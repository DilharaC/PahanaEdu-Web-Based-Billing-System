package com.pahanaedu.dao;

import com.pahanaedu.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // ✅ Removed Singleton implementation
    public ProductDAO() {
        // public constructor
    }

    public void addProduct(Product product, Connection conn) throws SQLException {
        String query = "INSERT INTO product (name, description, category, price, quantity, available) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setString(3, product.getCategory());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getQuantity());
            ps.setBoolean(6, product.isAvailable());
            ps.executeUpdate();
        }
    }

    public int addProductAndGetId(Product product, Connection conn) throws SQLException {
        String query = "INSERT INTO product (name, description, category, price, quantity, available) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setString(3, product.getCategory());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getQuantity());
            ps.setBoolean(6, product.isAvailable());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
        }
    }

    public List<Product> getAllProducts(Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getBoolean("available")
                ));
            }
        }
        return products;
    }

    public Product getProductById(int productId, Connection conn) throws SQLException {
        String query = "SELECT * FROM product WHERE product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            rs.getBoolean("available")
                    );
                }
            }
        }
        return null;
    }

    public boolean updateProduct(Product product, Connection conn) throws SQLException {
        String query = "UPDATE product SET name=?, description=?, category=?, price=?, quantity=?, available=? WHERE product_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setString(3, product.getCategory());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getQuantity());
            ps.setBoolean(6, product.isAvailable());
            ps.setInt(7, product.getProductId());

            return ps.executeUpdate() > 0; // true if at least one row updated
        }
    }

    public void deleteProduct(int productId, Connection conn) throws SQLException {
        String query = "DELETE FROM product WHERE product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, productId);
            ps.executeUpdate();
        }
    }

    public List<Product> getProductsByCategory(String category, Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE category = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            rs.getBoolean("available")
                    ));
                }
            }
        }
        return products;
    }

    public List<String> getAllCategories(Connection conn) throws SQLException {
        List<String> categories = new ArrayList<>();
        String query = "SELECT DISTINCT category FROM product";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        }
        return categories;
    }

    public List<Product> searchProductsByNameOrCategory(String keyword, Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE LOWER(name) LIKE ? OR LOWER(category) LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            String likePattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, likePattern);
            ps.setString(2, likePattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            rs.getBoolean("available")
                    ));
                }
            }
        }
        return products;
    }

    public List<Product> searchProductsByNameOrCategoryAndCategory(String keyword, String category, Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE (LOWER(name) LIKE ? OR LOWER(category) LIKE ?) AND category = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            String likePattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, likePattern);
            ps.setString(2, likePattern);
            ps.setString(3, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            rs.getBoolean("available")
                    ));
                }
            }
        }
        return products;
    }

    public int countProducts(Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM product";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public List<String> getTopSellingProductNames(Connection conn, int limit) throws SQLException {
        List<String> names = new ArrayList<>();
        String sql = "SELECT p.name, SUM(bi.quantity) AS total_qty " +
                     "FROM bill_item bi JOIN product p ON bi.product_id = p.product_id " +
                     "GROUP BY p.product_id, p.name " +
                     "ORDER BY total_qty DESC LIMIT ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    names.add(rs.getString("name"));
                }
            }
        }
        return names;
    }

    public List<Integer> getTopSellingProductQuantities(Connection conn, int limit) throws SQLException {
        List<Integer> quantities = new ArrayList<>();
        String sql = "SELECT p.name, SUM(bi.quantity) AS total_qty " +
                     "FROM bill_item bi JOIN product p ON bi.product_id = p.product_id " +
                     "GROUP BY p.product_id, p.name " +
                     "ORDER BY total_qty DESC LIMIT ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    quantities.add(rs.getInt("total_qty"));
                }
            }
        }
        return quantities;
    }
}
