package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.pahanaedu.dao.AuditLogDAO;
import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.model.AuditLog;
import com.pahanaedu.model.Bill;
import com.pahanaedu.service.CustomerService;
import com.pahanaedu.service.ProductService;

@WebServlet("/AdminDashboard")
public class AdminDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductService productService;
    private CustomerService customerService;
    private AuditLogDAO auditLogDAO = new AuditLogDAO();
    @Override
    public void init() throws ServletException {
        productService = ProductService.getInstance();
        customerService = CustomerService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        // Check if logged in and admin
        if (session == null || session.getAttribute("staff") == null || 
            !"admin".equalsIgnoreCase(String.valueOf(session.getAttribute("role")))) {
            response.sendRedirect(request.getContextPath() + "/StaffLogin.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("dashboard")) {
                showDashboard(request, response);
            } else if ("allTransactions".equals(action)) {
                showAllTransactions(request, response);
            } else if ("auditLog".equals(action)) {
                showAuditLog(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // New method to handle audit logs
    private void showAuditLog(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staff") == null) {
            response.sendRedirect(request.getContextPath() + "/StaffLogin.jsp");
            return;
        }

        String search = request.getParameter("search"); // search query
        String category = request.getParameter("category"); // category filter

        try (Connection conn = com.pahanaedu.dao.DBConnection.getConnection()) {
            List<AuditLog> auditLogs;

            if ((search != null && !search.trim().isEmpty()) || (category != null && !category.isEmpty())) {
                auditLogs = auditLogDAO.searchLogs(conn, search, category); // updated DAO method
            } else {
                auditLogs = auditLogDAO.getAllLogs(conn);
            }

            request.setAttribute("auditLogs", auditLogs);
            request.setAttribute("searchQuery", search);
            request.setAttribute("selectedCategory", category);
        } catch (Exception e) {
            throw new ServletException("Error loading audit logs", e);
        }

        request.getRequestDispatcher("/WEB-INF/view/auditLog.jsp").forward(request, response);
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {

        int totalProducts = productService.getTotalProducts();
        int totalCustomers = customerService.getTotalCustomers();

        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("billsToday", 0); // TODO: You can calculate today's bills if needed

        try (Connection conn = com.pahanaedu.dao.DBConnection.getConnection()) {
            BillDAO billDAO = new BillDAO();
            List<Bill> last5Bills = billDAO.getLast5Bills(conn);
            request.setAttribute("last5Bills", last5Bills);
        }

        request.getRequestDispatcher("/WEB-INF/view/adminDashboard.jsp").forward(request, response);
    }

    private void showAllTransactions(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        try (Connection conn = com.pahanaedu.dao.DBConnection.getConnection()) {
            BillDAO billDAO = new BillDAO();
            List<Bill> allBills = billDAO.getAllBills(conn);
            request.setAttribute("allBills", allBills);
        }

        request.getRequestDispatcher("/WEB-INF/view/allTransactions.jsp").forward(request, response);
    }
}
