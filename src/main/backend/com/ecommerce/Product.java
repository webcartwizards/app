package com.ecommerce;
import java.util.Objects;

public class Product {
    private String name;
    private double price;
    private String size;  // Optional size for clothing
    private String category; // New field to define product category

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product p = (Product) o;
        return name.equals(p.name) &&
                price == p.price &&
                category.equals(p.category) &&
                size.equals(p.size);
        }
    @Override
    public int hashCode() {
        return Objects.hash(name, price, category, size);
    }
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
