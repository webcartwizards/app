package com.ecommerce;

import java.util.ArrayList;
import java.util.List;
/**
Constructor for the customer class
*/
public class Customer {
    private final String name;
    private final String accountId;
    private String password;
    private boolean admin;   // true if this is the admin account
    private List<Order> orders;

    /** 
    Constructor for normal accounts.
    */
    public Customer(String name, String password, String accountId) {
        this.name = name;
        this.password = password;
        this.accountId = accountId;
        this.admin = false;
        this.orders = new ArrayList<>();
    }

    /** 
    Constructor that allows creation of an admin account.
    */
    public Customer(String name, String password, String accountId, boolean admin) {
        this.name = name;
        this.password = password;
        this.accountId = accountId;
        this.admin = admin;
        this.orders = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }
}
