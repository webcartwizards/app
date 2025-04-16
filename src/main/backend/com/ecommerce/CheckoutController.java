package com.ecommerce;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class CheckoutController implements Initializable {

    @FXML
    private VBox rootContainer;  // Root container defined in checkout.fxml

    @FXML
    private Label orderSummaryLabel;

    @FXML
    private TextField shippingAddressField;

    @FXML
    private TextField paymentInfoField;

    @FXML
    private Button placeOrderButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Check if a customer is logged in.
        if (Session.currentCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Required");
            alert.setHeaderText(null);
            alert.setContentText("You must be logged in to proceed to checkout.");
            alert.showAndWait();
            placeOrderButton.setDisable(true);
        } else {
            orderSummaryLabel.setText("Checkout for " + Session.currentCustomer.getName());
        }

        placeOrderButton.setOnAction(e -> placeOrder());
    }

    private void placeOrder() {
        String shippingAddress = shippingAddressField.getText().trim();
        String paymentInfo = paymentInfoField.getText().trim();

        if (shippingAddress.isEmpty() || paymentInfo.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please provide a shipping address and payment information.");
            alert.showAndWait();
            return;
        }

        // Get the current cart items.
        Cart cart = Cart.getInstance();
        List<CartItem> cartItems = new ArrayList<>(cart.getCartItems());
        if (cartItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cart Empty");
            alert.setHeaderText(null);
            alert.setContentText("Your cart is empty!");
            alert.showAndWait();
            return;
        }

        // Convert cart items to order items and calculate total price.
        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getQuantity() * item.getProd().getPrice();
            orderItems.add(new OrderItem(item.getProd(), item.getQuantity(), item.getProd().getPrice()));
        }

        // Generate unique order ID and get current date/time.
        String orderId = UUID.randomUUID().toString();
        LocalDateTime orderDate = LocalDateTime.now();

        // Create a new Order with the proper parameters.
        Order newOrder = new Order(Session.currentCustomer.getAccountId(), orderId, orderDate, orderItems, totalPrice);
        Session.currentCustomer.addOrder(newOrder);
        OrderStorage.saveOrder(newOrder);

        // Clear the cart after placing the order.
        cart.getCartItems().clear();
        // alert that the order has been placed
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Placed");
        alert.setHeaderText(null);
        alert.setContentText("Your order (" + orderId + ") has been placed successfully!");
        alert.showAndWait();

        // Close the checkout window (modal).
        Stage checkoutStage = (Stage) placeOrderButton.getScene().getWindow();
        checkoutStage.close();
    }
}
