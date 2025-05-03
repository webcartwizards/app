package com.ecommerce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    // Static map to store accounts (username -> Customer).
    public static Map<String, Customer> accounts = new HashMap<>();
    public static final String ACCOUNTS_FILE = "accounts.txt";

    @FXML
    public void initialize() {
        // Print working directory for debugging file location.
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
        loadAccounts();
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (!isInputValid(username, password)) return;

        Customer customer = accounts.get(username);
        if (customer == null) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Account does not exist. Please register.");
        } else if (!customer.getPassword().equals(password)) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect password.");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + customer.getName() + "!");
            Session.currentCustomer = customer;
        }
    }

    @FXML
    public void handleRegister(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (!isInputValid(username, password)) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both username and password.");
            return;
        }

        // Check if the username is "admin" and handle registration logic
        if (username.equals("admin")) {
            // Admin-specific registration logic
            if (!password.equals("password")) {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Admin account must have password 'password'.");
                return;
            }
            if (accounts.containsKey("admin")) {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Admin account already exists.");
                return;
            }
            Customer admin = new Customer("admin", "password", UUID.randomUUID().toString(), true);
            accounts.put("admin", admin);
            saveAccounts();
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Admin account created successfully.");
        } else {
            // Normal user registration logic
            if (accounts.containsKey(username)) {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username already exists.");
                return;
            }
            Customer newCustomer = new Customer(username, password, UUID.randomUUID().toString());
            accounts.put(username, newCustomer);
            saveAccounts();
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Account created successfully.");
        }
    }

    // Validates that both the username and password are non-empty.
    public boolean isInputValid(String username, String password) {
        if(username.isEmpty() || password.isEmpty()) {
            return false;
        }else
            return true;
    }

    // Register the special admin account.
    private void registerAdmin() {
        // Admin must have the password "password"
        if (!passwordField.getText().trim().equals("password")) {
            throw new IllegalArgumentException("Admin account must have password 'password'.");
        }
        if (accounts.containsKey("admin")) {
            throw new IllegalArgumentException("Admin account already exists.");
        }
        Customer admin = new Customer("admin", "password", UUID.randomUUID().toString(), true);
        accounts.put("admin", admin);
        saveAccounts();
    }

    // Register a normal account
    private void registerUser(String username, String password) {
        if (accounts.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }
        Customer newCustomer = new Customer(username, password, UUID.randomUUID().toString());
        accounts.put(username, newCustomer);
        saveAccounts();
    }

    // Loads accounts from accounts.txt
    private void loadAccounts() {
        accounts.clear();
        File file = new File(ACCOUNTS_FILE);
        if (!file.exists()) {
            System.out.println("accounts.txt not found at " + file.getAbsolutePath());
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: username:password:accountId:admin
                String[] parts = line.split(":", 4);
                if (parts.length == 4) {
                    String uname = parts[0].trim();
                    String pass = parts[1].trim();
                    String accId = parts[2].trim();
                    boolean isAdmin = Boolean.parseBoolean(parts[3].trim());
                    Customer customer = new Customer(uname, pass, accId, isAdmin);
                    accounts.put(uname, customer);
                    System.out.println("Loaded account: " + uname);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Saves accounts to accounts.txt
    private void saveAccounts() {
        File file = new File(ACCOUNTS_FILE);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Customer c : accounts.values()) {
                writer.println(String.format("%s:%s:%s:%s", c.getName(), c.getPassword(), c.getAccountId(), c.isAdmin()));
                System.out.println("Saved account: " + c.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Static helper method to retrieve a username by account ID.
    public static String getUsernameFromAccountId(String accountId) {
        for (Customer c : accounts.values()) {
            if (c.getAccountId().equals(accountId)) {
                return c.getName();
            }
        }
        return "Unknown";
    }

    // Displays an alert dialog.
    private void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
