package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.pahanaedu.service.CustomerService;
import com.pahanaedu.service.ProductService;

@WebServlet("/StaffDashboard")
public class StaffDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductService productService;
    private CustomerService customerService;  // Declare CustomerService

    @Override
    public void init() throws ServletException {
        productService = ProductService.getInstance();
        customerService = CustomerService.getInstance();  // Initialize CustomerService here
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("dashboard")) {
                showDashboard(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {

        int totalProducts = productService.getTotalProducts();
        int totalCustomers = customerService.getTotalCustomers();

        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("billsToday", 0);

        request.getRequestDispatcher("WEB-INF/view/staffDashboard.jsp").forward(request, response);
    }
}