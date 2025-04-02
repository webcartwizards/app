package com.ecommerce;
import java.io.*;
import java.util.*;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addToCart(Product prod, int quant) {
        items.add(new CartItem(prod, quant));

    }

    public boolean removeFromCart(CartItem item, int num) {
        if (num > item.quantity) {
            System.out.println("You cannot remove more than the quantity");
            return false;
        } else {
            item.quantity = item.quantity - num;
            return true;
        }
    }
}
