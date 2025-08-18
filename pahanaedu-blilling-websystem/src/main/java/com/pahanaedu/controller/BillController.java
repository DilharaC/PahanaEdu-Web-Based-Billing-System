package com.pahanaedu.controller;

import com.pahanaedu.dao.*;
import com.pahanaedu.model.*;
import com.pahanaedu.service.AuditLogService;
import com.pahanaedu.service.BillService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Bill")
public class BillController extends HttpServlet {

    private BillService billService = BillService.getInstance(); // Singleton
    private BillDAO billDAO = BillDAO.getInstance();             // Singleton
    private CustomerDAO customerDAO = CustomerDAO.getInstance(); // Singleton
    private ProductDAO productDAO = ProductDAO.getInstance();    // Singleton

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "create";

        switch (action) {
            case "create":
            case "form":
                showBillForm(request, response);
                break;
            case "view":
                if (request.getParameter("billId") != null) {
                    viewBillDetails(request, response);
                } else {
                    viewBills(request, response);
                }
                break;
            case "topProductsData":
                sendTopProductsData(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "save";

        if ("save".equals(action)) {
            saveBill(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action");
        }
    }

    private void showBillForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            request.setAttribute("customers", customerDAO.getAllCustomers(conn));
            request.setAttribute("products", productDAO.getAllProducts(conn));
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/view/bill_form.jsp").forward(request, response);
    }

    private void saveBill(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {

        List<BillItem> items = new ArrayList<>();
        Connection conn = null;

        try {
            conn = DBConnectionFactory.getConnection();
            conn.setAutoCommit(false); // start transaction

            int customerId = Integer.parseInt(request.getParameter("customerId").trim());
            Customer customer = customerDAO.getCustomerById(customerId, conn);
            if (customer == null) throw new ServletException("Customer not found.");

            Integer staffId = (Integer) request.getSession().getAttribute("staffId");
            if (staffId == null) throw new ServletException("No staff logged in.");

            String[] productIds = request.getParameterValues("productId");
            String[] quantities = request.getParameterValues("quantity");

            if (productIds == null || quantities == null || productIds.length != quantities.length)
                throw new ServletException("Product or quantity data invalid.");

            for (int i = 0; i < productIds.length; i++) {
                int pid = Integer.parseInt(productIds[i].trim());
                int qty = Integer.parseInt(quantities[i].trim());
                if (qty <= 0) continue;

                Product product = productDAO.getProductById(pid, conn);
                if (product == null) throw new ServletException("Product not found: " + pid);

                items.add(new BillItem(product, qty, product.getPrice()));
            }

            if (items.isEmpty()) throw new ServletException("No valid products to create bill.");

            // ✅ Create bill and get full bill (with items) in one step
            Bill fullBill = billService.createBill(customer, items, staffId, conn);

            // Audit log
            AuditLog log = new AuditLog();
            log.setAction("Create Bill");
            log.setPerformedBy("Staff ID: " + staffId);
            log.setTargetEntity("Bill");
            log.setTargetId(fullBill.getBillId());

            StringBuilder details = new StringBuilder("Created bill for customer: " + customer.getName());
            details.append(", Items: ");
            for (BillItem item : items) {
                details.append(item.getProduct().getName())
                       .append(" x").append(item.getQuantity()).append(", ");
            }
            if (details.length() > 2) details.setLength(details.length() - 2);
            log.setDetails(details.toString());
            AuditLogService.getInstance().logAction(log);

            request.setAttribute("bill", fullBill);
            request.getRequestDispatcher("/WEB-INF/view/bill_success.jsp").forward(request, response);

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            e.printStackTrace();
            throw new ServletException("Error creating bill: " + e.getMessage(), e);
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    private void viewBills(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            request.setAttribute("bills", billDAO.getAllBills(conn));
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/view/allTransaction.jsp").forward(request, response);
    }

    private void viewBillDetails(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int billId = Integer.parseInt(request.getParameter("billId").trim());
        try (Connection conn = DBConnectionFactory.getConnection()) {
            Bill bill = billDAO.getBillById(billId, conn);
            if (bill == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found");
                return;
            }
            request.setAttribute("bill", bill);
            request.getRequestDispatcher("/WEB-INF/view/bill_view.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error retrieving bill details", e);
        }
    }

    private void sendTopProductsData(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json");
        try (Connection conn = DBConnectionFactory.getConnection()) {
            List<ProductSales> topProducts = billDAO.getTopSellingProducts(conn, 5);
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < topProducts.size(); i++) {
                ProductSales ps = topProducts.get(i);
                json.append("{\"name\":\"").append(ps.getProductName()).append("\",")
                    .append("\"sold\":").append(ps.getQuantitySold()).append("}");
                if (i < topProducts.size() - 1) json.append(",");
            }
            json.append("]");
            response.getWriter().write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("[]");
        }
    }
}
