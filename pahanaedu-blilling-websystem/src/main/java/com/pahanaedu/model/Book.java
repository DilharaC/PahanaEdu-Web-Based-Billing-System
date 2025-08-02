package com.pahanaedu.model;

public class Book {

    private int bookId;
    private String title;
    private String author;
    private String description;
    private double price;
    private int quantity;
    private String image;       // Can store image file name or URL
    private boolean available;  // Availability status

    // Constructor with all fields
    public Book(int bookId, String title, String author, String description, double price, int quantity, String image, boolean available) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.available = available;
    }

    // Constructor without description
    public Book(int bookId, String title, String author, double price, int quantity, boolean available) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.available = available;
    }

    // Default constructor
    public Book() {
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}