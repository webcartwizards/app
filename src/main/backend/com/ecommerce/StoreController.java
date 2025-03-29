package com.ecommerce;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class StoreController implements Initializable {

    @FXML
    private TilePane productContainer;

    // A Cart instance to store added products.
    private Cart cart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize your cart
        Cart cart = Cart.getInstance();


        // product list
        List<Product> products = Arrays.asList(
                new Product("Adidas Pants", 10.0),
                new Product("Black T Shirt", 20.0),
                new Product("Oakley Sunglasses", 60.0),
                new Product("Old Skool Hi Top Vans", 70.0),
                new Product("Product 5", 20.0),
                new Product("Product 6", 30.0),
                new Product("Product 7", 10.0),
                new Product("Product 8", 20.0),
                new Product("Product 9", 30.0),
                new Product("Product 10", 10.0),
                new Product("Product 11", 20.0),
                new Product("Product 12", 30.0)
        );

        for (Product p : products) {
            // Create a product card as a VBox.
            VBox productCard = new VBox(5);  // 5px spacing between elements
            productCard.setAlignment(Pos.CENTER);
            productCard.setStyle("-fx-border-color: #ccc; -fx-padding: 10; -fx-background-color: #fff;");
            productCard.setPrefSize(220, 220);  // fixed size for uniformity

            // Create an ImageView for the product image.
            // The image path is built from the product name
            String imagePath = "file:images/" + p.getName().toLowerCase().replace(" ", "_") + ".png";
            ImageView imageView = new ImageView(new Image(imagePath, true));
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);

            // Create labels for name and price.
            Label nameLabel = new Label(p.getName());
            Label priceLabel = new Label(String.format("$%.2f", p.getPrice()));

            // Create an "Add to Cart" button.
            Button addButton = new Button("Add to Cart");
            addButton.setOnAction(e -> {
                // Add one unit of the product to the cart.
                cart.addToCart(p, 1);

                // Show an alert confirming the addition.
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Added to Cart");
                alert.setHeaderText("Changes to Cart!");
                alert.setContentText(p.getName() + " has been added to your cart.");
                alert.showAndWait();
            });

            // Add the components to the product card.
            productCard.getChildren().addAll(imageView, nameLabel, priceLabel, addButton);

            // Add the product card to the TilePane
            productContainer.getChildren().add(productCard);
        }
    }
}
