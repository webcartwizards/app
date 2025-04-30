package com.ecommerce;


public class Product {
    private String name;
    private double price;
    private String size;  // Optional size for clothing
    private String category; // New field to define product category

    // Constructor without size
    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.size = ""; // Default empty
    }

    // Constructor with size
    public Product(String name, double price, String category, String size) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.size = size;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getCategory() {
        return category;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
