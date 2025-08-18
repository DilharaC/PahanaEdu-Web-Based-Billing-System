package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.dao.DBConnectionFactory;
import com.pahanaedu.model.Bill;
import com.pahanaedu.service.CustomerService;
import com.pahanaedu.service.ProductService;

@WebServlet("/StaffDashboard")
public class StaffDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductService productService;
    private CustomerService customerService;
    private BillDAO billDAO;

    @Override
    public void init() throws ServletException {
        productService = ProductService.getInstance();
        customerService = CustomerService.getInstance();
        billDAO = BillDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("dashboard")) {
                showDashboard(request, response);
            } else if ("allTransactions".equals(action)) {
                showAllTransactions(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        try (Connection conn = DBConnectionFactory.getConnection()) {

            int totalProducts = productService.getTotalProducts(conn);
            int totalCustomers = customerService.getTotalCustomers(conn);

//            int billsToday = billDAO.getTodaysBillCount(conn); // implement this in BillDAO with Connection
            List<Bill> last5Bills = billDAO.getLast5Bills(conn);

            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("totalCustomers", totalCustomers);
//            request.setAttribute("billsToday", billsToday);
            request.setAttribute("last5Bills", last5Bills);

            request.getRequestDispatcher("WEB-INF/view/staffDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error loading dashboard: " + e.getMessage(), e);
        }
    }

    private void showAllTransactions(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        try (Connection conn = DBConnectionFactory.getConnection()) {

            List<Bill> allBills = billDAO.getAllBills(conn);
            request.setAttribute("allBills", allBills);

            request.getRequestDispatcher("WEB-INF/view/allTransactions.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error loading transactions: " + e.getMessage(), e);
        }
    }}
