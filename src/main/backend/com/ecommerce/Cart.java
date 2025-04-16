package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart instance = new Cart();  // Singleton instance
    private List<CartItem> cartList;

    // Private constructor ensures only one instance.
    private Cart() {
        cartList = new ArrayList<>();
    }

    // Public accessor for the singleton.
    public static Cart getInstance() {
        return instance;
    }

    // When adding a product, check if it exists in the cart.
    public void addToCart(Product prod, int quant) {
        for (CartItem item : cartList) {
            // Assuming products are identified by their name.
            if (item.getProd().getName().equals(prod.getName())) {
                // Increase the quantity of the existing item.
                item.changeQuantity(item.getQuantity() + quant);
                return; // Done—no need to add a new CartItem.
            }
        }
        // If the product is not already in the cart, add a new CartItem.
        CartItem newItem = new CartItem(prod, quant);
        cartList.add(newItem);
    }

    public List<CartItem> getCartItems() {
        return cartList;
    }
}
