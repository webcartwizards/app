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

    // List of products for sorting
    private List<Product> products;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the cart and product list
        cart = Cart.getInstance();
        products = Arrays.asList(
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

        // Show the products initially
        showProducts();
    }

    // Method to display the products in the TilePane
    private void showProducts() {
        productContainer.getChildren().clear();  // Clear existing items

        for (Product p : products) {
            VBox productCard = new VBox(5);
            productCard.setAlignment(Pos.CENTER);
            productCard.setStyle("-fx-border-color: #ccc; -fx-padding: 10; -fx-background-color: #fff;");
            productCard.setPrefSize(220, 220);

            String imagePath = "file:images/" + p.getName().toLowerCase().replace(" ", "_") + ".png";
            ImageView imageView = new ImageView(new Image(imagePath, true));
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);

            Label nameLabel = new Label(p.getName());
            Label priceLabel = new Label(String.format("$%.2f", p.getPrice()));

            Button addButton = new Button("Add to Cart");
            addButton.setOnAction(e -> {
                cart.addToCart(p, 1);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Added to Cart");
                alert.setHeaderText("Changes to Cart!");
                alert.setContentText(p.getName() + " has been added to your cart.");
                alert.showAndWait();
            });

            productCard.getChildren().addAll(imageView, nameLabel, priceLabel, addButton);
            productContainer.getChildren().add(productCard);
        }
    }

    // Sorts products by name
    @FXML
    public void sortByName() {
        ProductSorter.sortByName(products);
        showProducts();
    }

    // Sorts products by price
    @FXML
    private void sortByPrice() {
        ProductSorter.sortByPrice(products);  // Corrected class name here
        showProducts();
    }
    @FXML
    public void sortByPriceDescending(){
        ProductSorter.sortByPriceDescending(products);
        showProducts();
    }
}


