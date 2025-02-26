package com.ecommerce;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AccountController {

    @FXML
    private void showCongrats() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account");
        alert.setHeaderText(null);
        alert.setContentText("Congrats!");
        alert.showAndWait();
    }
}
