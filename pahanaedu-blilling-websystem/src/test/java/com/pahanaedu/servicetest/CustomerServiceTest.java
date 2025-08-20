package com.pahanaedu.servicetest;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.dao.DBConnectionFactory;
import com.pahanaedu.model.Customer;
import com.pahanaedu.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerDAO customerDAO;
    private Connection mockConn;

    @BeforeEach
    void setUp() {
        // fresh singleton each test (optional but cleaner)
        customerService = CustomerService.getInstance();
        customerDAO = mock(CustomerDAO.class);
        mockConn = mock(Connection.class);
    }

    
    
    @Test
  void testGetAllCustomers() throws Exception {
      try (MockedStatic<DBConnectionFactory> dbMock = mockStatic(DBConnectionFactory.class)) {
          dbMock.when(DBConnectionFactory::getConnection).thenReturn(mockConn);

          CustomerDAO daoSpy = mock(CustomerDAO.class);
          when(daoSpy.getAllCustomers(mockConn)).thenReturn(List.of(new Customer()));

          setPrivateDAO(customerService, daoSpy);

          List<Customer> customers = customerService.getAllCustomers();

          assertEquals(1, customers.size());
          verify(daoSpy).getAllCustomers(mockConn);
      }
  }
    
    
    
    
    @Test
    void testAddCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setName("Test User");

        try (MockedStatic<DBConnectionFactory> dbMock = mockStatic(DBConnectionFactory.class)) {
            dbMock.when(DBConnectionFactory::getConnection).thenReturn(mockConn);

            // spy DAO into service (hacky way, or use reflection if needed)
            CustomerDAO daoSpy = mock(CustomerDAO.class);
            when(daoSpy.addCustomer(eq(customer), eq(mockConn))).thenReturn(1);

            // temporarily swap DAO using reflection
            setPrivateDAO(customerService, daoSpy);

            int result = customerService.addCustomer(customer);

            assertEquals(1, result);
            verify(daoSpy).addCustomer(customer, mockConn);
        }
    }

   

    @Test
    void testGetCustomerById() throws Exception {
        Customer c = new Customer();
        c.setCustomerId(99);

        try (MockedStatic<DBConnectionFactory> dbMock = mockStatic(DBConnectionFactory.class)) {
            dbMock.when(DBConnectionFactory::getConnection).thenReturn(mockConn);

            CustomerDAO daoSpy = mock(CustomerDAO.class);
            when(daoSpy.getCustomerById(99, mockConn)).thenReturn(c);

            setPrivateDAO(customerService, daoSpy);

            Customer result = customerService.getCustomerById(99);

            assertEquals(99, result.getCustomerId());
            verify(daoSpy).getCustomerById(99, mockConn);
        }
    }
//
//    // === Helper to replace private DAO via reflection ===
    private void setPrivateDAO(CustomerService service, CustomerDAO mockDAO) {
        try {
            var field = CustomerService.class.getDeclaredField("customerDAO");
            field.setAccessible(true);
            field.set(service, mockDAO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
