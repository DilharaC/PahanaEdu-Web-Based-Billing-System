package com.pahanaedu.model;

public class BillItem {
    private Product product;
    private int quantity;
    private double price;
    private double total; 

    public BillItem() {}

    public BillItem(Product product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.total = quantity * price; 
    }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
        this.total = this.quantity * this.price; 
    }

    public double getPrice() { return price; }
    public void setPrice(double price) { 
        this.price = price; 
        this.total = this.quantity * this.price; 
    }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; } 
}