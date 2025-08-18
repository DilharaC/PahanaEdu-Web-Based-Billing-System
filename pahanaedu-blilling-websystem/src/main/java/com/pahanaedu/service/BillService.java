package com.pahanaedu.service;

import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.dao.ProductDAO;
import com.pahanaedu.model.*;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class BillService {

    private final BillDAO billDAO;
    private final ProductDAO productDAO;
    private static BillService instance;

    private BillService() {
        billDAO = BillDAO.getInstance();
        productDAO = ProductDAO.getInstance();
    }

    public static BillService getInstance() {
        if (instance == null) {
            synchronized (BillService.class) {
                if (instance == null) instance = new BillService();
            }
        }
        return instance;
    }

    public Bill createBill(Customer customer, List<BillItem> items, int staffId, Connection conn) throws Exception {
        if (items == null || items.isEmpty())
            throw new Exception("No items to create bill");

        double total = 0;
        for (BillItem item : items) {
            Product p = item.getProduct();
            if (p.getQuantity() < item.getQuantity())
                throw new Exception("Insufficient stock for product: " + p.getName());
            total += item.getTotal();
        }

        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setStaffId(staffId);
        bill.setBillDate(new Date());
        bill.setTotalAmount(total);
        bill.setItems(items);

        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            // Reduce stock
            for (BillItem item : items) {
                Product p = item.getProduct();
                p.setQuantity(p.getQuantity() - item.getQuantity());
                productDAO.updateProduct(p, conn);
            }

            // Create bill and get full details BEFORE commit
            int billId = billDAO.createBill(bill, conn);
            Bill fullBill = billDAO.getBillById(billId, conn);

            conn.commit();
            return fullBill;

        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(previousAutoCommit);
        }
    }
}
