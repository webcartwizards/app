package com.ecommerce;
import java.io.*;
import java.util.*;

public class Cart {
    private List<CartItem> CartList;

    public Cart() {
        CartList = new ArrayList<>();
    }

    public void addToCart(Product prod, int quant) {
        CartItem item = new CartItem(prod, quant);
        CartList.add(item);

    }

    public boolean removeFromCart(CartItem item, int num) {
        if (num > item.quantity) {
            System.out.println("You cannot remove more than the quantity");
            return false;
        } else {
            item.quantity = item.quantity - num;
            for(int i = 0; i < CartList.size(); i++){
                if(CartList.get(i) == item && num > 0){
                    CartList.remove(i);
                    num--;
                }
            }
            return true;
        }
    }
}
