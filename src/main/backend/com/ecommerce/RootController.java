package com.ecommerce;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class RootController {

    @FXML private BorderPane rootPane; // fx:id="rootPane" in root.fxml

    // This method replaces the center of rootPane with the requested FXML
    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node page = loader.load();
            rootPane.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Called when user selects "Go to Shop"
    @FXML
    private void goToShop() {
        loadPage("store.fxml");
    }

    // Called when user selects "View Cart"
    @FXML
    private void goToCart() {
        loadPage("cart.fxml");
    }

    // Called when user selects "Login Page"
    @FXML
    private void goToLogin() {
        loadPage("login.fxml");
    }

    // Called when user selects "My Account"
    @FXML
    private void goToAccount() {
        loadPage("account.fxml");
    }

}
