package com.phanaedu.modeltest;

import com.pahanaedu.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void testAllArgsConstructor() {
        Product product = new Product(1, "Book", "Classic novel", "Books",
                19.99, 10, true);

        assertEquals(1, product.getProductId());
        assertEquals("Book", product.getName());
        assertEquals("Classic novel", product.getDescription());
        assertEquals("Books", product.getCategory());
        assertEquals(19.99, product.getPrice());
        assertEquals(10, product.getQuantity());
        assertTrue(product.isAvailable());
    }

    @Test
    void testConstructorWithoutDescription() {
        Product product = new Product(2, "Pen", "Stationery", 1.50, 100, true);

        assertEquals(2, product.getProductId());
        assertEquals("Pen", product.getName());
        assertNull(product.getDescription()); // description should be null
        assertEquals("Stationery", product.getCategory());
        assertEquals(1.50, product.getPrice());
        assertEquals(100, product.getQuantity());
        assertTrue(product.isAvailable());
    }

    @Test
    void testDefaultConstructorAndSetters() {
        Product product = new Product();
        product.setProductId(3);
        product.setName("Notebook");
        product.setDescription("200 pages ruled");
        product.setCategory("Stationery");
        product.setPrice(3.25);
        product.setQuantity(50);
        product.setAvailable(false);

        assertEquals(3, product.getProductId());
        assertEquals("Notebook", product.getName());
        assertEquals("200 pages ruled", product.getDescription());
        assertEquals("Stationery", product.getCategory());
        assertEquals(3.25, product.getPrice());
        assertEquals(50, product.getQuantity());
        assertFalse(product.isAvailable());
    }
}
