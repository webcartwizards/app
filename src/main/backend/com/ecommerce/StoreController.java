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
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class StoreController implements Initializable {

    @FXML
    private TilePane productContainer;
    @FXML
    private TextField searchField;
    private List<Product> originalProducts;

    // A Cart instance to store added products.
    private Cart cart;

    // List of products for sorting
    private List<Product> products;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the cart and product list
        cart = Cart.getInstance();
        products = Arrays.asList(
                new Product("Black T Shirt", 25.0, "clothing"),
                new Product("Dress Shirt", 40.0, "clothing"),
                new Product("Purple Hoodie", 30.0, "clothing"),
                new Product("Adidas Pants", 70.0, "clothing"),
                new Product("Jeans", 20.0, "clothing"),
                new Product("Green Shorts", 15.0, "clothing"),
                new Product("Old Skool Hi Top Vans", 75.0, "shoes"),
                new Product("Converse", 80.0, "shoes"),
                new Product("Air Jordans", 180.0, "shoes"),
                new Product("Oakley Sunglasses", 200.0, "accessory"),
                new Product("Black Cap", 10.0, "accessory"),
                new Product("Rolex", 10000.0, "accessory")

        );
        originalProducts = products;

        // Show the products initially
        showProducts();
    }

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
                if (p.getCategory().equals("shoes")) {
                    List<String> sizes = Arrays.asList("7", "8", "9", "10", "11", "12"); // Shoe sizes
                    javafx.scene.control.ChoiceDialog<String> sizeDialog = new javafx.scene.control.ChoiceDialog<>("M", sizes);
                    sizeDialog.setTitle("Select Size");
                    sizeDialog.setHeaderText("Choose a size for " + p.getName());
                    sizeDialog.setContentText("Size:");

                    sizeDialog.showAndWait().ifPresent(selectedSize -> {
                        Product selectedProduct = new Product(p.getName(), p.getPrice(), p.getCategory(), selectedSize);
                        cart.addToCart(selectedProduct, 1);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Added to Cart");
                        alert.setHeaderText("Changes to Cart!");
                        alert.setContentText(p.getName() + " (Size " + selectedSize + ") has been added to your cart.");
                        alert.showAndWait();
                    });
                }
                else if (p.getCategory().equals("clothing")) {
                    List<String> sizes = Arrays.asList("S", "M", "L", "XL"); // Clothing sizes
                    javafx.scene.control.ChoiceDialog<String> sizeDialog = new javafx.scene.control.ChoiceDialog<>("M", sizes);
                    sizeDialog.setTitle("Select Size");
                    sizeDialog.setHeaderText("Choose a size for " + p.getName());
                    sizeDialog.setContentText("Size:");

                    sizeDialog.showAndWait().ifPresent(selectedSize -> {
                        Product selectedProduct = new Product(p.getName(), p.getPrice(), p.getCategory(), selectedSize);
                        cart.addToCart(selectedProduct, 1);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Added to Cart");
                        alert.setHeaderText("Changes to Cart!");
                        alert.setContentText(p.getName() + " (Size " + selectedSize + ") has been added to your cart.");
                        alert.showAndWait();
                    });
                }
                else {
                    cart.addToCart(p, 1);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Added to Cart");
                    alert.setHeaderText("Changes to Cart!");
                    alert.setContentText(p.getName() + " has been added to your cart.");
                    alert.showAndWait();
                }
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
    public void sortByPriceDescending() {
        ProductSorter.sortByPriceDescending(products);
        showProducts();
    }

    @FXML
    private void searchProducts() {
        String query = searchField.getText();
        // Check if originalProducts is null
        if (originalProducts == null) {
            // Log or handle this case appropriately
            System.out.println("originalProducts is null");
            return;  // Early exit if originalProducts is null
        }

        if (query == null || query.isEmpty()) {
            products = originalProducts; // Reset if no search text
        } else {
            // Pass originalProducts to searchByName method
            products = Search.searchByName(originalProducts, query);
        }
        showProducts();
    }
}




