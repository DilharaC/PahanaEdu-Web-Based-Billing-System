package com.pahanaedu.dao;

import com.pahanaedu.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public int createBill(Bill bill, Connection conn) throws SQLException {
        String billQuery = "INSERT INTO bill (customer_id, staff_id, bill_date, total_amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement billPs = conn.prepareStatement(billQuery, Statement.RETURN_GENERATED_KEYS)) {
            billPs.setInt(1, bill.getCustomer().getCustomerId());
            billPs.setInt(2, bill.getStaffId());
            billPs.setDate(3, new java.sql.Date(bill.getBillDate().getTime()));
            billPs.setDouble(4, bill.getTotalAmount());
            billPs.executeUpdate();

            ResultSet rs = billPs.getGeneratedKeys();
            if (rs.next()) {
                int billId = rs.getInt(1);

                String itemQuery = "INSERT INTO bill_item (bill_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
                try (PreparedStatement itemPs = conn.prepareStatement(itemQuery)) {
                    for (BillItem item : bill.getItems()) {
                        itemPs.setInt(1, billId);
                        itemPs.setInt(2, item.getProduct().getProductId());
                        itemPs.setInt(3, item.getQuantity());
                        itemPs.setDouble(4, item.getPrice());
                        itemPs.addBatch();
                    }
                    itemPs.executeBatch();
                }
                return billId;
            }
        }
        return 0;
    }

    public List<Bill> getAllBills(Connection conn) throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT b.bill_id, b.bill_date, b.total_amount, b.staff_id, " +
                     "c.customer_id, c.name AS customer_name " +
                     "FROM bill b JOIN customer c ON b.customer_id = c.customer_id " +
                     "ORDER BY b.bill_id DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("customer_name"));

                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setBillDate(rs.getDate("bill_date"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setStaffId(rs.getInt("staff_id"));
                bill.setCustomer(customer);

                bills.add(bill);
            }
        }
        return bills;
    }
}