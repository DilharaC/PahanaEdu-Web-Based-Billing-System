
package com.pahanaedu.controllertest;

import com.pahanaedu.controller.AdminDashboardController;
import com.pahanaedu.dao.AuditLogDAO;
import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.model.Bill;
import com.pahanaedu.service.CustomerService;
import com.pahanaedu.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class AdminDashboardControllerTest {

    private AdminDashboardController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    // Mocks for services/DAOs
    private ProductService productService;
    private CustomerService customerService;
    private AuditLogDAO auditLogDAO;
    private BillDAO billDAO;

    @BeforeEach
    void setUp() {
        controller = new AdminDashboardController();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        productService = mock(ProductService.class);
        customerService = mock(CustomerService.class);
        auditLogDAO = mock(AuditLogDAO.class);
        billDAO = mock(BillDAO.class);

        // inject mocks into controller
        controller.setProductService(productService);
        controller.setCustomerService(customerService);
        controller.setAuditLogDAO(auditLogDAO);
        controller.setBillDAO(billDAO);
    }

   

    @Test
    void testForwardToDashboardWhenAdmin() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("staff")).thenReturn("adminUser");
        when(session.getAttribute("role")).thenReturn("admin");
        when(request.getParameter("action")).thenReturn("dashboard");
        when(request.getRequestDispatcher("/WEB-INF/view/adminDashboard.jsp"))
                .thenReturn(dispatcher);

        // stub service results to avoid null
        when(productService.getTotalProducts(any())).thenReturn(10);
        when(customerService.getTotalCustomers(any())).thenReturn(5);
        when(billDAO.getLast5Bills(any())).thenReturn(Collections.<Bill>emptyList());

        controller.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }
    
    

//    @Test
//    void testForwardToAllTransactions() throws Exception {
//        when(request.getSession(false)).thenReturn(session);
//        when(session.getAttribute("staff")).thenReturn("adminUser");
//        when(session.getAttribute("role")).thenReturn("admin");
//        when(request.getParameter("action")).thenReturn("allTransactions");
//        when(request.getRequestDispatcher("/WEB-INF/view/allTransactions.jsp"))
//                .thenReturn(dispatcher);
//
//        when(billDAO.getAllBills(any())).thenReturn(Collections.<Bill>emptyList());
//
//        controller.doGet(request, response);
//
//        verify(dispatcher).forward(request, response);
//    }
//    @Test
//    void testRedirectsToLoginIfNoSession() throws Exception {
//        when(request.getSession(false)).thenReturn(null);
//
//        controller.doGet(request, response);
//
//        verify(response).sendRedirect(contains("/StaffLogin.jsp"));
//    }
}
