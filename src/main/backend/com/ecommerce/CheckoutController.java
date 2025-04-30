package com.ecommerce;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class CheckoutController implements Initializable {

    @FXML private Label orderSummaryLabel;
    @FXML private TextField shippingAddressField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField paymentField;

    @FXML private Label subtotalLabel;
    @FXML private Label taxLabel;
    @FXML private Label totalLabel;

    @FXML private Button placeOrderButton;

    // sales tax rate
    private static final double TAX_RATE = 0.0975;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Session.currentCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You must be logged in to checkout.");
            alert.showAndWait();
            placeOrderButton.setDisable(true);
            return;
        }

        orderSummaryLabel.setText("Checkout for: " + Session.currentCustomer.getName());

        // calculate and display subtotal/tax/total
        updateTotals();

        placeOrderButton.setOnAction(e -> placeOrder());
    }

    private void updateTotals() {
        Cart cart = Cart.getInstance();
        double subtotal = 0;
        for (CartItem item : cart.getCartItems()) {
            subtotal += item.getQuantity() * item.getProd().getPrice();
        }
        double tax   = subtotal * TAX_RATE;
        double total = subtotal + tax;

        subtotalLabel.setText(String.format("$%.2f", subtotal));
        taxLabel     .setText(String.format("$%.2f", tax));
        totalLabel   .setText(String.format("$%.2f", total));
    }

    private void placeOrder() {
        String ship   = shippingAddressField.getText().trim();
        String mail   = emailField.getText().trim();
        String phone  = phoneField.getText().trim();
        String payment= paymentField.getText().trim();

        if (ship.isEmpty() || mail.isEmpty() || phone.isEmpty() || payment.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill out all fields.");
            alert.showAndWait();
            return;
        }

        Cart cart = Cart.getInstance();
        List<CartItem> cartItems = new ArrayList<>(cart.getCartItems());
        if (cartItems.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Your cart is empty!").showAndWait();
            return;
        }

        // convert cart items
        List<OrderItem> orderItems = new ArrayList<>();
        double subtotal = 0;
        StringBuilder orderSummary = new StringBuilder();

        for (CartItem ci : cartItems) {
            double price = ci.getProd().getPrice();
            String size = ci.getProd().getSize();
            //Add sizes
            orderSummary.append(ci.getProd().getName())
                    .append("[Size: ").append(size).append("] ")
                    .append("x").append(ci.getQuantity())
                    .append(" - $").append(price * ci.getQuantity()).append("\n");
            subtotal += ci.getQuantity() * price;
            orderItems.add(new OrderItem(ci.getProd(), ci.getQuantity(), price));
        }

        String orderId   = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order(Session.currentCustomer.getAccountId(), orderId, now, orderItems, subtotal + subtotal * TAX_RATE);

        Session.currentCustomer.addOrder(order);
        OrderStorage.saveOrder(order);
        //Clear cart after order
        cart.getCartItems().clear();

        //Display order summary
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                String.format("Order %s placed!\n\n%s\nSubtotal: $%.2f\nTax: $%.2f\nTotal: $%.2f",
                        orderId, orderSummary.toString(), subtotal, subtotal * TAX_RATE, subtotal + subtotal * TAX_RATE)
        );
        alert.showAndWait();
        ((Stage) placeOrderButton.getScene().getWindow()).close();
    }
}
