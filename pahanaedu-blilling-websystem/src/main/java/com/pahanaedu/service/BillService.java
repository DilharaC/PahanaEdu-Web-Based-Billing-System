package com.pahanaedu.service;

import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.dao.ProductDAO;
import com.pahanaedu.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class BillService {

    private static BillService instance;
    private final BillDAO billDAO;
    private final ProductDAO productDAO;

    private BillService() {
        this.billDAO = new BillDAO();
        this.productDAO = new ProductDAO();
    }

    public static BillService getInstance() {
        if (instance == null) {
            synchronized (BillService.class) {
                if (instance == null) {
                    instance = new BillService();
                }
            }
        }
        return instance;
    }

    /**
     * Create a new bill (no transaction handling here).
     * The controller is responsible for commit/rollback.
     */
    public Bill createBill(Customer customer, List<BillItem> items, int staffId, Connection conn) throws SQLException {
        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setStaffId(staffId);
        bill.setItems(items);

        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        bill.setTotalAmount(total);
        bill.setBillDate(new java.util.Date());

        int billId = billDAO.createBill(bill, conn);
        bill.setBillId(billId);

        return bill;
    }
}