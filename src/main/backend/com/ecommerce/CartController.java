package com.ecommerce;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CartController implements Initializable {

    // The grid-like container showing cart item "cards"
    @FXML
    private TilePane cartContainer;

    // The button that opens the checkout
    @FXML
    private Button checkoutButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateCartDisplay();
        checkoutButton.setOnAction(e -> openCheckoutModal());
    }

    /**
     * Rebuilds the cart display grid by clearing existing items and adding
     * a card for each cart item.
     */
    private void updateCartDisplay() {
        cartContainer.getChildren().clear();
        Cart cart = Cart.getInstance();
        List<CartItem> items = cart.getCartItems();

        // show message if cart is empty
        if (items.isEmpty()) {
            javafx.scene.control.Label empty = new javafx.scene.control.Label("You currently don't have any items in your cart!");
            cartContainer.getChildren().add(empty);
            return;
        }
        for (CartItem item : items) {
            VBox itemCard = createCartItemCard(item);
            cartContainer.getChildren().add(itemCard);
        }
    }

    /**
     * Creates and returns a VBox that represents a cart item.
     * The card includes an image, labels for name, quantity, price, and
     * buttons to increase or decrease (or remove) the quantity.
     */
    private VBox createCartItemCard(CartItem item) {
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; " +
                "-fx-background-color: #fff; -fx-background-radius: 10; -fx-border-radius: 10;");
        card.setPrefSize(220, 220);

        // Load product image assuming a convention: file:images/<product_name_in_lowercase>.png
        String imagePath = "file:images/" + item.getProd().getName().toLowerCase().replace(" ", "_") + ".png";
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(new javafx.scene.image.Image(imagePath, true));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        // Create labels for product name, quantity and price.
        javafx.scene.control.Label nameLabel = new javafx.scene.control.Label(item.getProd().getName());
        javafx.scene.control.Label sizeLabel = new javafx.scene.control.Label("Size: " + item.getProd().getSize());
        javafx.scene.control.Label quantityLabel = new javafx.scene.control.Label("Qty: " + item.getQuantity());
        javafx.scene.control.Label priceLabel = new javafx.scene.control.Label(String.format("$%.2f", item.getProd().getPrice()));

        // Increase quantity button
        Button increaseButton = new Button("+");
        increaseButton.setOnAction(e -> {
            item.changeQuantity(item.getQuantity() + 1);
            updateCartDisplay();
        });

        // Decrease button
        // Decrease button becomes a trash icon if quantity is 1.
        Button decreaseButton = (item.getQuantity() == 1) ? new Button("🗑") : new Button("-");
        decreaseButton.setOnAction(e -> {
            int newQuantity = item.getQuantity() - 1;
            if (newQuantity < 1) {
                // Remove the item from the cart.
                Cart.getInstance().getCartItems().remove(item);
                showAlert("Item Removed", item.getProd().getName() + " is removed from the cart!");
            } else {
                item.changeQuantity(newQuantity);
            }
            updateCartDisplay();
        });

        // Arrange buttons in an HBox with some spacing.
        HBox buttonContainer = new HBox(10, increaseButton, decreaseButton);
        buttonContainer.setAlignment(Pos.CENTER);

        // Assemble the card.
        card.getChildren().addAll(imageView, nameLabel,sizeLabel, quantityLabel, priceLabel, buttonContainer);

        return card;
    }

    /**
     * Opens the checkout page in a modal window.
     * The main application stays open while the checkout modal is displayed.
     */
    private void openCheckoutModal() {
        if (Session.currentCustomer == null) {
            showAlert("Login Required", "You must be logged in to proceed to checkout.");
            return;
        } else if (Cart.getInstance().getCartItems().isEmpty()) {
            showAlert("Cannot proceed to checkout", "There are no items currently in cart.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("checkout.fxml"));
            Parent checkoutRoot = loader.load();
            Scene checkoutScene = new Scene(checkoutRoot);
            Stage modalStage = new Stage();
            modalStage.initOwner(checkoutButton.getScene().getWindow());
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(checkoutScene);
            modalStage.setTitle("Checkout");
            modalStage.showAndWait();  // Modal window; execution waits until this closes.
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "An error occurred while loading the checkout page.");
        }
    }

    /**
     * Helper method to show an alert dialog.
     */
    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
