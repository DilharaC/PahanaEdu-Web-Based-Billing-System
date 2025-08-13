package com.pahanaedu.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.dao.DBConnectionFactory;
import com.pahanaedu.model.Customer;

public class CustomerService {
    private static CustomerService instance;
    private CustomerDAO customerDAO;

    private CustomerService() {
        this.customerDAO = new CustomerDAO();
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

    public int getTotalCustomers() throws SQLException {
        return customerDAO.countCustomers();
    }
}