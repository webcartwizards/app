package com.ecommerce;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String accountId;    // The account that placed the order.
    private String orderId;
    private LocalDateTime orderDate;
    private List<OrderItem> items;
    private double totalPrice;
    private String status;

    // Constructor
    public Order(String accountId, String orderId, LocalDateTime orderDate, List<OrderItem> items, double totalPrice) {
        this.accountId = accountId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = "Pending"; // default status.
    }

    // Add the getter method:
    public String getAccountId() {
        return this.accountId;
    }

    // Other getters and setters...
    public String getOrderId() {
        return orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
