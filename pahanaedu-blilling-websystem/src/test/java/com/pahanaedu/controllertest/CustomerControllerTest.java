package com.pahanaedu.controllertest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahanaedu.controller.CustomerController;
import com.pahanaedu.model.Customer;
import com.pahanaedu.model.Staff;
import com.pahanaedu.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerControllerTest {

    private CustomerController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private CustomerService mockService;

    @BeforeEach
    void setUp() throws Exception {
        controller = new CustomerController();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        mockService = mock(CustomerService.class);

        controller.setCustomerService(mockService);
    }
    
    
    
    
    @Test
    void testListCustomers() throws Exception {
        when(request.getParameter("action")).thenReturn("list");
        when(mockService.getAllCustomers()).thenReturn(Arrays.asList(
            new Customer(1, "Alice", "alice@example.com", "0771234567", "123 Main St", true)
        ));
        when(request.getRequestDispatcher("WEB-INF/view/listCustomers.jsp")).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(request).setAttribute(eq("customers"), anyList());
        verify(dispatcher).forward(request, response);
    }
    
    
   
    
//    @Test
//    void testAddCustomer() throws Exception {
//        when(request.getParameter("action")).thenReturn("add");
//        when(request.getParameter("name")).thenReturn("Alice");
//        when(request.getParameter("email")).thenReturn("alice@example.com");
//        when(request.getParameter("phone")).thenReturn("0771234567");
//        when(request.getParameter("address")).thenReturn("123 Main St");
//        when(request.getSession(false)).thenReturn(session);
//
//        
//        Staff staff = new Staff();
//        staff.setStaffId(1);
//        staff.setFullName("Admin User");
//        when(session.getAttribute("staff")).thenReturn(staff);
//
//        when(mockService.addCustomer(any(Customer.class))).thenReturn(1);
//
//        controller.doPost(request, response);
//
//        verify(response).sendRedirect(contains("Customer?action=list&success=Customer added successfully!"));
//    }

  
    
 
}
