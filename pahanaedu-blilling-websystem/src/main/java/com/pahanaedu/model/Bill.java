package com.pahanaedu.model;

import java.util.Date;
import java.util.List;

public class Bill {
    private int billId;
    private Customer customer;
    private int staffId;
    private String staffName;
    private Date billDate;
    private double totalAmount;
    private List<BillItem> items;

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }



    public Date getBillDate() { return billDate; }
    public void setBillDate(Date billDate) { this.billDate = billDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public List<BillItem> getItems() { return items; }
    public void setItems(List<BillItem> items) { this.items = items; }
    
    public String getStaffName() {
        return staffName;
    }
    
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
    }
