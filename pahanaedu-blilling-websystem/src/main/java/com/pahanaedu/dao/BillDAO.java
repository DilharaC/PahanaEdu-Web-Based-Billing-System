package com.pahanaedu.dao;

import com.pahanaedu.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {


    public BillDAO() {}

    // ----------------- CREATE BILL -----------------
    public int createBill(Bill bill, Connection conn) throws SQLException {
        String billQuery = "INSERT INTO bill (customer_id, staff_id, bill_date, total_amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement billPs = conn.prepareStatement(billQuery, Statement.RETURN_GENERATED_KEYS)) {
            billPs.setInt(1, bill.getCustomer().getCustomerId());
            billPs.setInt(2, bill.getStaffId());
            billPs.setDate(3, new java.sql.Date(bill.getBillDate().getTime()));
            billPs.setDouble(4, bill.getTotalAmount());
            billPs.executeUpdate();

            try (ResultSet rs = billPs.getGeneratedKeys()) {
                if (rs.next()) {
                    int billId = rs.getInt(1);

                    // Insert bill items
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
                } else {
                    throw new SQLException("Failed to retrieve generated bill ID");
                }
            }
        }
    }

    // ----------------- GET ALL BILLS -----------------
    public List<Bill> getAllBills(Connection conn) throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT b.bill_id, b.bill_date, b.total_amount, b.staff_id, " +
                     "c.customer_id, c.name AS customer_name, c.phone " +
                     "FROM bill b JOIN customer c ON b.customer_id = c.customer_id " +
                     "ORDER BY b.bill_id DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("customer_name"));
                customer.setPhone(rs.getString("phone"));

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

    // ----------------- GET BILL BY ID -----------------
    public Bill getBillById(int billId, Connection conn) throws SQLException {
        String sql = "SELECT b.bill_id, b.bill_date, b.total_amount, b.staff_id, " +
                     "c.customer_id, c.name AS customer_name, c.phone " +
                     "FROM bill b " +
                     "JOIN customer c ON b.customer_id = c.customer_id " +
                     "WHERE b.bill_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(rs.getInt("bill_id"));
                    bill.setBillDate(rs.getDate("bill_date"));
                    bill.setTotalAmount(rs.getDouble("total_amount"));
                    bill.setStaffId(rs.getInt("staff_id"));

                    Customer customer = new Customer();
                    customer.setCustomerId(rs.getInt("customer_id"));
                    customer.setName(rs.getString("customer_name"));
                    customer.setPhone(rs.getString("phone"));
                    bill.setCustomer(customer);

                    // Fetch bill items
                    List<BillItem> items = getBillItemsByBillId(billId, conn);
                    bill.setItems(items);

                    return bill;
                }
            }
        }
        return null;
    }

    // ----------------- GET BILL ITEMS -----------------
    private List<BillItem> getBillItemsByBillId(int billId, Connection conn) throws SQLException {
        String sql = "SELECT bi.product_id, bi.quantity, bi.price, p.name AS product_name " +
                     "FROM bill_item bi " +
                     "JOIN product p ON bi.product_id = p.product_id " +
                     "WHERE bi.bill_id = ?";

        List<BillItem> items = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("product_id"));
                    product.setName(rs.getString("product_name"));

                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");

                    BillItem item = new BillItem(product, quantity, price);
                    items.add(item);
                }
            }
        }
        return items;
    }

    // ----------------- GET LAST 5 BILLS -----------------
    public List<Bill> getLast5Bills(Connection conn) throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT b.bill_id, b.bill_date, b.total_amount, " +
                     "c.name AS customer_name, c.phone AS customer_phone, b.staff_id " +
                     "FROM bill b " +
                     "JOIN customer c ON b.customer_id = c.customer_id " +
                     "ORDER BY b.bill_id DESC LIMIT 5";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setBillDate(rs.getDate("bill_date"));
                bill.setTotalAmount(rs.getDouble("total_amount"));

                Customer customer = new Customer();
                customer.setName(rs.getString("customer_name"));
                customer.setPhone(rs.getString("customer_phone"));
                bill.setCustomer(customer);

                bill.setStaffId(rs.getInt("staff_id"));
                bills.add(bill);
            }
        }
        return bills;
    }

    // ----------------- TOP SELLING PRODUCTS -----------------
    public List<ProductSales> getTopSellingProducts(Connection conn, int limit) throws SQLException {
        List<ProductSales> topProducts = new ArrayList<>();
        String sql = "SELECT p.name AS product_name, SUM(bi.quantity) AS total_sold " +
                     "FROM bill_item bi " +
                     "JOIN product p ON bi.product_id = p.product_id " +
                     "GROUP BY bi.product_id " +
                     "ORDER BY total_sold DESC LIMIT ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductSales psale = new ProductSales();
                    psale.setProductName(rs.getString("product_name"));
                    psale.setQuantitySold(rs.getInt("total_sold"));
                    topProducts.add(psale);
                }
            }
        }
        return topProducts;
    }
}
