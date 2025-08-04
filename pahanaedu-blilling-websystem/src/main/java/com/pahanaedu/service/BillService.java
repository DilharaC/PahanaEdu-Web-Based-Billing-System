package com.pahanaedu.service;

import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.dao.ProductDAO;
import com.pahanaedu.model.*;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class BillService {
    private BillDAO billDAO = new BillDAO();
    private ProductDAO productDAO = new ProductDAO();

    public int createBill(Customer customer, List<BillItem> items, int staffId, Connection conn) throws Exception {
        double total = items.stream().mapToDouble(BillItem::getTotal).sum();

        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setStaffId(staffId);
        bill.setBillDate(new Date());
        bill.setTotalAmount(total);
        bill.setItems(items);

        // Reduce stock quantity
        for (BillItem item : items) {
            Product p = item.getProduct();
            p.setQuantity(p.getQuantity() - item.getQuantity());
            productDAO.updateProduct(p, conn);
        }

        return billDAO.createBill(bill, conn);
    }
}