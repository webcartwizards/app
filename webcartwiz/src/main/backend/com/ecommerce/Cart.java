package com.ecommerce;
import java.io.*;
import java.util.*;

public class Cart {
    private List<CartItem> items;
    public Cart(){
        items = new ArrayList<>();
    }
    public void addToCart(Product prod, int quant){
        items.add(new CartItem(prod, quant));

    }
    public boolean removeFromCart(Product prod, int num){
        if(num > 0){
            System.out.println("You cannot remove nothing from the cart");
            return false;
        }
        else return true;
    }
}
