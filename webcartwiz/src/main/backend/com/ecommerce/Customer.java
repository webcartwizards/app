package com.ecommerce;

public class Customer{
    public final String name;
    private String password;
    public Customer() {
        this.name = "User";
        this.password = "password";
    }
    public Customer(String name){
        this.name = name;
        this.password = "password";
    }
    public Customer(String name, String password){
        this.name = name;
        this.password = password;
    }
    public void addToCart(Cart cart, Product prod, int quantity){
        cart.addToCart(prod,quantity);
    }
    public Product removeFromCart(Cart cart, Product prod, int quantity){
        cart.removeFromCart(prod,quantity);
        return prod;
    }
}
