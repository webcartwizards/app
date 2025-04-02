package com.ecommerce;

public class CartItem {
    Product prod;
    int quantity;

    public CartItem(Product prod, int i){
        this.prod = prod;
        this.quantity = i;
    }
    public Product getProd(){
        return prod;
    }
    public int getQuantity(){
        return quantity;
    }
    public void changeQuantity(int i){
        this.quantity = i;
    }
}
