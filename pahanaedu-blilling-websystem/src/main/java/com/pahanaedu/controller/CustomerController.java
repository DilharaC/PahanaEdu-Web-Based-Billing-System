package com.pahanaedu.controller;



import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahanaedu.dao.DBConnectionFactory;
import com.pahanaedu.model.AuditLog;
import com.pahanaedu.model.Customer;

import com.pahanaedu.model.Staff;
import com.pahanaedu.service.AuditLogService;
import com.pahanaedu.service.CustomerService;

/**
 * Servlet implementation class CustomerController
 */
@WebServlet("/Customer")
public class CustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = CustomerService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                listCustomers(request, response);
            } else if (action.equals("add")) {
                showAddForm(request, response);
            } else if (action.equals("edit")) {
                showEditForm(request, response);
            }
            else if (action.equals("monthlyChartData")) {
                getMonthlyChartData(request, response);
            }
            
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                addCustomer(request, response);
            } else if ("update".equals(action)) {
                updateCustomer(request, response);
            } else if ("delete".equals(action)) {
                deleteCustomer(request, response);
            }
           
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String search = request.getParameter("search");
        List<Customer> customerList;

        if (search != null && !search.trim().isEmpty()) {
            // Only search by phone number (digits expected)
            customerList = customerService.searchCustomersByPhone(search.trim());
        } else {
            customerList = customerService.getAllCustomers();
        }

        request.setAttribute("customers", customerList);
        request.getRequestDispatcher("WEB-INF/view/listCustomers.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/addCustomer.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        Customer existingCustomer = customerService.getCustomerById(customerId);
        request.setAttribute("customer", existingCustomer);
        request.getRequestDispatcher("WEB-INF/view/editCustomer.jsp").forward(request, response);
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Customer customer = extractCustomerFromRequest(request);

        // Add customer and get the generated ID
        int generatedId = CustomerService.getInstance().addCustomer(customer);
        customer.setCustomerId(generatedId); // optional: store ID in object

        // Get the staff who performed the action
        HttpSession session = request.getSession(false);
        String performedBy = "Unknown";
        if (session != null && session.getAttribute("staff") != null) {
            performedBy = ((Staff) session.getAttribute("staff")).getFullName();
        }

        // Audit log
        AuditLog log = new AuditLog();
        log.setAction("Add Customer");
        log.setPerformedBy(performedBy);
        log.setTargetEntity("Customer");
        log.setTargetId(generatedId);
        log.setDetails("Added customer: " + customer.getName() + " (ID: " + generatedId + ")");
        AuditLogService.getInstance().logAction(log);

        response.sendRedirect("Customer?action=list");
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));

        // Get existing customer
        Customer existingCustomer = customerService.getCustomerById(customerId);

        // Extract new data
        Customer customer = extractCustomerFromRequest(request);
        customer.setCustomerId(customerId);

        // Update customer in DB
        customerService.updateCustomer(customer);

        // Get staff who performed the action
        HttpSession session = request.getSession(false);
        String performedBy = "Unknown";
        if (session != null && session.getAttribute("staff") != null) {
            performedBy = ((Staff) session.getAttribute("staff")).getFullName();
        }

        // Build details string for changed fields
        StringBuilder details = new StringBuilder("Updated customer ID: " + customerId);
        boolean changed = false;

        if (!existingCustomer.getName().equals(customer.getName())) {
            details.append(", Name: '").append(existingCustomer.getName()).append("' → '").append(customer.getName()).append("'");
            changed = true;
        }
        if (!existingCustomer.getEmail().equals(customer.getEmail())) {
            details.append(", Email: '").append(existingCustomer.getEmail()).append("' → '").append(customer.getEmail()).append("'");
            changed = true;
        }
        if (!existingCustomer.getPhone().equals(customer.getPhone())) {
            details.append(", Phone: '").append(existingCustomer.getPhone()).append("' → '").append(customer.getPhone()).append("'");
            changed = true;
        }
        if (!existingCustomer.getAddress().equals(customer.getAddress())) {
            details.append(", Address: '").append(existingCustomer.getAddress()).append("' → '").append(customer.getAddress()).append("'");
            changed = true;
        }
        if (existingCustomer.isActive() != customer.isActive()) {
            details.append(", Active: '").append(existingCustomer.isActive()).append("' → '").append(customer.isActive()).append("'");
            changed = true;
        }

        // Log only if something changed
        if (changed) {
            AuditLog log = new AuditLog();
            log.setAction("Update Customer");
            log.setPerformedBy(performedBy);
            log.setTargetEntity("Customer");
            log.setTargetId(customerId);
            log.setDetails(details.toString());
            AuditLogService.getInstance().logAction(log);
        }

        response.sendRedirect("Customer?action=list");
    }
    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        
        // Get the customer before deletion for logging
        Customer customer = customerService.getCustomerById(customerId);

        // Delete customer from database
        customerService.deleteCustomer(customerId);

        // Get the staff who performed the action
        HttpSession session = request.getSession(false);
        String performedBy = "Unknown";
        if (session != null && session.getAttribute("staff") != null) {
            performedBy = ((Staff) session.getAttribute("staff")).getFullName();
        }

        // Log the deletion
        AuditLog log = new AuditLog();
        log.setAction("Delete Customer");
        log.setPerformedBy(performedBy);
        log.setTargetEntity("Customer");
        log.setTargetId(customerId);
        log.setDetails("Deleted customer: " + (customer != null ? customer.getName() : "Unknown") + " (ID: " + customerId + ")");
        AuditLogService.getInstance().logAction(log);

        // Redirect back to list
        response.sendRedirect("Customer?action=list");
    }

    private Customer extractCustomerFromRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Parse active from select
        String activeParam = request.getParameter("active");
        boolean active = "true".equalsIgnoreCase(activeParam); // true if "true", false otherwise

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setActive(active); // <-- now correctly set from select

        return customer;
    }
    private void getMonthlyChartData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int months = 6; // last 6 months
        try {
            List<Integer> counts = customerService.getMonthlyNewCustomers(months);
            List<String> labels = customerService.getLastMonthsLabels(months);

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < counts.size(); i++) {
                json.append("{\"month\":\"").append(labels.get(i))
                    .append("\", \"count\":").append(counts.get(i)).append("}");
                if (i < counts.size() - 1) json.append(",");
            }
            json.append("]");

            response.setContentType("application/json");
            response.getWriter().write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating chart data");
        }
    }}