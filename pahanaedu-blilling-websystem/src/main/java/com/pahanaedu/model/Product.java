package com.pahanaedu.model;



public class Product {

    private int productId;          // Unique product ID
    private String name;            // Product name
    private String description;     // Product description (optional)
    private String category;        // Category name (Books, Stationery, etc.)
    private double price;           // Price per unit
    private int quantity;           // Stock quantity
    private boolean available;      // Availability status

    // Constructor with all fields
    public Product(int productId, String name, String description, String category,
                   double price, int quantity, boolean available) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.available = available;
    }

    // Constructor without description
    public Product(int productId, String name, String category,
                   double price, int quantity, boolean available) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.available = available;
    }

    // Default constructor
    public Product() {
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}