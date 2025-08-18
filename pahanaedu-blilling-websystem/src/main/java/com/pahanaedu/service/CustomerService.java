package com.pahanaedu.service;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.dao.DBConnectionFactory;
import com.pahanaedu.model.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    private static CustomerService instance;
    private CustomerDAO customerDAO;

    private CustomerService() {
        this.customerDAO = CustomerDAO.getInstance();
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            synchronized (CustomerService.class) {
                if (instance == null) {
                    instance = new CustomerService();
                }
            }
        }
        return instance;
    }

    public int addCustomer(Customer customer) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return customerDAO.addCustomer(customer, conn);
        }
    }

    public List<Customer> getAllCustomers() throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return customerDAO.getAllCustomers(conn);
        }
    }

    public Customer getCustomerById(int customerId) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return customerDAO.getCustomerById(customerId, conn);
        }
    }

    public void updateCustomer(Customer customer) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            customerDAO.updateCustomer(customer, conn);
        }
    }

    public void deleteCustomer(int customerId) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            customerDAO.deleteCustomer(customerId, conn);
        }
    }

    public List<Customer> searchCustomersByPhone(String phone) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return customerDAO.searchCustomersByPhone(phone, conn);
        }
    }

    public int getTotalCustomers(Connection conn) throws SQLException {
        return customerDAO.countCustomers(conn);
    }
    public List<Integer> getMonthlyNewCustomers(int months) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return customerDAO.getMonthlyNewCustomers(conn, months);
        }
    }

    public List<String> getLastMonthsLabels(int months) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            return customerDAO.getLastMonthsLabels(conn, months);
        }
    }
}