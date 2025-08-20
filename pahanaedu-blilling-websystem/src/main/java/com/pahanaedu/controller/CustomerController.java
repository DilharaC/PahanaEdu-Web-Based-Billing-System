package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahanaedu.model.AuditLog;
import com.pahanaedu.model.Customer;
import com.pahanaedu.model.Staff;
import com.pahanaedu.service.AuditLogService;
import com.pahanaedu.service.CustomerService;

@WebServlet("/Customer")
public class CustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = CustomerService.getInstance();
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                listCustomers(request, response);
            } else if (action.equals("add")) {
                showAddForm(request, response);
            } else if (action.equals("edit")) {
                showEditForm(request, response);
            } else if (action.equals("monthlyChartData")) {
                getMonthlyChartData(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Customer customer = extractCustomerFromRequest(request);

            int generatedId = customerService.addCustomer(customer);
            customer.setCustomerId(generatedId);

            HttpSession session = request.getSession(false);
            String performedBy = "Unknown";
            if (session != null && session.getAttribute("staff") != null) {
                performedBy = ((Staff) session.getAttribute("staff")).getFullName();
            }

            AuditLog log = new AuditLog();
            log.setAction("Add Customer");
            log.setPerformedBy(performedBy);
            log.setTargetEntity("Customer");
            log.setTargetId(generatedId);
            log.setDetails("Added customer: " + customer.getName() + " (ID: " + generatedId + ")");
            AuditLogService.getInstance().logAction(log);

            response.sendRedirect("Customer?action=list&success=Customer added successfully!");

        } catch (ValidationException ve) {
            request.setAttribute("errorMessage", ve.getMessage());
            request.getRequestDispatcher("WEB-INF/view/addCustomer.jsp").forward(request, response);
        } catch (SQLException e) {
            String errorMessage;
            if (e.getMessage().contains("Phone number already exists") || e.getMessage().contains("Duplicate entry")) {
                errorMessage = "Email or phone already exists. Please use unique values.";
            } else {
                errorMessage = "An unexpected error occurred: " + e.getMessage();
            }
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("WEB-INF/view/addCustomer.jsp").forward(request, response);
        }
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        Customer existingCustomer = customerService.getCustomerById(customerId);

        try {
            Customer customer = extractCustomerFromRequest(request);
            customer.setCustomerId(customerId);

            customerService.updateCustomer(customer);

            HttpSession session = request.getSession(false);
            String performedBy = "Unknown";
            if (session != null && session.getAttribute("staff") != null) {
                performedBy = ((Staff) session.getAttribute("staff")).getFullName();
            }

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

        } catch (ValidationException ve) {
            request.setAttribute("errorMessage", ve.getMessage());
            request.getRequestDispatcher("WEB-INF/view/editCustomer.jsp").forward(request, response);
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        Customer customer = customerService.getCustomerById(customerId);

        customerService.deleteCustomer(customerId);

        HttpSession session = request.getSession(false);
        String performedBy = "Unknown";
        if (session != null && session.getAttribute("staff") != null) {
            performedBy = ((Staff) session.getAttribute("staff")).getFullName();
        }

        AuditLog log = new AuditLog();
        log.setAction("Delete Customer");
        log.setPerformedBy(performedBy);
        log.setTargetEntity("Customer");
        log.setTargetId(customerId);
        log.setDetails("Deleted customer: " + (customer != null ? customer.getName() : "Unknown") + " (ID: " + customerId + ")");
        AuditLogService.getInstance().logAction(log);

        response.sendRedirect("Customer?action=list");
    }

    private Customer extractCustomerFromRequest(HttpServletRequest request) throws ValidationException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }

        if (email == null || !email.contains("@")) {
            throw new ValidationException("Invalid email format.");
        }

        if (phone == null || phone.trim().isEmpty()) {
            throw new ValidationException("Phone number cannot be empty.");
        }

        boolean active = "true".equalsIgnoreCase(request.getParameter("active"));

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setActive(active);

        return customer;
    }

    private void getMonthlyChartData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int months = 6;
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
    }

    // Custom exception for validation
    private static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
