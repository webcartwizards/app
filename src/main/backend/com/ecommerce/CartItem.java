package com.ecommerce;

public class CartItem {
    Product prod;
    int quantity;
    boolean inCart;

    public CartItem(Product prod, int i) {
        this.prod = prod;
        this.quantity = i;
        if (quantity > 0) {
            inCart = true;
        } else {
            inCart = false;
        }
    }

    public Product getProd() {
        return prod;
    }

    public int getQuantity() {
        return quantity;
    }

    public void changeQuantity(int i) {
        if (i > 0) {
            this.quantity = i;
        } else {
            System.out.println("You cannot have a quantity less than 0");
        }
    }
}