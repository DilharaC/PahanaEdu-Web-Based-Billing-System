package com.pahanaedu.daotest;

import com.pahanaedu.dao.StaffDAO;
import com.pahanaedu.model.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class StaffDAOTest {

    private StaffDAO staffDAO;

    @BeforeEach
    void setUp() {
        staffDAO = new StaffDAO();
    }

//    @Test
//    void testAddStaffSuccess() {
//        Staff newStaff = new Staff();
//        newStaff.setUsername("testuser123");
//        newStaff.setPassword("testpass");
//        newStaff.setFullName("Test User");
//        newStaff.setEmail("testuser@example.com");
//        newStaff.setPhone("0771345567");
//        newStaff.setNic("999999999V");
//        newStaff.setRole("staff");
//        newStaff.setStatus("active");
//
//        assertDoesNotThrow(() -> staffDAO.addStaff(newStaff));
//
//        Staff staff = staffDAO.login("testuser", "testpass");
//        assertNotNull(staff, "Newly added staff should be able to log in");
//        assertEquals("testuser", staff.getUsername(), "Inserted staff username should match");
//    }
    
    
    
    
//    @Test
//    void testLoginWithWrongPassword_ShouldFail() {
//        Staff staff = staffDAO.login("dilhara", "12345");
//
//        // WRONG expectation on purpose → this will FAIL (red)
//        assertNotNull(staff, "This should fail because wrong password must not login");
//    }
    
    
    @Test
    void testLoginWithCorrectCredentials() {
        Staff staff = staffDAO.login("dilhara", "1234");
        assertNotNull(staff, "Login should succeed with valid username & password");
        assertEquals("dilhara", staff.getUsername(), "Username should match DB record");
    }

   
   
  
}
