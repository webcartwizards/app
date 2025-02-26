package com.ecommerce;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    // In-memory account store: username -> password
    private static Map<String, String> accounts = new HashMap<>();

    // File to store account info (in the working directory)
    private static final String ACCOUNTS_FILE = "accounts.txt";

    //calls this method whenever login page is selected
    @FXML
    private void initialize() {
        loadAccounts();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Check that both fields are filled
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter both username and password.");
            return;
        }

        // Check if the username already exists
        if (accounts.containsKey(username)) {
            // Username exists, check the password
            if (accounts.get(username).equals(password)) {
                showAlert(Alert.AlertType.INFORMATION, "Login", null, "Welcome back!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", null, "Incorrect password.");
            }
        } else {
            // Username does not exist, create a new account
            accounts.put(username, password);
            saveAccounts(); // Save the updated accounts to file
            showAlert(Alert.AlertType.INFORMATION, "Account Created", null, "An account has been made!");
        }
    }

    // Helper method to show an alert dialog
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Loads accounts from the text file into the 'accounts' map.
    private void loadAccounts() {
        accounts.clear();
        File file = new File(ACCOUNTS_FILE);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                // Each line should be in the format: username:password
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        accounts.put(parts[0], parts[1]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Saves the current 'accounts' map to the text file.
    private void saveAccounts() {
        File file = new File(ACCOUNTS_FILE);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Map.Entry<String, String> entry : accounts.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
