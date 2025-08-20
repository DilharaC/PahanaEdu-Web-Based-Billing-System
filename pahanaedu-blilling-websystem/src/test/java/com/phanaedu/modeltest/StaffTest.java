package com.phanaedu.modeltest;

import com.pahanaedu.model.Staff;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StaffTest {

    @Test
    void testSettersAndGetters() {
        Staff staff = new Staff();

        staff.setStaffId(101);
        staff.setUsername("dilhara");
        staff.setPassword("securePass");
        staff.setFullName("Dilhara Perera");
        staff.setEmail("dilhara@example.com");
        staff.setPhone("0771234567");
        staff.setNic("991234567V");
        staff.setJobTitle("Manager");
        staff.setRole("Admin");
        staff.setStatus("active");

        assertEquals(101, staff.getStaffId());
        assertEquals("dilhara", staff.getUsername());
        assertEquals("securePass", staff.getPassword());
        assertEquals("Dilhara Perera", staff.getFullName());
        assertEquals("dilhara@example.com", staff.getEmail());
        assertEquals("0771234567", staff.getPhone());
        assertEquals("991234567V", staff.getNic());
        assertEquals("Manager", staff.getJobTitle());
        assertEquals("Admin", staff.getRole());
        assertEquals("active", staff.getStatus());
    }

    @Test
    void testUpdateFields() {
        Staff staff = new Staff();
        staff.setUsername("oldUser");
        assertEquals("oldUser", staff.getUsername());

        staff.setUsername("newUser");
        assertEquals("newUser", staff.getUsername());
    }

    @Test
    void testNullValues() {
        Staff staff = new Staff();
        staff.setEmail(null);
        staff.setPhone(null);

        assertNull(staff.getEmail());
        assertNull(staff.getPhone());
    }
}
