package com.ecommerce;

public class OrderItem {
    private Product product;
    private int quantity;
    private double price;
    private String size;

    public OrderItem(Product product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.size = product.getSize();
    }

    // Getters
    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
    public String getSize(){
        return size;
    }
}
