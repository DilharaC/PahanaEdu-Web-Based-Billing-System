package com.pahanaedu.controllertest;

import com.pahanaedu.controller.ProductController;
import com.pahanaedu.model.Product;
import com.pahanaedu.model.Staff;
import com.pahanaedu.service.AuditLogService;
import com.pahanaedu.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import javax.servlet.ServletException; 
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        controller = new ProductController();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    
    
    @Test
    void testAddProduct() throws Exception {
        Staff staff = new Staff();
        staff.setFullName("Admin");

        try (MockedStatic<ProductService> mockedProductService = Mockito.mockStatic(ProductService.class);
             MockedStatic<AuditLogService> mockedAuditLogService = Mockito.mockStatic(AuditLogService.class)) {

            ProductService ps = mock(ProductService.class);
            AuditLogService auditService = mock(AuditLogService.class);

            mockedProductService.when(ProductService::getInstance).thenReturn(ps);
            mockedAuditLogService.when(AuditLogService::getInstance).thenReturn(auditService);

            when(request.getParameter("action")).thenReturn("add");
            when(request.getParameter("name")).thenReturn("Test Product");
            when(request.getParameter("description")).thenReturn("Description");
            when(request.getParameter("category")).thenReturn("Books");
            when(request.getParameter("price")).thenReturn("100.0");
            when(request.getParameter("quantity")).thenReturn("10");
            when(request.getParameter("available")).thenReturn("true");
            when(request.getSession(false)).thenReturn(session);
            when(session.getAttribute("staff")).thenReturn(staff);

            when(ps.addProductAndGetId(any(Product.class))).thenReturn(1);

            when(request.getMethod()).thenReturn("POST");  // simulate POST
            controller.service(request, response);

            verify(ps).addProductAndGetId(any(Product.class));
            verify(auditService).logAction(any());
            verify(response).sendRedirect("Product?action=list");
        }
    }
   
    
    
//    @Test
//    void testDeleteProduct() throws Exception {
//        Product product = new Product();
//        product.setProductId(5);
//        product.setName("Book");
//        product.setCategory("Books");
//
//        Staff staff = new Staff();
//        staff.setFullName("Manager");
//
//        try (MockedStatic<ProductService> mockedProductService = Mockito.mockStatic(ProductService.class);
//             MockedStatic<AuditLogService> mockedAuditLogService = Mockito.mockStatic(AuditLogService.class)) {
//
//            ProductService ps = mock(ProductService.class);
//            AuditLogService auditService = mock(AuditLogService.class);
//
//            mockedProductService.when(ProductService::getInstance).thenReturn(ps);
//            mockedAuditLogService.when(AuditLogService::getInstance).thenReturn(auditService);
//
//            when(request.getParameter("action")).thenReturn("delete");
//            when(request.getParameter("productId")).thenReturn("5");
//            when(ps.getProductById(5)).thenReturn(product);
//            when(request.getSession(false)).thenReturn(session);
//            when(session.getAttribute("staff")).thenReturn(staff);
//
//            controller.init(); // FIX: initialize productService
//            when(request.getMethod()).thenReturn("POST");
//            controller.service(request, response);
//
//            verify(ps).deleteProduct(5);
//            verify(auditService).logAction(any());
//            verify(response).sendRedirect("Product?action=list");
//        }
//    }
//    
//    
//    @Test
//    void testDeleteProduct_InvalidId_ShouldFail() throws Exception {
//        try (MockedStatic<ProductService> mockedProductService = Mockito.mockStatic(ProductService.class);
//             MockedStatic<AuditLogService> mockedAuditLogService = Mockito.mockStatic(AuditLogService.class)) {
//
//            ProductService ps = mock(ProductService.class);
//            AuditLogService auditService = mock(AuditLogService.class);
//
//            mockedProductService.when(ProductService::getInstance).thenReturn(ps);
//            mockedAuditLogService.when(AuditLogService::getInstance).thenReturn(auditService);
//
//            when(request.getParameter("action")).thenReturn("delete");
//            when(request.getParameter("productId")).thenReturn("999");
//            when(request.getSession(false)).thenReturn(session);
//
//            when(ps.getProductById(999)).thenThrow(new SQLException("Product not found"));
//
//            controller.init();
//            when(request.getMethod()).thenReturn("POST");
//
//            ServletException ex = assertThrows(ServletException.class,
//                    () -> controller.service(request, response));
//
//            // ❌ force failure by expecting the wrong message
//            assertEquals("Some wrong message", ex.getCause().getMessage());
//        }
//    }
//    
//    
//    
//    @Test
//    void testListProducts_NoFilters() throws Exception {
//        List<Product> mockProducts = Arrays.asList(new Product(), new Product());
//        List<String> mockCategories = Arrays.asList("Books", "Stationery");
//
//        try (MockedStatic<ProductService> mockedStatic = Mockito.mockStatic(ProductService.class)) {
//            ProductService productService = mock(ProductService.class);
//            mockedStatic.when(ProductService::getInstance).thenReturn(productService);
//
//            when(request.getParameter("action")).thenReturn("list");
//            when(productService.getAllProducts()).thenReturn(mockProducts);
//            when(productService.getAllCategories()).thenReturn(mockCategories);
//            when(request.getRequestDispatcher("WEB-INF/view/listProducts.jsp")).thenReturn(dispatcher);
//
//            controller.init();
//            when(request.getMethod()).thenReturn("GET");   // simulate GET
//            controller.service(request, response);
//
//            verify(request).setAttribute("products", mockProducts);
//            verify(request).setAttribute("categories", mockCategories);
//            verify(dispatcher).forward(request, response);
//        }
//    }

    
   
   
}
