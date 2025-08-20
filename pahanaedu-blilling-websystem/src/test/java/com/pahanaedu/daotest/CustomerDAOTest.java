package com.pahanaedu.daotest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.*;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.dao.DBConnectionFactory;
import com.pahanaedu.model.Customer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // so @BeforeAll can use non-static
public class CustomerDAOTest {

    private Connection conn;
    private CustomerDAO customerDAO;
    private Customer dummyCustomer;
    private int dummyId;

    @BeforeAll
    void setUp() throws Exception {
        conn = DBConnectionFactory.getConnection();
        customerDAO = new CustomerDAO();

        // Create a dummy customer for tests
        dummyCustomer = new Customer(0, "gang Silva",
                "gang@example.com",
                "0774401222",
                "10 Flower Road",
                true);

        dummyId = customerDAO.addCustomer(dummyCustomer, conn);
        dummyCustomer.setCustomerId(dummyId);
    }

    @AfterAll
    void tearDown() throws Exception {
        if (dummyId > 0) {
            customerDAO.deleteCustomer(dummyId, conn);
        }
        if (conn != null) conn.close();
    }

    @Test
    void testAddCustomerSuccess() throws SQLException {
        Customer newCustomer = new Customer(0, "Chriss Silva",
                "chriis@example.com",
                "0774414222",
                "99 Temple Rd",
                true);

        int id = customerDAO.addCustomer(newCustomer, conn);
        assertTrue(id > 0, "New customer should return generated ID");

        // cleanup this test’s customer
        customerDAO.deleteCustomer(id, conn);
    }
    

//   
      @Test
    void testGetDummyCustomerById() throws SQLException {
        Customer found = customerDAO.getCustomerById(dummyId, conn);
        assertNotNull(found, "Dummy customer should exist in DB");
        assertEquals("gang Silva", found.getName());
    }
    

   
//    @Test
//    void testAddCustomerWithDuplicatePhoneNumber() throws SQLException {
//        Customer duplicate = new Customer(0, "Bob Silva",
//                "bob@example.com",
//                "0774401222", // same phone as dummy
//                "22 Palm Street",
//                true);
//
//        int id = customerDAO.addCustomer(duplicate, conn);
//        assertEquals(-1, id, "Duplicate phone should return -1");
//    }
}