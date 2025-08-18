package com.pahanaedu.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.pahanaedu.dao.DBConnectionFactory;
import com.pahanaedu.dao.ProductDAO;
import com.pahanaedu.model.Product;

public class ProductService {
     
    private static ProductService instance;
    private ProductDAO productDAO;

    private ProductService() {
        this.productDAO = new ProductDAO(); // ✅ FIXED
    }


    public static ProductService getInstance() {
        if (instance == null) {
            synchronized (ProductService.class) {
                if (instance == null) {
                    instance = new ProductService();
                }
            }
        }
        return instance;
    }

    public void addProduct(Product product) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            productDAO.addProduct(product, conn);
        }
    }

    public int addProductAndGetId(Product product) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return productDAO.addProductAndGetId(product, conn);
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return productDAO.getAllProducts(conn);
        }
    }

    public Product getProductById(int productId) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return productDAO.getProductById(productId, conn);
        }
    }

    public void updateProduct(Product product) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            productDAO.updateProduct(product, conn);
        }
    }

    public void deleteProduct(int productId) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            productDAO.deleteProduct(productId, conn);
        }
    }

    public List<Product> getProductsByCategory(String category) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return productDAO.getProductsByCategory(category, conn);
        }
    }

    public List<String> getAllCategories() throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return productDAO.getAllCategories(conn);
        }
    }

    public List<Product> searchProductsByNameOrCategory(String keyword) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return productDAO.searchProductsByNameOrCategory(keyword, conn);
        }
    }

    public List<Product> searchProductsByNameOrCategoryAndCategory(String keyword, String category) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return productDAO.searchProductsByNameOrCategoryAndCategory(keyword, category, conn);
        }
    }

    public int getTotalProducts(Connection conn) throws SQLException {
        return productDAO.countProducts(conn);
    }
}
