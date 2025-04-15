package com.ecommerce;
import java.io.*;
import java.util.*;
import java.util.HashMap;

public class Cart {
    private Customer customer;
    private Map<CartItem, Integer> CartList = new HashMap<>();

    public Cart(){
        this.CartList = new HashMap<>();
    }
    public Customer getCustomer(){
        return customer;
    }
    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    public Map<CartItem, Integer> getCartList(){
        return CartList;
    }
    public void addToCart(CartItem item) {
        if(CartList.containsKey(item)){
            CartList.put(item,CartList.get(item) + 1);
        }
        else{
            CartList.put(item,1);
        }
    }

    public void removeFromCart(CartItem item) {
        if(CartList.containsKey(item)){
            int quantity = item.quantity--;
            if(quantity > 0){
                CartList.put(item, quantity);
            }
            else{
                CartList.remove(item);
            }
        }
    }
}
