package com.ecommerce;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderStorage {
    private static final String ORDERS_FILE = "orders.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Saves an order to file by appending a new line.
     */
    public static void saveOrder(Order order) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ORDERS_FILE, true))) {
            writer.println(buildOrderLine(order));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *  Loads all orders from the orders file
     */
    public static List<Order> loadAllOrders() {
        List<Order> orders = new ArrayList<>();
        File file = new File(ORDERS_FILE);
        if (!file.exists()) return orders;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Order order = parseOrderLine(line);
                if (order != null) {
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Loads only the orders for the specified accountId.
     */
    public static List<Order> loadOrders(String accountId) {
        List<Order> allOrders = loadAllOrders();
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getAccountId().equals(accountId)) {
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }

    /**
     * Rewrites the orders file with the provided list of orders.
     */
    public static void updateOrders(List<Order> orders) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ORDERS_FILE, false))) {
            for (Order order : orders) {
                writer.println(buildOrderLine(order));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a single line representation of an order.
     * Format: accountId|orderId|orderDate|totalPrice|status|itemsData
     */
    private static String buildOrderLine(Order order) {
        StringBuilder itemsData = new StringBuilder();
        for (OrderItem item : order.getItems()) {
            itemsData.append(item.getProduct().getName())
                    .append(",")
                    .append(item.getQuantity())
                    .append(",")
                    .append(item.getPrice())
                    .append(",")
                    .append(item.getProduct().getSize());
        }
        if (itemsData.length() > 0) {
            itemsData.setLength(itemsData.length() - 1); // Remove trailing semicolon
        }
        return order.getAccountId() + "|" +
                order.getOrderId() + "|" +
                order.getOrderDate().format(FORMATTER) + "|" +
                order.getTotalPrice() + "|" +
                order.getStatus() + "|" +
                itemsData.toString();
    }

    /**
     * Parses a line from the orders file and returns an Order object.
     */
    private static Order parseOrderLine(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length < 6) return null;

            String accountId = parts[0];
            String orderId = parts[1];
            LocalDateTime orderDate = LocalDateTime.parse(parts[2], FORMATTER);
            double totalPrice = Double.parseDouble(parts[3]);
            String status = parts[4];
            String itemsData = parts[5];

            List<OrderItem> orderItems = new ArrayList<>();
            if (!itemsData.isEmpty()) {
                String[] itemEntries = itemsData.split(";");
                for (String itemEntry : itemEntries) {
                    String[] fields = itemEntry.split(",");
                    if (fields.length < 4) continue;
                    String productName = fields[0];
                    int quantity = Integer.parseInt(fields[1]);
                    double price = Double.parseDouble(fields[2]);
                    String size = fields[3];
                    Product dummyProduct = new Product(productName, price, size);
                    orderItems.add(new OrderItem(dummyProduct, quantity, price));
                }
            }

            Order order = new Order(accountId, orderId, orderDate, orderItems, totalPrice);
            order.setStatus(status);
            return order;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    }
