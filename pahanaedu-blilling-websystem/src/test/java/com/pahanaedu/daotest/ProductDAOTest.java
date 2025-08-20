package com.pahanaedu.daotest;

import com.pahanaedu.dao.DBConnection;
import com.pahanaedu.dao.ProductDAO;
import com.pahanaedu.model.Product;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDAOTest {

    private ProductDAO dao;
    private Product product;
    private int productId;

    @BeforeEach
    void setup() throws Exception {
        dao = new ProductDAO();
        try (Connection conn = DBConnection.getConnection()) {
            // create a fresh product for testing
            product = new Product(0, "JUnit Book", "Test description", "Books", 10.5, 5, true);
            productId = dao.addProductAndGetId(product, conn);  // insert & get generated ID
            product.setProductId(productId);
        }
    }

    @AfterEach
    void cleanup() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            dao.deleteProduct(productId, conn);  // cleanup test product
        }
    }

    
    
    @Test
    void testSearchProductsByNameOrCategory() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            var results = dao.searchProductsByNameOrCategory("JUnit", conn);
            assertFalse(results.isEmpty());
            assertTrue(results.stream().anyMatch(p -> p.getProductId() == productId));
        }
    }
    
    
    
   
    @Test
    void testGetAllProducts() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            assertTrue(dao.getAllProducts(conn).size() > 0);
        }
    }

    
    @Test
    void testUpdateProduct() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            product.setName("JUnit Book Updated");
            product.setPrice(15.0);
            boolean updated = dao.updateProduct(product, conn);

            assertTrue(updated);

            Product updatedProduct = dao.getProductById(productId, conn);
            assertEquals("JUnit Book Updated", updatedProduct.getName());
            assertEquals(15.0, updatedProduct.getPrice());
        }
    }
    
    @Test
    void testGetProductById() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            Product fetched = dao.getProductById(productId, conn);
            assertNotNull(fetched);
            assertEquals("JUnit Book", fetched.getName());
        }
    }
    
  

    
}