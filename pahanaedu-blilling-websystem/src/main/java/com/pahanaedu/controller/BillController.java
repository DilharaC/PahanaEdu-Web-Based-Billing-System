package com.pahanaedu.controller;

import com.pahanaedu.dao.*;
import com.pahanaedu.model.*;
import com.pahanaedu.service.BillService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class BillController
 */
@WebServlet("/Bill")
public class BillController extends HttpServlet {

    private BillService billService = new BillService();
    private BillDAO billDAO = new BillDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "create";  // default to 'create' page

        switch (action) {
            case "create":
            case "form":  // allow form as synonym for create
                showBillForm(request, response);
                break;
            case "view":
                viewBills(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "save";

        switch (action) {
            case "save":
                saveBill(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action");
        }
    }

    private void showBillForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            request.setAttribute("customers", customerDAO.getAllCustomers(conn));
            request.setAttribute("products", productDAO.getAllProducts(conn));
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/view/bill_form.jsp").forward(request, response);
    }

    private void saveBill(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");

        Integer staffId = (Integer) request.getSession().getAttribute("staffId");
        if (staffId == null) staffId = 1; // testing default

        List<BillItem> items = new ArrayList<>();

        try (Connection conn = DBConnectionFactory.getConnection()) {
            Customer customer = customerDAO.getCustomerById(customerId, conn);

            for (int i = 0; i < productIds.length; i++) {
                Product product = productDAO.getProductById(Integer.parseInt(productIds[i]), conn);
                int qty = Integer.parseInt(quantities[i]);
                if (qty > 0) {
                    items.add(new BillItem(product, qty, product.getPrice()));
                }
            }

            int billId = billService.createBill(customer, items, staffId, conn);

            request.setAttribute("billId", billId);
            request.getRequestDispatcher("/WEB-INF/view/bill_success.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error creating bill");
        }
    }

    private void viewBills(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            request.setAttribute("bills", billDAO.getAllBills(conn));
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/view/bill_list.jsp").forward(request, response);
    }
}