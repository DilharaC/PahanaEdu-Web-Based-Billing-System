package com.phanaedu.modeltest;

import com.pahanaedu.model.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    void testAllArgsConstructor() {
        Customer customer = new Customer(1, "John Doe", "john@example.com",
                "1234567890", "123 Main St", true);

        assertEquals(1, customer.getCustomerId());
        assertEquals("John Doe", customer.getName());
        assertEquals("john@example.com", customer.getEmail());
        assertEquals("1234567890", customer.getPhone());
        assertEquals("123 Main St", customer.getAddress());
        assertTrue(customer.isActive());
    }

    @Test
    void testDefaultConstructorAndSetters() {
        Customer customer = new Customer();
        customer.setCustomerId(2);
        customer.setName("Jane Smith");
        customer.setEmail("jane@example.com");
        customer.setPhone("9876543210");
        customer.setAddress("456 High St");
        customer.setActive(false);

        assertEquals(2, customer.getCustomerId());
        assertEquals("Jane Smith", customer.getName());
        assertEquals("jane@example.com", customer.getEmail());
        assertEquals("9876543210", customer.getPhone());
        assertEquals("456 High St", customer.getAddress());
        assertFalse(customer.isActive());
    }

    @Test
    void testActiveToggle() {
        Customer customer = new Customer();
        customer.setActive(true);
        assertTrue(customer.isActive());

        customer.setActive(false);
        assertFalse(customer.isActive());
    }
}
