package com.pahanaedu.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pahanaedu.model.Customer;
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
        customerService.addCustomer(customer);
        response.sendRedirect("Customer?action=list");
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        Customer customer = extractCustomerFromRequest(request);
        customer.setCustomerId(customerId);
        customerService.updateCustomer(customer);
        response.sendRedirect("Customer?action=list");
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        customerService.deleteCustomer(customerId);
        response.sendRedirect("Customer?action=list");
    }

    private Customer extractCustomerFromRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);

        return customer;
    }
}