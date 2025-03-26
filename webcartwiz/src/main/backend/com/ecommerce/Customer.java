package com.ecommerce;

public class Customer{
    private final String name;
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
}
