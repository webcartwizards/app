package com.ecommerce;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    // The VBox inside the ScrollPane in account.fxml that will hold all UI elements.
    @FXML
    private VBox accountContainer;

    /**
     * The initialize method is called automatically when the FXML file is loaded.
     * It clears any previous content, checks if a customer is logged in, displays a welcome message,
     * loads orders based on whether the current customer is an admin, and then displays the orders.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Clear the container to start fresh.
        accountContainer.getChildren().clear();

        // If no customer is logged in, show a message and exit.
        if (Session.currentCustomer == null) {
            accountContainer.getChildren().add(new Label("You are not logged in."));
            return;
        }

        // Display a welcome message with the customer's name
        displayWelcomeMessage();

        // Load orders based on the role (admin sees all orders, others see their own orders).
        List<Order> orders = loadOrdersBasedOnRole();

        // If no orders exist, inform the user.
        if (orders.isEmpty()) {
            accountContainer.getChildren().add(new Label("No orders placed yet."));
        } else {
            // Otherwise, display each order.
            displayOrders(orders);
        }
    }

    /**
     * Adds a welcome message to the accountContainer using the current customer's name.
     */
    private void displayWelcomeMessage() {
        String welcome = "Welcome, " + Session.currentCustomer.getName();
        accountContainer.getChildren().add(new Label(welcome));
    }

    /**
     * Loads orders for the current customer.
     * If the current customer is an admin, all orders are loaded.
     * Otherwise, only orders matching the current customer's account ID are loaded.
     * returns a list of orders appropriate for the current customer's role.
     */
    private List<Order> loadOrdersBasedOnRole() {
        if (Session.currentCustomer.isAdmin()) {
            return OrderStorage.loadAllOrders();
        } else {
            return OrderStorage.loadOrders(Session.currentCustomer.getAccountId());
        }
    }

    /**
     * Iterates through a list of orders and adds a visual representation (an order box)
     * for each order to the accountContainer.
     */
    private void displayOrders(List<Order> orders) {
        // Define a date formatter for displaying order dates.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        for (Order order : orders) {
            // Create an order box (a VBox) for each order.
            accountContainer.getChildren().add(createOrderBox(order, formatter));
        }
    }

    /**
     * Creates and returns a VBox that contains all the details of a single order.
     * If the current customer is an admin, it also displays the username associated with the order's account ID.
     * returns a VBox containing the order details.
     */
    private VBox createOrderBox(Order order, DateTimeFormatter formatter) {
        VBox orderBox = new VBox(5);
        orderBox.setAlignment(Pos.CENTER_LEFT);
        orderBox.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10;");

        // If the logged-in customer is an admin, show the username for the order.
        if (Session.currentCustomer.isAdmin()) {
            String username = LoginController.getUsernameFromAccountId(order.getAccountId());
            orderBox.getChildren().add(new Label("Username: " + username));
        }

        // Add basic order details.
        orderBox.getChildren().add(new Label("Account ID: " + order.getAccountId()));
        orderBox.getChildren().add(new Label("Order ID: " + order.getOrderId()));
        orderBox.getChildren().add(new Label("Date: " + order.getOrderDate().format(formatter)));
        orderBox.getChildren().add(new Label("Status: " + order.getStatus()));
        orderBox.getChildren().add(new Label(String.format("Total: $%.2f", order.getTotalPrice())));

        // Iterate through each order item and display its details.
        for (OrderItem item : order.getItems()) {
            String itemInfo = String.format("%s | Qty: %d | Price: $%.2f",
                    item.getProduct().getName(), item.getQuantity(), item.getPrice());
            orderBox.getChildren().add(new Label(itemInfo));
        }
        return orderBox;
    }
}
