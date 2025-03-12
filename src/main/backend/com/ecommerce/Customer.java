package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user account, holding credentials, admin status, and order history.
 */
public class Customer {
    private final String name;
    private final String accountId;
    private String password;
    private boolean admin;
    private List<Order> orders;

    /**
     * Creates a regular customer account.
     *
     * @param name      the customer’s username
     * @param password  the customer’s password
     * @param accountId a unique identifier for this account
     */
    public Customer(String name, String password, String accountId) {
        this.name = name;
        this.password = password;
        this.accountId = accountId;
        this.admin = false;
        this.orders = new ArrayList<>();
    }

    /**
     * Creates a customer account, optionally with admin privileges.
     *
     * @param name      the account’s username
     * @param password  the account’s password
     * @param accountId a unique identifier for this account
     * @param admin     true if this account should have admin rights
     */
    public Customer(String name, String password, String accountId, boolean admin) {
        this.name = name;
        this.password = password;
        this.accountId = accountId;
        this.admin = admin;
        this.orders = new ArrayList<>();
    }

    /**
     * Returns the customer’s display name.
     *
     * @return the username
     */
    public String getName() {
        return name;
    }

    /**
     * Returns this account’s unique ID.
     *
     * @return the accountId.
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Returns the customer’s password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Checks whether this account has admin privileges.
     *
     * @return true if admin, false otherwise
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Grants or revokes admin privileges for this account.
     *
     * @param admin set to true to make this an admin account
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * Adds a completed order to the customer’s order history.
     *
     * @param order the order to record
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Retrieves the list of all orders placed by this customer.
     *
     * @return list of past orders
     */
    public List<Order> getOrders() {
        return orders;
    }
}
