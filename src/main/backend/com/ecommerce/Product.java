package com.ecommerce;

public class Product {
    public String name;
    private double price;
    int quantity;
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public void getName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return price;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void itemBought(){
        quantity--;
    }

}
