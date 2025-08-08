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
        int customerId = 0;
        Integer staffId = null;
        List<BillItem> items = new ArrayList<>();

        try (Connection conn = DBConnectionFactory.getConnection()) {
            System.out.println("[DEBUG] Starting saveBill...");

            String customerIdStr = request.getParameter("customerId");
            System.out.println("[DEBUG] customerIdStr: " + customerIdStr);

            if (customerIdStr == null || customerIdStr.trim().isEmpty()) {
                throw new ServletException("Customer ID is missing.");
            }
            customerId = Integer.parseInt(customerIdStr.trim());

            Customer customer = customerDAO.getCustomerById(customerId, conn);
            System.out.println("[DEBUG] Retrieved customer: " + customer);

            if (customer == null) {
                throw new ServletException("Customer not found with ID: " + customerId);
            }

            staffId = (Integer) request.getSession().getAttribute("staffId");
            if (staffId == null) {
                staffId = 1; // default staff for testing
            }
            System.out.println("[DEBUG] staffId: " + staffId);

            String[] productIds = request.getParameterValues("productId");
            String[] quantities = request.getParameterValues("quantity");

            System.out.println("[DEBUG] productIds length: " + (productIds == null ? "null" : productIds.length));
            System.out.println("[DEBUG] quantities length: " + (quantities == null ? "null" : quantities.length));

            if (productIds == null || quantities == null || productIds.length != quantities.length) {
                throw new ServletException("Product or quantity data is invalid.");
            }

            for (int i = 0; i < productIds.length; i++) {
                String pidStr = productIds[i];
                String qtyStr = quantities[i];

                System.out.println("[DEBUG] Processing productId: " + pidStr + ", quantity: " + qtyStr);

                if (pidStr == null || pidStr.trim().isEmpty()) continue;
                if (qtyStr == null || qtyStr.trim().isEmpty()) continue;

                int pid = Integer.parseInt(pidStr.trim());
                int qty = Integer.parseInt(qtyStr.trim());
                if (qty <= 0) continue;

                Product product = productDAO.getProductById(pid, conn);
                System.out.println("[DEBUG] Retrieved product: " + product);

                if (product == null) {
                    throw new ServletException("Product not found with ID: " + pid);
                }

                items.add(new BillItem(product, qty, product.getPrice()));
            }

            System.out.println("[DEBUG] Total valid bill items: " + items.size());

            if (items.isEmpty()) {
                throw new ServletException("No valid products with quantity to create a bill.");
            }

            int billId = billService.createBill(customer, items, staffId, conn);
            System.out.println("[DEBUG] Created bill with ID: " + billId);

            if (billId <= 0) {
                throw new ServletException("Failed to create bill");
            }

            Bill fullBill = billDAO.getBillById(billId, conn);
            System.out.println("[DEBUG] Retrieved full bill: " + fullBill);
            System.out.println("[DEBUG] Bill items count: " + (fullBill.getItems() == null ? 0 : fullBill.getItems().size()));

            request.setAttribute("bill", fullBill);
            request.getRequestDispatcher("/WEB-INF/view/bill_success.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error creating bill: " + e.getMessage(), e);
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