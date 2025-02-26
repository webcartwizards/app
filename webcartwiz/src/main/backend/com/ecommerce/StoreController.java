package com.ecommerce;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class StoreController {

    @FXML private BorderPane rootPane;  // Ensure store.fxml has fx:id="rootPane"

    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node page = loader.load();
            rootPane.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToShop() {
        loadPage("store.fxml");
    }

    @FXML
    private void goToCart() {
        loadPage("cart.fxml");
    }

    @FXML
    private void goToLogin() {
        loadPage("login.fxml");
    }

    @FXML
    private void goToAccount() {
        loadPage("account.fxml");
    }
}
